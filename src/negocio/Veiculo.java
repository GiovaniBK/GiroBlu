package negocio;

import negocio.excecao.VeiculoIndisponivelException;

/**
 * CAMADA DE NEGÓCIO Superclasse abstrata dos veículos. Esta classe não conhece
 * Swing.
 */
public abstract class Veiculo {
    private final String codigo;
    private final String modelo;
    public static final String DISPONIVEL = "DISPONIVEL";
    public static final String EM_USO = "EM_USO";
    public static final String EM_MANUTENCAO = "EM_MANUTENCAO";

    private String status;
    private Estacao estacaoAtual;

    /**
     * Cria um veículo sem posição inicial. @param codigo código único @param modelo
     * modelo
     */
    protected Veiculo(String codigo, String modelo) {
        if (codigo == null || codigo.isBlank())
            throw new IllegalArgumentException("O código do veículo não pode ser vazio.");
        if (modelo == null || modelo.isBlank())
            throw new IllegalArgumentException("O modelo do veículo não pode ser vazio.");
        this.codigo = codigo;
        this.modelo = modelo;
        this.status = DISPONIVEL;
        this.estacaoAtual = null;
    }

    /** @return código */
    public String getCodigo() {
        return codigo;
    }

    /** @return modelo */
    public String getModelo() {
        return modelo;
    }

    /** @return status */
    public String getStatus() {
        return status;
    }

    /** @return estação atual ou null */
    public Estacao getEstacaoAtual() {
        return estacaoAtual;
    }

    /** @return valor do desbloqueio */
    public abstract double getValorDesbloqueio();

    /** @return valor por minuto */
    public abstract double getValorPorMinuto();

    /** @return consumo de bateria por minuto */
    public abstract int getConsumoBateriaPorMinuto();

    /** @return tipo textual do veículo */
    public abstract String getTipoVeiculo();

    /** @return carga percentual do veículo, ou zero para veículo mecânico */
    public int getCargaPercentual() {
        return 0;
    }

    /** @return verdadeiro se aceita desconto universitário */
    public abstract boolean aceitaDescontoUniversitario();

    /** @return verdadeiro se pode ser liberado */
    public abstract boolean podeSerLiberado();

    /** Calcula o preço cheio. @param minutos duração @return valor arredondado */
    public double calcularValorCheio(int minutos) {
        if (minutos < 0)
            throw new IllegalArgumentException("Os minutos da viagem não podem ser negativos.");
        double valor = getValorDesbloqueio() + getValorPorMinuto() * minutos;
        return Dinheiro.arredondar(valor);
    }

    /** Libera o veículo para uma viagem. */
    public void liberarParaViagem() {
        if (!DISPONIVEL.equals(status))
            throw new VeiculoIndisponivelException("O veículo " + codigo + " não está disponível.");
        if (estacaoAtual == null)
            throw new VeiculoIndisponivelException("O veículo " + codigo + " não está em uma estação.");
        if (!podeSerLiberado())
            throw new VeiculoIndisponivelException("O veículo " + codigo + " não possui carga suficiente.");
        estacaoAtual.retirar(this);
        estacaoAtual = null;
        status = EM_USO;
    }

    /**
     * Devolve o veículo ao destino. @param destino estação @param minutos duração
     */
    public void devolver(Estacao destino, int minutos) {
        if (!EM_USO.equals(status))
            throw new VeiculoIndisponivelException("Apenas veículo em uso pode ser devolvido.");
        if (destino == null)
            throw new IllegalArgumentException("A estação de destino não pode ser nula.");
        if (minutos < 0)
            throw new IllegalArgumentException("Os minutos da viagem não podem ser negativos.");
        destino.receber(this);
        consumirBateria(minutos);
        status = DISPONIVEL;
    }

    /** Envia o veículo para manutenção. */
    public void enviarParaManutencao() {
        if (EM_USO.equals(status))
            throw new VeiculoIndisponivelException("Um veículo em uso não pode ir para manutenção.");
        status = EM_MANUTENCAO;
    }

    /** Retorna o veículo da manutenção à operação. */
    public void voltarDaManutencao() {
        if (!EM_MANUTENCAO.equals(status))
            throw new VeiculoIndisponivelException("Somente veículo em manutenção pode voltar à operação.");
        status = DISPONIVEL;
    }

    /** Consome bateria internamente ao devolver. @param minutos minutos */
    protected abstract void consumirBateria(int minutos);

    /** Recarrega ou não altera veículo mecânico. */
    public abstract void recarregar();

    /** Define a estação dentro do pacote. @param estacao estação atual */
    void definirEstacaoAtual(Estacao estacao) {
        estacaoAtual = estacao;
    }

    /**
     * Restaura o status persistido sem alterar vagas ou estacionamentos.
     * 
     * @param statusRestaurado estado gravado
     */
    public void restaurarStatus(String statusRestaurado) {
        if (!DISPONIVEL.equals(statusRestaurado)
                && !EM_USO.equals(statusRestaurado)
                && !EM_MANUTENCAO.equals(statusRestaurado))
            throw new IllegalArgumentException("Status de veículo inválido.");
        status = statusRestaurado;
    }

    @Override
    public String toString() {
        return "Código: " + codigo + ", Modelo: " + modelo + ", Status: " + status;
    }
}
