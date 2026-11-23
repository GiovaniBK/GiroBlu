package negocio;

import java.math.BigDecimal;
import java.math.RoundingMode;
import negocio.excecao.VeiculoIndisponivelException;

/** CAMADA DE NEGÓCIO Superclasse abstrata dos veículos. Esta classe não conhece Swing. */
public abstract class Veiculo {
    private final String codigo;
    private final String modelo;
    private StatusVeiculo status;
    private Estacao estacaoAtual;

    /** Cria um veículo sem posição inicial. @param codigo código único @param modelo modelo */
    protected Veiculo(String codigo, String modelo) {
        if (codigo == null || codigo.isBlank()) throw new IllegalArgumentException("O código do veículo não pode ser vazio.");
        if (modelo == null || modelo.isBlank()) throw new IllegalArgumentException("O modelo do veículo não pode ser vazio.");
        this.codigo = codigo;
        this.modelo = modelo;
        this.status = StatusVeiculo.DISPONIVEL;
        this.estacaoAtual = null;
    }

    /** @return código */ public String getCodigo() { return codigo; }
    /** @return modelo */ public String getModelo() { return modelo; }
    /** @return status */ public StatusVeiculo getStatus() { return status; }
    /** @return estação atual ou null */ public Estacao getEstacaoAtual() { return estacaoAtual; }

    /** @return valor do desbloqueio */ public abstract BigDecimal getValorDesbloqueio();
    /** @return valor por minuto */ public abstract BigDecimal getValorPorMinuto();
    /** @return consumo de bateria por minuto */ public abstract int getConsumoBateriaPorMinuto();
    /** @return verdadeiro se aceita desconto universitário */ public abstract boolean aceitaDescontoUniversitario();
    /** @return verdadeiro se pode ser liberado */ public abstract boolean podeSerLiberado();

    /** Calcula o preço cheio. @param minutos duração @return valor arredondado */
    public BigDecimal calcularValorCheio(int minutos) {
        if (minutos < 0) throw new IllegalArgumentException("Os minutos da viagem não podem ser negativos.");
        BigDecimal valor = getValorDesbloqueio().add(getValorPorMinuto().multiply(new BigDecimal(minutos)));
        return valor.setScale(2, RoundingMode.HALF_UP);
    }

    /** Libera o veículo para uma viagem. */
    public void liberarParaViagem() {
        if (status != StatusVeiculo.DISPONIVEL) throw new VeiculoIndisponivelException("O veículo " + codigo + " não está disponível.");
        if (estacaoAtual == null) throw new VeiculoIndisponivelException("O veículo " + codigo + " não está em uma estação.");
        if (!podeSerLiberado()) throw new VeiculoIndisponivelException("O veículo " + codigo + " não possui carga suficiente.");
        estacaoAtual.retirar(this);
        estacaoAtual = null;
        status = StatusVeiculo.EM_USO;
    }

    /** Devolve o veículo ao destino. @param destino estação @param minutos duração */
    public void devolver(Estacao destino, int minutos) {
        if (status != StatusVeiculo.EM_USO) throw new VeiculoIndisponivelException("Apenas veículo em uso pode ser devolvido.");
        if (destino == null) throw new IllegalArgumentException("A estação de destino não pode ser nula.");
        if (minutos < 0) throw new IllegalArgumentException("Os minutos da viagem não podem ser negativos.");
        destino.receber(this);
        consumirBateria(minutos);
        status = StatusVeiculo.DISPONIVEL;
    }

    /** Envia o veículo para manutenção. */
    public void enviarParaManutencao() {
        if (status == StatusVeiculo.EM_USO) throw new VeiculoIndisponivelException("Um veículo em uso não pode ir para manutenção.");
        status = StatusVeiculo.EM_MANUTENCAO;
    }

    /** Retorna o veículo da manutenção à operação. */
    public void voltarDaManutencao() {
        if (status != StatusVeiculo.EM_MANUTENCAO) throw new VeiculoIndisponivelException("Somente veículo em manutenção pode voltar à operação.");
        status = StatusVeiculo.DISPONIVEL;
    }

    /** Consome bateria internamente ao devolver. @param minutos minutos */
    protected abstract void consumirBateria(int minutos);

    /** Recarrega ou não altera veículo mecânico. */
    public abstract void recarregar();

    /** Define a estação dentro do pacote. @param estacao estação atual */
    void definirEstacaoAtual(Estacao estacao) { estacaoAtual = estacao; }

    @Override
    public String toString() {
        return "Código: " + codigo + ", Modelo: " + modelo + ", Status: " + status;
    }
}
