package negocio;

/** CAMADA DE NEGÓCIO Bicicleta elétrica da frota GiroBlu. */
public class BicicletaEletrica extends VeiculoEletrico {
    /** @param codigo código @param modelo modelo @param bateria carga */
    public BicicletaEletrica(String codigo, String modelo, int bateria) {
        super(codigo, modelo, bateria);
    }

    @Override
    public double getValorDesbloqueio() {
        return 2.00;
    }

    @Override
    public double getValorPorMinuto() {
        return 0.30;
    }

    @Override
    public int getConsumoBateriaPorMinuto() {
        return 1;
    }

    @Override
    public boolean aceitaDescontoUniversitario() {
        return true;
    }

    @Override
    public String getTipoVeiculo() {
        return "Bicicleta elétrica";
    }

    @Override
    public String toString() {
        return super.toString() + ", Tipo: Bicicleta elétrica";
    }
}
