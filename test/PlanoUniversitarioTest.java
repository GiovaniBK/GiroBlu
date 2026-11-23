package negocio;

import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class PlanoUniversitarioTest {
    @Test void aplicaDescontoNaBicicletaEletrica() {
        BicicletaEletrica bicicleta = new BicicletaEletrica("BE-1", "M", 80);
        assertEquals(new BigDecimal("4.75"), new PlanoUniversitario().calcularValor(bicicleta, 25));
    }

    @Test void patineteNaoRecebeDesconto() {
        PatineteEletrico patinete = new PatineteEletrico("PE-1", "M", 100);
        assertEquals(new BigDecimal("7.00"), new PlanoUniversitario().calcularValor(patinete, 10));
    }

    @Test void arredondaComHalfUp() {
        BicicletaMecanica bicicleta = new BicicletaMecanica("BM-1", "M");
        assertEquals(new BigDecimal("0.58"), new PlanoUniversitario().calcularValor(bicicleta, 1));
    }
}
