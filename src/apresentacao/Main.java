package apresentacao;

import javax.swing.SwingUtilities;

/** CAMADA DE APRESENTAÇÃO Ponto de entrada da aplicação GiroBlu. */
public class Main {
    public static final String CODIGO_HOMOLOGACAO = "GB-POO-26-0B7";

    /** Inicia a interface gráfica. @param args argumentos da linha de comando */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new JanelaPrincipal().setVisible(true));
    }
}
