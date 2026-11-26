import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

import negocio.BicicletaEletrica;
import negocio.BicicletaMecanica;
import negocio.Estacao;
import negocio.PatineteEletrico;
import negocio.PlanoAvulso;
import negocio.PlanoMensal;
import negocio.PlanoUniversitario;
import negocio.Plataforma;
import negocio.Usuario;
import negocio.Viagem;
import negocio.excecao.SaldoInsuficienteException;

class PlataformaTest {
  @Test
  void cadastraSemDuplicarECobraOsCincoExemplosDeFluxo() {
    Plataforma plataforma = new Plataforma();
    Estacao origem = new Estacao("Origem", 5);
    Estacao destino = new Estacao("Destino", 5);
    plataforma.cadastrarEstacao(origem);
    plataforma.cadastrarEstacao(destino);

    executarExemplo(plataforma, origem, destino, new Usuario("Ana1", "1@teste.com", new PlanoAvulso()),
        new BicicletaMecanica("BM-1", "Mec"), 12, 2.80);
    executarExemplo(plataforma, origem, destino, new Usuario("Ana2", "2@teste.com", new PlanoUniversitario()),
        new BicicletaEletrica("BE-1", "Elétrica", 100), 25, 4.75);
    executarExemplo(plataforma, origem, destino, new Usuario("Ana3", "3@teste.com", new PlanoMensal()),
        new PatineteEletrico("PA-1", "Patinete", 100), 20, 2.00);
    executarExemplo(plataforma, origem, destino, new Usuario("Ana4", "4@teste.com", new PlanoUniversitario()),
        new PatineteEletrico("PA-2", "Patinete", 100), 10, 7.00);
    executarExemplo(plataforma, origem, destino, new Usuario("Ana5", "5@teste.com", new PlanoMensal()),
        new BicicletaMecanica("BM-2", "Mec"), 10, 0.00);

    assertEquals(5, plataforma.getViagens().size());
    assertEquals(5, plataforma.getUsuarios().size());
  }

  private void executarExemplo(Plataforma plataforma, Estacao origem, Estacao destino, Usuario usuario,
      negocio.Veiculo veiculo, int minutos, double esperado) {
    plataforma.cadastrarUsuario(usuario);
    plataforma.cadastrarVeiculo(veiculo, origem);
    plataforma.recarregarCarteira(usuario, LocalDateTime.of(2026, 1, 1, 8, 0), 50.00);
    LocalDateTime inicio = LocalDateTime.of(2026, 1, 1, 9, 0);
    Viagem viagem = plataforma.iniciarViagem(usuario, veiculo, origem, inicio);
    plataforma.encerrarViagem(viagem, destino, inicio.plusMinutes(minutos));
    assertEquals(esperado, viagem.getValorCobrado(), 0.001);
  }

  @Test
  void rejeitaEstacaoComNomeDuplicado() {
    Plataforma plataforma = new Plataforma();
    plataforma.cadastrarEstacao(new Estacao("Centro", 2));
    assertThrows(IllegalArgumentException.class,
        () -> plataforma.cadastrarEstacao(new Estacao("CENTRO", 3)));
  }

  @Test
  void rejeitaSaldoMenorQueCincoVeiculoNaOrigemErradaEDuplicidade() {
    Plataforma plataforma = new Plataforma();
    Estacao origem = new Estacao("Origem", 2);
    Estacao outra = new Estacao("Outra", 2);
    Usuario usuario = new Usuario("Ana", "ana@teste.com", new PlanoAvulso());
    plataforma.cadastrarUsuario(usuario);

    assertThrows(SaldoInsuficienteException.class, () -> plataforma.iniciarViagem(usuario,
        new BicicletaMecanica("BM-1", "Mec"), origem, LocalDateTime.now()));

    BicicletaMecanica bicicleta = new BicicletaMecanica("BM-2", "Mec");
    plataforma.cadastrarVeiculo(bicicleta, outra);
    plataforma.recarregarCarteira(usuario, LocalDateTime.now(), 10.00);
    assertThrows(IllegalArgumentException.class, () -> plataforma.iniciarViagem(usuario,
        bicicleta, origem, LocalDateTime.now()));

    assertThrows(IllegalArgumentException.class, () -> plataforma.cadastrarUsuario(
        new Usuario("Outra Ana", "ANA@TESTE.COM", new PlanoAvulso())));
  }
}
