import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

import negocio.Carteira;
import negocio.Lancamento;
import negocio.Recarga;

class LancamentoTest {
  @Test
  void extratoOrdenaRecargasEDebitosPorHorario() {
    Carteira carteira = new Carteira();
    LocalDateTime recargaAntes = LocalDateTime.of(2026, 1, 1, 9, 0);
    LocalDateTime viagemDepois = LocalDateTime.of(2026, 1, 1, 10, 0);
    carteira.creditar(new Recarga(recargaAntes, 10.00));
    carteira.debitarViagem(viagemDepois, "Viagem - BM-1", 2.80);

    assertEquals(2, carteira.getExtrato().size());
    assertEquals(7.20, carteira.getExtrato().get(1).getSaldoDepois(), 0.001);
    assertEquals(-2.80, carteira.getExtrato().get(1).getValor(), 0.001);
  }

  @Test
  void lancamentoValidaDados() {
    assertThrows(IllegalArgumentException.class,
        () -> new Lancamento(null, "Recarga", 1.00, 1.00));
  }
}
