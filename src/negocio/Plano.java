package negocio;

/** CAMADA DE NEGÓCIO Contrato para regras de cobrança de planos. */
public interface Plano {
    /**
     * Calcula o valor da viagem. @param veiculo veículo @param minutos
     * duração @return valor
     */
    double calcularValor(Veiculo veiculo, int minutos);
}
