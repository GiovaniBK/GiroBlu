import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import negocio.BicicletaMecanica;
import negocio.PlanoAvulso;

class PlanoAvulsoTest {
    @Test
    void calculaPrecoCompletoDaBicicletaMecanica() {
        BicicletaMecanica bicicleta = new BicicletaMecanica("BM-1", "Caloi");
        assertEquals(2.80, new PlanoAvulso().calcularValor(bicicleta, 12), 0.001);
    }
}
