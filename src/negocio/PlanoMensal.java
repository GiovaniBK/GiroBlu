package negocio;

import java.math.BigDecimal;
import java.math.RoundingMode;

/** CAMADA DE NEGÓCIO Plano mensal sem desbloqueio e com quinze minutos gratuitos. */
public class PlanoMensal implements Plano {
    @Override public BigDecimal calcularValor(Veiculo veiculo, int minutos) {
        if (minutos < 0) throw new IllegalArgumentException("Os minutos da viagem não podem ser negativos.");
        if (minutos <= 15) return BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        BigDecimal valor = veiculo.getValorPorMinuto().multiply(new BigDecimal(minutos - 15));
        return valor.setScale(2, RoundingMode.HALF_UP);
    }
}
