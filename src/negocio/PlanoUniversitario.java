package negocio;

/**
 * CAMADA DE NEGÓCIO Plano universitário com cinquenta por cento de desconto
 * quando permitido pelo veículo.
 */
public class PlanoUniversitario implements Plano {
    @Override
    public double calcularValor(Veiculo veiculo, int minutos) {
        double valor = veiculo.calcularValorCheio(minutos);
        if (veiculo.aceitaDescontoUniversitario())
            valor = valor * 0.50;
        return Dinheiro.arredondar(valor);
    }
}
