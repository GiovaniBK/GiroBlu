package negocio;

import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class BicicletaMecanicaTest {
    @Test void tarifaECodigoSaoDefinidos() {
        BicicletaMecanica bicicleta = new BicicletaMecanica("BM-1", "Caloi");
        assertEquals(new BigDecimal("1.00"), bicicleta.getValorDesbloqueio());
        assertEquals(new BigDecimal("0.15"), bicicleta.getValorPorMinuto());
        assertTrue(bicicleta.podeSerLiberado());
    }
}
