package apresentacao;

import javax.swing.SwingUtilities;
import negocio.Plataforma;

/** CAMADA DE APRESENTAÇÃO Ponto de entrada da aplicação GiroBlu. */
public class Main {
    /** Inicia a interface gráfica. @param args argumentos da linha de comando */
    public static void main(String[] args) {
        Plataforma plataforma = new Plataforma("data");
        SwingUtilities.invokeLater(() -> new JanelaPrincipal(plataforma).setVisible(true));
    }
}
