package negocio;

import java.time.LocalDateTime;
import java.time.Duration;

/**
 * CAMADA DE NEGÓCIO Representa uma viagem iniciada e encerrada na plataforma.
 */
public class Viagem {
  private final Usuario usuario;
  private final Veiculo veiculo;
  private final Estacao estacaoOrigem;
  private Estacao estacaoDestino;
  private final LocalDateTime inicio;
  private LocalDateTime termino;
  private int duracaoMinutos;
  private double valorCobrado;

  /**
   * Inicia uma viagem.
   * 
   * @param usuario       usuário
   * @param veiculo       veículo
   * @param estacaoOrigem estação de origem
   * @param inicio        data e hora de início
   */
  public Viagem(Usuario usuario, Veiculo veiculo, Estacao estacaoOrigem, LocalDateTime inicio) {
    if (usuario == null)
      throw new IllegalArgumentException("O usuário da viagem não pode ser nulo.");
    if (veiculo == null)
      throw new IllegalArgumentException("O veículo da viagem não pode ser nulo.");
    if (estacaoOrigem == null)
      throw new IllegalArgumentException("A estação de origem não pode ser nula.");
    if (inicio == null)
      throw new IllegalArgumentException("A data e hora de início não podem ser nulas.");
    this.usuario = usuario;
    this.veiculo = veiculo;
    this.estacaoOrigem = estacaoOrigem;
    this.inicio = inicio;
    this.valorCobrado = 0.00;
  }

  /**
   * Restaura uma viagem encerrada já persistida sem cobrar novamente.
   * 
   * @param usuario        usuário
   * @param veiculo        veículo
   * @param estacaoOrigem  estação de origem
   * @param estacaoDestino estação de destino
   * @param inicio         início da viagem
   * @param termino        término da viagem
   * @param duracaoMinutos duração em minutos
   * @param valorCobrado   valor já cobrado
   */
  public Viagem(Usuario usuario, Veiculo veiculo, Estacao estacaoOrigem,
      Estacao estacaoDestino, LocalDateTime inicio, LocalDateTime termino,
      int duracaoMinutos, double valorCobrado) {
    this(usuario, veiculo, estacaoOrigem, inicio);
    if (estacaoDestino == null)
      throw new IllegalArgumentException("A estação de destino não pode ser nula.");
    if (termino == null)
      throw new IllegalArgumentException("A data e hora de término não podem ser nulas.");
    if (!termino.isAfter(inicio))
      throw new IllegalArgumentException("O término deve ser posterior ao início.");
    if (duracaoMinutos < 0)
      throw new IllegalArgumentException("A duração não pode ser negativa.");
    if (!Double.isFinite(valorCobrado) || valorCobrado < 0)
      throw new IllegalArgumentException("O valor cobrado não pode ser negativo.");
    this.estacaoDestino = estacaoDestino;
    this.termino = termino;
    this.duracaoMinutos = duracaoMinutos;
    this.valorCobrado = Dinheiro.arredondar(valorCobrado);
  }

  /**
   * Encerra a viagem no destino.
   * 
   * @param destino estação de destino
   * @param termino data e hora de término
   */
  public void encerrar(Estacao destino, LocalDateTime termino) {
    if (destino == null)
      throw new IllegalArgumentException("A estação de destino não pode ser nula.");
    if (termino == null)
      throw new IllegalArgumentException("A data e hora de término não podem ser nulas.");
    if (!termino.isAfter(inicio))
      throw new IllegalArgumentException("O término deve ser posterior ao início.");
    if (destino.getVagasLivres() <= 0)
      throw new negocio.excecao.EstacaoLotadaException("A estação " + destino.getNome() + " está lotada.");

    duracaoMinutos = (int) Duration.between(inicio, termino).toMinutes();
    valorCobrado = usuario.getPlano().calcularValor(veiculo, duracaoMinutos);
    veiculo.devolver(destino, duracaoMinutos);
    usuario.getCarteira().debitarViagem(termino, "Viagem - " + veiculo.getCodigo(), valorCobrado);
    estacaoDestino = destino;
    this.termino = termino;
  }

  /** @return usuário */
  public Usuario getUsuario() {
    return usuario;
  }

  /** @return veículo */
  public Veiculo getVeiculo() {
    return veiculo;
  }

  /** @return origem */
  public Estacao getEstacaoOrigem() {
    return estacaoOrigem;
  }

  /** @return destino ou null */
  public Estacao getEstacaoDestino() {
    return estacaoDestino;
  }

  /** @return início */
  public LocalDateTime getInicio() {
    return inicio;
  }

  /** @return término ou null */
  public LocalDateTime getTermino() {
    return termino;
  }

  /** @return duração em minutos */
  public int getDuracaoMinutos() {
    return duracaoMinutos;
  }

  /** @return valor cobrado */
  public double getValorCobrado() {
    return valorCobrado;
  }

  /** @return verdadeiro quando ainda está em andamento */
  public boolean estaEmAndamento() {
    return termino == null;
  }
}
