package negocio;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

/** CAMADA DE NEGÓCIO Representa uma recarga. */
public class Recarga {
    private final LocalDateTime dataHora;
    private final BigDecimal valor;

    /** Cria uma recarga. @param dataHora momento @param valor valor positivo */
    public Recarga(LocalDateTime dataHora, BigDecimal valor) {
        if (dataHora == null) throw new IllegalArgumentException("A data e hora da recarga não podem ser nulas.");
        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) throw new IllegalArgumentException("O valor da recarga deve ser positivo.");
        this.dataHora = dataHora;
        this.valor = valor.setScale(2, RoundingMode.HALF_UP);
    }

    /** @return data e hora */ public LocalDateTime getDataHora() { return dataHora; }
    /** @return valor */ public BigDecimal getValor() { return valor; }
}
