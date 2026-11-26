import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import negocio.BicicletaEletrica;
import negocio.BicicletaMecanica;
import negocio.PatineteEletrico;
import negocio.PlanoUniversitario;

class PlanoUniversitarioTest {
    @Test
    void aplicaDescontoNaBicicletaEletrica() {
        BicicletaEletrica bicicleta = new BicicletaEletrica("BE-1", "M", 80);
        assertEquals(4.75, new PlanoUniversitario().calcularValor(bicicleta, 25), 0.001);
    }

    @Test
    void patineteNaoRecebeDesconto() {
        PatineteEletrico patinete = new PatineteEletrico("PE-1", "M", 100);
        assertEquals(7.00, new PlanoUniversitario().calcularValor(patinete, 10), 0.001);
    }

    @Test
    void arredondaComHalfUp() {
        BicicletaMecanica bicicleta = new BicicletaMecanica("BM-1", "M");
        assertEquals(0.58, new PlanoUniversitario().calcularValor(bicicleta, 1), 0.001);
    }
}
