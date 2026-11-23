package apresentacao;

import javax.swing.JFrame;

/** CAMADA DE APRESENTAÇÃO Janela principal do painel GiroBlu. */
public class JanelaPrincipal extends JFrame {
    /** Cria a janela principal. */
    public JanelaPrincipal() {
        super("GiroBlu | Painel de Operação");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
    }
}
