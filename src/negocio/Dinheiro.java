package negocio;

import java.util.Locale;

/**
 * CAMADA DE NEGÓCIO Simplifica arredondamento e exibição de valores em reais.
 */
public final class Dinheiro {
  private Dinheiro() {
  }

  /**
   * Arredonda um valor para duas casas decimais. @param valor valor a
   * arredondar @return valor arredondado
   */
  public static double arredondar(double valor) {
    if (!Double.isFinite(valor))
      throw new IllegalArgumentException("O valor monetário não é válido.");
    return Math.round((valor + 1e-9) * 100.0) / 100.0;
  }

  /**
   * Formata um valor com duas casas. @param valor valor @return texto com duas
   * casas
   */
  public static String formatar(double valor) {
    return String.format(Locale.US, "%.2f", arredondar(valor));
  }
}
