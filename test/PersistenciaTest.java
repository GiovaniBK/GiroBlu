import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import negocio.BicicletaMecanica;
import negocio.Estacao;
import negocio.PlanoAvulso;
import negocio.Plataforma;
import negocio.Usuario;
import negocio.Veiculo;
import negocio.Viagem;

class PersistenciaTest {
  @Test
  void carregaDadosDeExemploComEstadosEReferencias() {
    Plataforma plataforma = new Plataforma("data");

    assertEquals(3, plataforma.getEstacoes().size());
    assertEquals(5, plataforma.getVeiculos().size());
    assertEquals(3, plataforma.getUsuarios().size());
    assertEquals(2, plataforma.getViagens().size());

    assertTrue(plataforma.getViagens().stream().anyMatch(Viagem::estaEmAndamento));
    assertTrue(plataforma.getVeiculos().stream()
        .anyMatch(veiculo -> Veiculo.EM_MANUTENCAO.equals(veiculo.getStatus())));
  }

  @Test
  void gravaECarregaViagemEncerradaViagemEmAndamentoERecargas() throws Exception {
    Path diretorio = Files.createTempDirectory("giroblu-persistencia-");
    Plataforma plataforma = new Plataforma(diretorio.toString());

    Estacao origem = new Estacao("Centro", 3);
    Estacao destino = new Estacao("Praia", 3);
    plataforma.cadastrarEstacao(origem);
    plataforma.cadastrarEstacao(destino);

    Usuario usuario = new Usuario("Ana", "ana@teste.com", new PlanoAvulso());
    plataforma.cadastrarUsuario(usuario);
    plataforma.recarregarCarteira(usuario, LocalDateTime.of(2026, 11, 25, 8, 0), 20.00);

    BicicletaMecanica encerrada = new BicicletaMecanica("BM-1", "Urbana");
    plataforma.cadastrarVeiculo(encerrada, origem);
    LocalDateTime inicio1 = LocalDateTime.of(2026, 11, 25, 9, 0);
    Viagem viagemEncerrada = plataforma.iniciarViagem(usuario, encerrada, origem, inicio1);
    plataforma.encerrarViagem(viagemEncerrada, destino, inicio1.plusMinutes(12));

    BicicletaMecanica emUso = new BicicletaMecanica("BM-2", "Passeio");
    plataforma.cadastrarVeiculo(emUso, origem);
    plataforma.recarregarCarteira(usuario, LocalDateTime.of(2026, 11, 25, 10, 0), 10.00);
    LocalDateTime inicio2 = LocalDateTime.of(2026, 11, 25, 11, 0);
    plataforma.iniciarViagem(usuario, emUso, origem, inicio2);

    Plataforma restaurada = new Plataforma(diretorio.toString());

    assertEquals(2, restaurada.getEstacoes().size());
    assertEquals(2, restaurada.getVeiculos().size());
    assertEquals(1, restaurada.getUsuarios().size());
    assertEquals(2, restaurada.getViagens().size());
    assertEquals(2, restaurada.getUsuarios().get(0).getCarteira().getRecargas().size());

    negocio.Veiculo restauradaEmUso = restaurada.getVeiculos().stream()
        .filter(veiculo -> veiculo.getCodigo().equals("BM-2"))
        .findFirst().orElseThrow();
    assertEquals(Veiculo.EM_USO, restauradaEmUso.getStatus());

    Viagem restauradaEmAndamento = restaurada.getViagens().stream()
        .filter(Viagem::estaEmAndamento)
        .findFirst().orElseThrow();
    assertEquals("BM-2", restauradaEmAndamento.getVeiculo().getCodigo());

    assertEquals(27.20,
        restaurada.getUsuarios().get(0).getCarteira().getSaldo(), 0.001);
  }
}
