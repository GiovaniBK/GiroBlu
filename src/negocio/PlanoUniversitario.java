package negocio;

import java.math.BigDecimal;
import java.math.RoundingMode;

/** CAMADA DE NEGÓCIO Plano universitário com cinquenta por cento de desconto quando permitido pelo veículo. */
public class PlanoUniversitario implements Plano {
    @Override public BigDecimal calcularValor(Veiculo veiculo, int minutos) {
        BigDecimal valor = veiculo.calcularValorCheio(minutos);
        if (veiculo.aceitaDescontoUniversitario()) valor = valor.multiply(new BigDecimal("0.50"));
        return valor.setScale(2, RoundingMode.HALF_UP);
    }
}
