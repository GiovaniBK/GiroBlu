import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import negocio.BicicletaMecanica;
import negocio.Estacao;
import negocio.excecao.EstacaoLotadaException;

class EstacaoTest {
    @Test void estacionarRejeitaVeiculoNuloEMUsoERepetido() {
        Estacao estacao = new Estacao("Centro", 2);
        assertThrows(IllegalArgumentException.class, () -> estacao.estacionar(null));

        BicicletaMecanica emUso = new BicicletaMecanica("BM-1", "Mec");
        estacao.estacionar(emUso);
        emUso.liberarParaViagem();
        assertThrows(IllegalArgumentException.class, () -> estacao.estacionar(emUso));

        BicicletaMecanica estacionado = new BicicletaMecanica("BM-2", "Mec");
        estacao.estacionar(estacionado);
        assertThrows(IllegalArgumentException.class, () -> estacao.estacionar(estacionado));
    }

    @Test void lotadaNaoEstacionaVeiculo() {
        Estacao estacao = new Estacao("Lotada", 1);
        estacao.estacionar(new BicicletaMecanica("BM-1", "Mec"));
        assertThrows(EstacaoLotadaException.class,
                () -> estacao.estacionar(new BicicletaMecanica("BM-2", "Mec")));
        assertEquals(0, estacao.getVagasLivres());
    }
}
