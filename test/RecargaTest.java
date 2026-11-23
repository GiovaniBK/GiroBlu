package negocio;

import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class RecargaTest {
    @Test void recargaValidaValorPositivo() {
        Recarga recarga = new Recarga(LocalDateTime.now(), new BigDecimal("10.555"));
        assertEquals(new BigDecimal("10.56"), recarga.getValor());
    }

    @Test void recargaRejeitaDadosInvalidos() {
        assertThrows(IllegalArgumentException.class, () -> new Recarga(null, new BigDecimal("10.00")));
        assertThrows(IllegalArgumentException.class, () -> new Recarga(LocalDateTime.now(), BigDecimal.ZERO));
        assertThrows(IllegalArgumentException.class, () -> new Recarga(LocalDateTime.now(), new BigDecimal("-1.00")));
    }
}
