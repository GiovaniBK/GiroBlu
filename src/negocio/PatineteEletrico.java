package negocio;

import java.math.BigDecimal;

/** CAMADA DE NEGÓCIO Patinete elétrico da frota GiroBlu. */
public class PatineteEletrico extends VeiculoEletrico {
    /** @param codigo código @param modelo modelo @param bateria carga */
    public PatineteEletrico(String codigo, String modelo, int bateria) { super(codigo, modelo, bateria); }
    @Override public BigDecimal getValorDesbloqueio() { return new BigDecimal("3.00"); }
    @Override public BigDecimal getValorPorMinuto() { return new BigDecimal("0.40"); }
    @Override public int getConsumoBateriaPorMinuto() { return 2; }
    @Override public boolean aceitaDescontoUniversitario() { return false; }
    @Override public String toString() { return super.toString() + ", Tipo: Patinete elétrico"; }
}
