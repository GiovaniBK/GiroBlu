package negocio.excecao;

/** Exceção lançada quando não há vaga em uma estação. */
public class EstacaoLotadaException extends GiroBluException {
    /** @param mensagem descrição da lotação */
    public EstacaoLotadaException(String mensagem) { super(mensagem); }
}
