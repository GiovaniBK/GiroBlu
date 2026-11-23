package negocio;

import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class PlanoAvulsoTest {
    @Test void calculaPrecoCompletoDaBicicletaMecanica() {
        BicicletaMecanica bicicleta = new BicicletaMecanica("BM-1", "Caloi");
        assertEquals(new BigDecimal("2.80"), new PlanoAvulso().calcularValor(bicicleta, 12));
    }
}
