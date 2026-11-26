import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import negocio.BicicletaEletrica;
import negocio.Veiculo;
import negocio.Estacao;
import negocio.excecao.EstacaoLotadaException;
import negocio.excecao.VeiculoIndisponivelException;

class BicicletaEletricaTest {
    @Test
    void liberaCom20ENaoLiberaCom19() {
        Estacao estacao = new Estacao("Centro", 5);
        BicicletaEletrica abaixo = new BicicletaEletrica("BE-19", "M", 19);
        estacao.estacionar(abaixo);
        assertThrows(VeiculoIndisponivelException.class, abaixo::liberarParaViagem);

        BicicletaEletrica limite = new BicicletaEletrica("BE-20", "M", 20);
        estacao.estacionar(limite);
        limite.liberarParaViagem();
        assertEquals(Veiculo.EM_USO, limite.getStatus());
    }

    @Test
    void devolucaoAtualizaBateriaNaoAlteraEmLotada() {
        Estacao origem = new Estacao("Origem", 5);
        Estacao destino = new Estacao("Destino", 0);
        BicicletaEletrica bicicleta = new BicicletaEletrica("BE-80", "M", 80);
        origem.estacionar(bicicleta);
        bicicleta.liberarParaViagem();
        assertThrows(EstacaoLotadaException.class, () -> bicicleta.devolver(destino, 25));
        assertEquals(80, bicicleta.getBateria());

        Estacao destinoLivre = new Estacao("Livre", 1);
        bicicleta.devolver(destinoLivre, 25);
        assertEquals(55, bicicleta.getBateria());
    }
}
