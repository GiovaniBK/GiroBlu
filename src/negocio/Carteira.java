package negocio;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** CAMADA DE NEGÓCIO Carteira de créditos de um usuário. */
public class Carteira {
    private BigDecimal saldo;
    private final List<Recarga> recargas;

    /** Cria uma carteira com saldo zero. */
    public Carteira() {
        this.saldo = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        this.recargas = new ArrayList<>();
    }

    /** @return saldo atual */ public BigDecimal getSaldo() { return saldo; }

    /** Credita uma recarga. @param recarga recarga válida */
    public void creditar(Recarga recarga) {
        if (recarga == null) throw new IllegalArgumentException("A recarga não pode ser nula.");
        recargas.add(recarga);
        saldo = saldo.add(recarga.getValor()).setScale(2, RoundingMode.HALF_UP);
    }

    /** Debita uma cobrança. @param valor valor não negativo */
    public void debitar(BigDecimal valor) {
        if (valor == null || valor.compareTo(BigDecimal.ZERO) < 0) throw new IllegalArgumentException("O valor a debitar não pode ser negativo.");
        saldo = saldo.subtract(valor).setScale(2, RoundingMode.HALF_UP);
    }

    /** @return cópia não modificável das recargas */
    public List<Recarga> getRecargas() { return Collections.unmodifiableList(new ArrayList<>(recargas)); }
}
