import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

import negocio.BicicletaMecanica;
import negocio.Estacao;
import negocio.Faturamento;
import negocio.PlanoAvulso;
import negocio.Usuario;
import negocio.Viagem;

class FaturamentoTest {
  @Test
  void somaViagensEncerradasPorTipoDentroDoPeriodo() {
    Estacao origem = new Estacao("Origem", 1);
    Estacao destino = new Estacao("Destino", 1);
    BicicletaMecanica bicicleta = new BicicletaMecanica("BM-1", "Mec");
    origem.estacionar(bicicleta);
    bicicleta.liberarParaViagem();
    Usuario usuario = new Usuario("Ana", "ana@teste.com", new PlanoAvulso());

    LocalDateTime inicio = LocalDateTime.of(2026, 1, 1, 9, 0);
    Viagem viagem = new Viagem(usuario, bicicleta, origem, inicio);
    viagem.encerrar(destino, inicio.plusMinutes(12));
    List<Viagem> viagens = new ArrayList<>();
    viagens.add(viagem);

    Faturamento faturamento = new Faturamento(viagens,
        LocalDateTime.of(2026, 1, 1, 0, 0),
        LocalDateTime.of(2026, 1, 1, 23, 59));

    assertEquals(1, faturamento.getQuantidadeTotal());
    assertEquals(2.80, faturamento.getValorTotal(), 0.001);
    assertEquals(1, faturamento.getItens().get(0).getQuantidade());
  }
}
