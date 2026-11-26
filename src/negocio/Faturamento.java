package negocio;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * CAMADA DE NEGÓCIO Consolida viagens encerradas por tipo de veículo em um
 * período.
 */
public class Faturamento {
  private final List<ItemFaturamento> itens;

  /**
   * Calcula o faturamento de uma lista de viagens.
   * 
   * @param viagens viagens encerradas
   * @param inicio  início inclusivo do período
   * @param fim     fim inclusivo do período
   */
  public Faturamento(List<Viagem> viagens, LocalDateTime inicio, LocalDateTime fim) {
    if (viagens == null)
      throw new IllegalArgumentException("A lista de viagens não pode ser nula.");
    if (inicio == null || fim == null)
      throw new IllegalArgumentException("O período não pode conter datas nulas.");
    if (fim.isBefore(inicio))
      throw new IllegalArgumentException("O fim do período não pode ser anterior ao início.");

    itens = new ArrayList<>();
    for (Viagem viagem : viagens) {
      if (viagem == null)
        throw new IllegalArgumentException("A lista de viagens não pode conter valores nulos.");
      LocalDateTime termino = viagem.getTermino();
      if (termino != null && !termino.isBefore(inicio) && !termino.isAfter(fim)) {
        String tipo = viagem.getVeiculo().getTipoVeiculo();
        ItemFaturamento encontrado = null;
        for (ItemFaturamento item : itens)
          if (item.getTipo().equals(tipo))
            encontrado = item;
        if (encontrado == null) {
          encontrado = new ItemFaturamento(tipo);
          itens.add(encontrado);
        }
        encontrado.adicionar(viagem.getValorCobrado());
      }
    }
  }

  /** @return cópia dos totais por tipo */
  public List<ItemFaturamento> getItens() {
    return List.copyOf(itens);
  }

  /** @return quantidade total de viagens */
  public int getQuantidadeTotal() {
    int total = 0;
    for (ItemFaturamento item : itens)
      total += item.getQuantidade();
    return total;
  }

  /** @return valor total arrecadado */
  public double getValorTotal() {
    double total = 0.00;
    for (ItemFaturamento item : itens)
      total += item.getValor();
    return Dinheiro.arredondar(total);
  }

  /** CAMADA DE NEGÓCIO Total simples de um tipo de veículo. */
  public static class ItemFaturamento {
    private final String tipo;
    private int quantidade;
    private double valor;

    private ItemFaturamento(String tipo) {
      this.tipo = tipo;
      this.valor = 0.00;
    }

    /** @return tipo do veículo */
    public String getTipo() {
      return tipo;
    }

    /** @return quantidade de viagens */
    public int getQuantidade() {
      return quantidade;
    }

    /** @return valor arrecadado */
    public double getValor() {
      return valor;
    }

    private void adicionar(double valorAdicionado) {
      quantidade++;
      valor = Dinheiro.arredondar(valor + valorAdicionado);
    }
  }
}
