import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import negocio.Estacao;
import negocio.PatineteEletrico;

class PatineteEletricoTest {
    @Test void consumoDePatineteEValorMinimoDaBateria() {
        Estacao origem = new Estacao("Origem", 5);
        Estacao destino = new Estacao("Destino", 5);
        PatineteEletrico patinete = new PatineteEletrico("PE-20", "M", 20);
        origem.estacionar(patinete);
        patinete.liberarParaViagem();
        patinete.devolver(destino, 20);
        assertEquals(0, patinete.getBateria());
    }

    @Test void bateriaForaDeFaixaLancaExcecao() {
        assertThrows(IllegalArgumentException.class, () -> new PatineteEletrico("PE-1", "M", -1));
        assertThrows(IllegalArgumentException.class, () -> new PatineteEletrico("PE-2", "M", 101));
    }
}
