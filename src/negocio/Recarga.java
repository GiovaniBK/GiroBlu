package negocio;

import java.time.LocalDateTime;

/** CAMADA DE NEGÓCIO Representa uma recarga. */
public class Recarga {
    private final LocalDateTime dataHora;
    private final double valor;

    /** Cria uma recarga. @param dataHora momento @param valor valor positivo */
    public Recarga(LocalDateTime dataHora, double valor) {
        if (dataHora == null)
            throw new IllegalArgumentException("A data e hora da recarga não podem ser nulas.");
        if (!Double.isFinite(valor) || valor <= 0)
            throw new IllegalArgumentException("O valor da recarga deve ser positivo.");
        this.dataHora = dataHora;
        this.valor = Dinheiro.arredondar(valor);
    }

    /** @return data e hora */
    public LocalDateTime getDataHora() {
        return dataHora;
    }

    /** @return valor */
    public double getValor() {
        return valor;
    }
}
