import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import negocio.Usuario;
import negocio.PlanoAvulso;

class UsuarioTest {
  @Test
  void usuarioComecaComCarteiraZerada() {
    Usuario usuario = new Usuario("Ana", "ana@giroblu.com", new PlanoAvulso());
    assertEquals("Ana", usuario.getNome());
    assertEquals("ana@giroblu.com", usuario.getEmail());
    assertEquals(0.00, usuario.getCarteira().getSaldo(), 0.001);
  }

  @Test
  void dadosObrigatoriosSaoValidados() {
    assertThrows(IllegalArgumentException.class, () -> new Usuario("", "ana@giroblu.com", new PlanoAvulso()));
    assertThrows(IllegalArgumentException.class, () -> new Usuario("Ana", null, new PlanoAvulso()));
    assertThrows(IllegalArgumentException.class, () -> new Usuario("Ana", "ana@giroblu.com", null));
  }
}
