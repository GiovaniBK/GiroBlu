import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

import negocio.Carteira;
import negocio.Recarga;

class CarteiraTest {
    @Test
    void carteiraCreditaEDebitaPermitindoSaldoNegativo() {
        Carteira carteira = new Carteira();
        assertEquals(0.00, carteira.getSaldo(), 0.001);
        carteira.creditar(new Recarga(LocalDateTime.now(), 10.00));
        carteira.debitar(12.50);
        assertEquals(-2.50, carteira.getSaldo(), 0.001);
    }

    @Test
    void creditarNuloRejeitaECopiaDeRecargasNaoPodeSerAlterada() {
        Carteira carteira = new Carteira();
        assertThrows(IllegalArgumentException.class, () -> carteira.creditar(null));
        carteira.creditar(new Recarga(LocalDateTime.now(), 5.00));
        assertThrows(UnsupportedOperationException.class, () -> carteira.getRecargas().clear());
    }
}
