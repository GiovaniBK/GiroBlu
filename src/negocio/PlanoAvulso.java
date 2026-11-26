package negocio;

/** CAMADA DE NEGÓCIO Plano avulso com tarifa completa. */
public class PlanoAvulso implements Plano {
    @Override
    public double calcularValor(Veiculo veiculo, int minutos) {
        return veiculo.calcularValorCheio(minutos);
    }
}
