package negocio.excecao;

/** Exceção lançada quando o saldo não atende à regra de viagem. */
public class SaldoInsuficienteException extends GiroBluException {
    /** @param mensagem descrição do saldo insuficiente */
    public SaldoInsuficienteException(String mensagem) { super(mensagem); }
}
