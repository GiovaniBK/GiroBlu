package negocio;

import java.math.BigDecimal;

/** CAMADA DE NEGÓCIO Bicicleta mecânica da frota GiroBlu. */
public class BicicletaMecanica extends Veiculo {
    /** @param codigo código @param modelo modelo */
    public BicicletaMecanica(String codigo, String modelo) { super(codigo, modelo); }
    @Override public BigDecimal getValorDesbloqueio() { return new BigDecimal("1.00"); }
    @Override public BigDecimal getValorPorMinuto() { return new BigDecimal("0.15"); }
    @Override public int getConsumoBateriaPorMinuto() { return 0; }
    @Override public boolean aceitaDescontoUniversitario() { return true; }
    @Override public boolean podeSerLiberado() { return true; }
    @Override protected void consumirBateria(int minutos) { }
    @Override public void recarregar() { }
    @Override public String toString() { return super.toString() + ", Tipo: Bicicleta mecânica"; }
}
