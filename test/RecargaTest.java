import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

import negocio.Recarga;

class RecargaTest {
    @Test
    void recargaValidaValorPositivo() {
        Recarga recarga = new Recarga(LocalDateTime.now(), 10.555);
        assertEquals(10.56, recarga.getValor(), 0.001);
    }

    @Test
    void recargaRejeitaDadosInvalidos() {
        assertThrows(IllegalArgumentException.class, () -> new Recarga(null, 10.00));
        assertThrows(IllegalArgumentException.class, () -> new Recarga(LocalDateTime.now(), 0.00));
        assertThrows(IllegalArgumentException.class, () -> new Recarga(LocalDateTime.now(), -1.00));
    }
}
