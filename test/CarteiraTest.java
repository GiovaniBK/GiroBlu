package negocio;

import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class CarteiraTest {
    @Test void carteiraCreditaEDebitaPermitindoSaldoNegativo() {
        Carteira carteira = new Carteira();
        assertEquals(new BigDecimal("0.00"), carteira.getSaldo());
        carteira.creditar(new Recarga(LocalDateTime.now(), new BigDecimal("10.00")));
        carteira.debitar(new BigDecimal("12.50"));
        assertEquals(new BigDecimal("-2.50"), carteira.getSaldo());
    }

    @Test void creditarNuloRejeitaECopiaDeRecargasNaoPodeSerAlterada() {
        Carteira carteira = new Carteira();
        assertThrows(IllegalArgumentException.class, () -> carteira.creditar(null));
        carteira.creditar(new Recarga(LocalDateTime.now(), new BigDecimal("5.00")));
        assertThrows(UnsupportedOperationException.class, () -> carteira.getRecargas().clear());
    }
}
