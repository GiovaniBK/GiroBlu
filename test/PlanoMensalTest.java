import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import negocio.BicicletaMecanica;
import negocio.PatineteEletrico;
import negocio.PlanoMensal;

class PlanoMensalTest {
    @Test
    void primeiraFaixaGratuita() {
        BicicletaMecanica bicicleta = new BicicletaMecanica("BM-1", "Caloi");
        assertEquals(0.00, new PlanoMensal().calcularValor(bicicleta, 10), 0.001);
    }

    @Test
    void cobraApenasMinutosExcedentesDoPatinete() {
        PatineteEletrico patinete = new PatineteEletrico("PE-1", "M", 60);
        assertEquals(2.00, new PlanoMensal().calcularValor(patinete, 20), 0.001);
    }
}
