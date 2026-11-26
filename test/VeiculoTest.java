import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import negocio.BicicletaMecanica;
import negocio.Estacao;
import negocio.Veiculo;
import negocio.excecao.VeiculoIndisponivelException;

class VeiculoTest {
    private Estacao origem;

    @BeforeEach
    void preparar() {
        origem = new Estacao("FURB", 5);
    }

    @Test
    void emUsoNaoPermaneceNaEstacao() {
        BicicletaMecanica veiculo = new BicicletaMecanica("BM-1", "Caloi");
        origem.estacionar(veiculo);
        veiculo.liberarParaViagem();
        assertEquals(Veiculo.EM_USO, veiculo.getStatus());
        assertNull(veiculo.getEstacaoAtual());
        assertEquals(0, origem.getQuantidadeVeiculosEstacionados());
    }

    @Test
    void voltarDaManutencaoExigeManutencao() {
        BicicletaMecanica veiculo = new BicicletaMecanica("BM-2", "Caloi");
        origem.estacionar(veiculo);
        assertThrows(VeiculoIndisponivelException.class, veiculo::voltarDaManutencao);
        veiculo.enviarParaManutencao();
        veiculo.voltarDaManutencao();
        assertEquals(Veiculo.DISPONIVEL, veiculo.getStatus());
    }
}
