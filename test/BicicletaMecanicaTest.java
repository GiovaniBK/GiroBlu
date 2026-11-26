import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import negocio.BicicletaMecanica;

class BicicletaMecanicaTest {
    @Test
    void tarifaECodigoSaoDefinidos() {
        BicicletaMecanica bicicleta = new BicicletaMecanica("BM-1", "Caloi");
        assertEquals(1.00, bicicleta.getValorDesbloqueio(), 0.001);
        assertEquals(0.15, bicicleta.getValorPorMinuto(), 0.001);
        assertTrue(bicicleta.podeSerLiberado());
    }
}
