package negocio;

import java.time.LocalDateTime;

/** CAMADA DE NEGÓCIO Representa um lançamento no extrato da carteira. */
public class Lancamento {
  private final LocalDateTime dataHora;
  private final String descricao;
  private final double valor;
  private final double saldoDepois;

  /**
   * Cria um lançamento.
   * 
   * @param dataHora    momento do lançamento
   * @param descricao   descrição da operação
   * @param valor       valor positivo para crédito ou negativo para débito
   * @param saldoDepois saldo após o lançamento
   */
  public Lancamento(LocalDateTime dataHora, String descricao, double valor, double saldoDepois) {
    if (dataHora == null)
      throw new IllegalArgumentException("A data e hora do lançamento não podem ser nulas.");
    if (descricao == null || descricao.isBlank())
      throw new IllegalArgumentException("A descrição do lançamento não pode ser vazia.");
    if (!Double.isFinite(valor))
      throw new IllegalArgumentException("O valor do lançamento não pode ser nulo.");
    if (!Double.isFinite(saldoDepois))
      throw new IllegalArgumentException("O saldo do lançamento não pode ser nulo.");
    this.dataHora = dataHora;
    this.descricao = descricao;
    this.valor = Dinheiro.arredondar(valor);
    this.saldoDepois = Dinheiro.arredondar(saldoDepois);
  }

  /** @return momento do lançamento */
  public LocalDateTime getDataHora() {
    return dataHora;
  }

  /** @return descrição */
  public String getDescricao() {
    return descricao;
  }

  /** @return crédito ou débito */
  public double getValor() {
    return valor;
  }

  /** @return saldo depois do lançamento */
  public double getSaldoDepois() {
    return saldoDepois;
  }
}
