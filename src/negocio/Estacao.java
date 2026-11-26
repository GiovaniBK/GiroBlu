package negocio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import negocio.excecao.EstacaoLotadaException;

/**
 * CAMADA DE NEGÓCIO Representa uma estação e controla veículos estacionados.
 */
public class Estacao {
    private final String nome;
    private final int quantidadeVagas;
    private final List<Veiculo> veiculosEstacionados;

    /** Cria uma estação. @param nome nome @param quantidadeVagas capacidade */
    public Estacao(String nome, int quantidadeVagas) {
        if (nome == null || nome.isBlank())
            throw new IllegalArgumentException("O nome da estação não pode ser vazio.");
        if (quantidadeVagas < 0)
            throw new IllegalArgumentException("A quantidade de vagas não pode ser negativa.");
        this.nome = nome;
        this.quantidadeVagas = quantidadeVagas;
        this.veiculosEstacionados = new ArrayList<>();
    }

    /** @return nome */
    public String getNome() {
        return nome;
    }

    /** @return vagas totais */
    public int getQuantidadeVagas() {
        return quantidadeVagas;
    }

    /** Estaciona um veículo disponível ou em manutenção. @param veiculo veículo */
    public void estacionar(Veiculo veiculo) {
        if (veiculo == null)
            throw new IllegalArgumentException("O veículo não pode ser nulo.");
        if (Veiculo.EM_USO.equals(veiculo.getStatus()))
            throw new IllegalArgumentException("Um veículo em uso não pode ser estacionado diretamente.");
        if (veiculo.getEstacaoAtual() != null)
            throw new IllegalArgumentException("O veículo já está estacionado em uma estação.");
        receber(veiculo);
    }

    /** Recebe um veículo em devolução. @param veiculo veículo devolvido */
    void receber(Veiculo veiculo) {
        if (veiculosEstacionados.size() >= quantidadeVagas)
            throw new EstacaoLotadaException("A estação " + nome + " está lotada.");
        veiculosEstacionados.add(veiculo);
        veiculo.definirEstacaoAtual(this);
    }

    /** Retira um veículo da estação. @param veiculo veículo */
    void retirar(Veiculo veiculo) {
        if (veiculo == null)
            throw new IllegalArgumentException("O veículo não pode ser nulo.");
        if (!veiculosEstacionados.contains(veiculo))
            throw new IllegalArgumentException("O veículo não está estacionado nesta estação.");
        veiculosEstacionados.remove(veiculo);
    }

    /** @return veículos disponíveis */
    public List<Veiculo> consultarDisponiveis() {
        List<Veiculo> disponiveis = new ArrayList<>();
        for (Veiculo veiculo : veiculosEstacionados)
            if (Veiculo.DISPONIVEL.equals(veiculo.getStatus()))
                disponiveis.add(veiculo);
        return Collections.unmodifiableList(disponiveis);
    }

    /** @return cópia dos veículos estacionados */
    public List<Veiculo> getVeiculosEstacionados() {
        return Collections.unmodifiableList(new ArrayList<>(veiculosEstacionados));
    }

    /** @return quantidade estacionada */
    public int getQuantidadeVeiculosEstacionados() {
        return veiculosEstacionados.size();
    }

    /** @return vagas livres */
    public int getVagasLivres() {
        return quantidadeVagas - veiculosEstacionados.size();
    }

    @Override
    public String toString() {
        return nome + " - vagas ocupadas: " + veiculosEstacionados.size() + " / " + quantidadeVagas;
    }
}
