package negocio.excecao;

/** Exceção lançada quando um veículo não pode realizar uma operação. */
public class VeiculoIndisponivelException extends GiroBluException {
    /** @param mensagem descrição da indisponibilidade */
    public VeiculoIndisponivelException(String mensagem) { super(mensagem); }
}
