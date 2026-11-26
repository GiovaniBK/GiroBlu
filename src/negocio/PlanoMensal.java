package negocio;

/**
 * CAMADA DE NEGÓCIO Plano mensal sem desbloqueio e com quinze minutos
 * gratuitos.
 */
public class PlanoMensal implements Plano {
    @Override
    public double calcularValor(Veiculo veiculo, int minutos) {
        if (minutos < 0)
            throw new IllegalArgumentException("Os minutos da viagem não podem ser negativos.");
        if (minutos <= 15)
            return 0.00;
        double valor = veiculo.getValorPorMinuto() * (minutos - 15);
        return Dinheiro.arredondar(valor);
    }
}
