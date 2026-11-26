import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

import negocio.BicicletaMecanica;
import negocio.Estacao;
import negocio.PlanoAvulso;
import negocio.Usuario;
import negocio.Viagem;

class ViagemTest {
  @Test
  void encerramentoCalculaDuracaoValorEDebitaCarteira() {
    Usuario usuario = new Usuario("Ana", "ana@teste.com", new PlanoAvulso());
    usuario.getCarteira().creditar(new negocio.Recarga(LocalDateTime.now(), 20.00));
    Estacao origem = new Estacao("Origem", 2);
    Estacao destino = new Estacao("Destino", 2);
    BicicletaMecanica bicicleta = new BicicletaMecanica("BM-1", "Mec");
    origem.estacionar(bicicleta);
    bicicleta.liberarParaViagem();

    LocalDateTime inicio = LocalDateTime.of(2026, 1, 1, 9, 0);
    Viagem viagem = new Viagem(usuario, bicicleta, origem, inicio);
    viagem.encerrar(destino, LocalDateTime.of(2026, 1, 1, 9, 12));

    assertEquals(12, viagem.getDuracaoMinutos());
    assertEquals(2.80, viagem.getValorCobrado(), 0.001);
    assertEquals(17.20, usuario.getCarteira().getSaldo(), 0.001);
    assertEquals(destino, bicicleta.getEstacaoAtual());
  }

  @Test
  void segundosExcedentesSaoDescartadosDaDuracao() {
    Usuario usuario = new Usuario("Ana", "ana@teste.com", new PlanoAvulso());
    Estacao origem = new Estacao("Origem", 1);
    Estacao destino = new Estacao("Destino", 1);
    BicicletaMecanica bicicleta = new BicicletaMecanica("BM-3", "Mec");
    origem.estacionar(bicicleta);
    bicicleta.liberarParaViagem();

    LocalDateTime inicio = LocalDateTime.of(2026, 1, 1, 9, 0);
    Viagem viagem = new Viagem(usuario, bicicleta, origem, inicio);
    viagem.encerrar(destino, LocalDateTime.of(2026, 1, 1, 9, 12, 30));

    assertEquals(12, viagem.getDuracaoMinutos());
  }

  @Test
  void terminoAnteriorOuIgualAoInicioEValidado() {
    Usuario usuario = new Usuario("Ana", "ana@teste.com", new PlanoAvulso());
    Estacao origem = new Estacao("Origem", 1);
    Estacao destino = new Estacao("Destino", 1);
    BicicletaMecanica bicicleta = new BicicletaMecanica("BM-2", "Mec");
    LocalDateTime inicio = LocalDateTime.of(2026, 1, 1, 9, 0);
    Viagem viagem = new Viagem(usuario, bicicleta, origem, inicio);
    assertThrows(IllegalArgumentException.class, () -> viagem.encerrar(destino, inicio));
  }
}
