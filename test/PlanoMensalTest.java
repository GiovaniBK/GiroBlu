package negocio;

import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class PlanoMensalTest {
    @Test void primeiraFaixaGratuita() {
        BicicletaMecanica bicicleta = new BicicletaMecanica("BM-1", "Caloi");
        assertEquals(new BigDecimal("0.00"), new PlanoMensal().calcularValor(bicicleta, 10));
    }

    @Test void cobraApenasMinutosExcedentesDoPatinete() {
        PatineteEletrico patinete = new PatineteEletrico("PE-1", "M", 60);
        assertEquals(new BigDecimal("2.00"), new PlanoMensal().calcularValor(patinete, 20));
    }
}
