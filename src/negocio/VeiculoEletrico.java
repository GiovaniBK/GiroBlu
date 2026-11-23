package negocio;

import negocio.excecao.VeiculoIndisponivelException;

/** CAMADA DE NEGÓCIO Superclasse dos veículos elétricos. */
public abstract class VeiculoEletrico extends Veiculo {
    private int bateria;

    /** Cria um elétrico. @param codigo código @param modelo modelo @param bateria carga */
    protected VeiculoEletrico(String codigo, String modelo, int bateria) {
        super(codigo, modelo);
        if (bateria < 0 || bateria > 100) throw new IllegalArgumentException("A bateria deve estar entre 0% e 100%.");
        this.bateria = bateria;
    }

    /** @return carga atual */ public int getBateria() { return bateria; }

    @Override public boolean podeSerLiberado() { return bateria >= 20; }

    @Override
    protected void consumirBateria(int minutos) {
        if (minutos < 0) throw new IllegalArgumentException("Os minutos de uso não podem ser negativos.");
        bateria = Math.max(0, bateria - minutos * getConsumoBateriaPorMinuto());
    }

    @Override
    public void recarregar() {
        if (getStatus() == StatusVeiculo.EM_USO) throw new VeiculoIndisponivelException("Um veículo em uso não pode ser recarregado.");
        bateria = 100;
    }

    @Override public String toString() { return super.toString() + ", Bateria: " + bateria + "%"; }
}
