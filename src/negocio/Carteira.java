package negocio;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/** CAMADA DE NEGÓCIO Carteira de créditos de um usuário. */
public class Carteira {
    private double saldo;
    private final List<Recarga> recargas;
    private final List<Lancamento> lancamentos;

    /** Cria uma carteira com saldo zero. */
    public Carteira() {
        this.saldo = 0.00;
        this.recargas = new ArrayList<>();
        this.lancamentos = new ArrayList<>();
    }

    /** @return saldo atual */
    public double getSaldo() {
        return saldo;
    }

    /** Credita uma recarga. @param recarga recarga válida */
    public void creditar(Recarga recarga) {
        if (recarga == null)
            throw new IllegalArgumentException("A recarga não pode ser nula.");
        recargas.add(recarga);
        saldo = Dinheiro.arredondar(saldo + recarga.getValor());
        lancamentos.add(new Lancamento(recarga.getDataHora(), "Recarga", recarga.getValor(), saldo));
    }

    /** Debita uma cobrança. @param valor valor não negativo */
    public void debitar(double valor) {
        if (!Double.isFinite(valor) || valor < 0)
            throw new IllegalArgumentException("O valor a debitar não pode ser negativo.");
        saldo = Dinheiro.arredondar(saldo - valor);
    }

    /**
     * Registra o débito de uma viagem no extrato. @param dataHora término da
     * viagem @param descricao descrição @param valor valor cobrado
     */
    public void debitarViagem(LocalDateTime dataHora, String descricao, double valor) {
        if (dataHora == null)
            throw new IllegalArgumentException("A data e hora da viagem não podem ser nulas.");
        if (descricao == null || descricao.isBlank())
            throw new IllegalArgumentException("A descrição da viagem não pode ser vazia.");
        if (!Double.isFinite(valor) || valor < 0)
            throw new IllegalArgumentException("O valor da viagem não pode ser negativo.");
        debitar(valor);
        lancamentos.add(new Lancamento(dataHora, descricao, -valor, saldo));
    }

    /** @return cópia não modificável das recargas */
    public List<Recarga> getRecargas() {
        return Collections.unmodifiableList(new ArrayList<>(recargas));
    }

    /** @return extrato em ordem cronológica */
    public List<Lancamento> getExtrato() {
        List<Lancamento> ordenado = new ArrayList<>(lancamentos);
        ordenado.sort(Comparator.comparing(Lancamento::getDataHora)
                .thenComparing(Lancamento::getDescricao));
        return Collections.unmodifiableList(ordenado);
    }
}
