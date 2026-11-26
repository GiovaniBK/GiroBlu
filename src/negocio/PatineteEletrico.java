package negocio;

/** CAMADA DE NEGÓCIO Patinete elétrico da frota GiroBlu. */
public class PatineteEletrico extends VeiculoEletrico {
    /** @param codigo código @param modelo modelo @param bateria carga */
    public PatineteEletrico(String codigo, String modelo, int bateria) {
        super(codigo, modelo, bateria);
    }

    @Override
    public double getValorDesbloqueio() {
        return 3.00;
    }

    @Override
    public double getValorPorMinuto() {
        return 0.40;
    }

    @Override
    public int getConsumoBateriaPorMinuto() {
        return 2;
    }

    @Override
    public boolean aceitaDescontoUniversitario() {
        return false;
    }

    @Override
    public String getTipoVeiculo() {
        return "Patinete elétrico";
    }

    @Override
    public String toString() {
        return super.toString() + ", Tipo: Patinete elétrico";
    }
}
