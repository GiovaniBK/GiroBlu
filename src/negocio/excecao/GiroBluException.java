package negocio.excecao;

/** Superclasse das exceções de regra de negócio. */
public class GiroBluException extends RuntimeException {
    /** @param mensagem descrição da violação */
    public GiroBluException(String mensagem) { super(mensagem); }
}
