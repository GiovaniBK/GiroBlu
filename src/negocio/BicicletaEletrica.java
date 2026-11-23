package negocio;

import java.math.BigDecimal;

/** CAMADA DE NEGÓCIO Bicicleta elétrica da frota GiroBlu. */
public class BicicletaEletrica extends VeiculoEletrico {
    /** @param codigo código @param modelo modelo @param bateria carga */
    public BicicletaEletrica(String codigo, String modelo, int bateria) { super(codigo, modelo, bateria); }
    @Override public BigDecimal getValorDesbloqueio() { return new BigDecimal("2.00"); }
    @Override public BigDecimal getValorPorMinuto() { return new BigDecimal("0.30"); }
    @Override public int getConsumoBateriaPorMinuto() { return 1; }
    @Override public boolean aceitaDescontoUniversitario() { return true; }
    @Override public String toString() { return super.toString() + ", Tipo: Bicicleta elétrica"; }
}
