package negocio;

import java.math.BigDecimal;

/** CAMADA DE NEGÓCIO Plano avulso com tarifa completa. */
public class PlanoAvulso implements Plano {
    @Override public BigDecimal calcularValor(Veiculo veiculo, int minutos) {
        return veiculo.calcularValorCheio(minutos);
    }
}
