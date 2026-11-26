package apresentacao;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import negocio.BicicletaEletrica;
import negocio.BicicletaMecanica;
import negocio.Estacao;
import negocio.Dinheiro;
import negocio.Faturamento;
import negocio.Lancamento;
import negocio.PatineteEletrico;
import negocio.PlanoAvulso;
import negocio.PlanoMensal;
import negocio.PlanoUniversitario;
import negocio.Plataforma;
import negocio.Usuario;
import negocio.Veiculo;
import negocio.Viagem;
import negocio.excecao.GiroBluException;

/** CAMADA DE APRESENTAÇÃO Interface Swing simples do painel GiroBlu. */
public class JanelaPrincipal extends JFrame {
    private final Plataforma plataforma;

    /**
     * Cria a janela com todas as operações da plataforma.
     * 
     * @param plataforma plataforma compartilhada pela aplicação
     */
    public JanelaPrincipal(Plataforma plataforma) {
        super("GiroBlu");
        if (plataforma == null)
            throw new IllegalArgumentException("A plataforma não pode ser nula.");
        this.plataforma = plataforma;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 650);
        setLocationRelativeTo(null);

        JTabbedPane abas = new JTabbedPane();
        abas.addTab("Cadastros", criarCadastros());
        abas.addTab("Recarga", criarRecarga());
        abas.addTab("Viagem", criarViagem());
        abas.addTab("Operações", criarOperacoes());
        abas.addTab("Consultas", criarConsulta());
        abas.addTab("Extrato", criarExtrato());
        abas.addTab("Faturamento", criarFaturamento());
        add(abas, BorderLayout.CENTER);
    }

    private JPanel criarCadastros() {
        JPanel painel = new JPanel(new GridLayout(1, 3, 8, 8));

        JPanel painelEstacao = new JPanel(new GridLayout(5, 2, 5, 5));
        painelEstacao.add(new JLabel("Cadastro de estação"));
        painelEstacao.add(new JLabel(""));
        JTextField nomeEstacao = new JTextField();
        JTextField vagasEstacao = new JTextField();
        painelEstacao.add(new JLabel("Nome:"));
        painelEstacao.add(nomeEstacao);
        painelEstacao.add(new JLabel("Vagas:"));
        painelEstacao.add(vagasEstacao);
        JButton salvarEstacao = new JButton("Cadastrar");
        painelEstacao.add(new JLabel(""));
        painelEstacao.add(salvarEstacao);

        salvarEstacao.addActionListener(evento -> executar(() -> {
            Estacao estacao = new Estacao(nomeEstacao.getText(),
                    Integer.parseInt(vagasEstacao.getText().trim()));
            plataforma.cadastrarEstacao(estacao);
            mostrar("Estação cadastrada.");
        }));

        JPanel painelVeiculo = new JPanel(new GridLayout(7, 2, 5, 5));
        painelVeiculo.add(new JLabel("Cadastro de veículo"));
        painelVeiculo.add(new JLabel(""));
        JTextField codigoVeiculo = new JTextField();
        JTextField modeloVeiculo = new JTextField();
        JTextField estacaoVeiculo = new JTextField();
        JTextField bateriaVeiculo = new JTextField();
        JComboBox<String> tipoVeiculo = new JComboBox<>(new String[] {
                "Bicicleta mecânica", "Bicicleta elétrica", "Patinete elétrico" });
        painelVeiculo.add(new JLabel("Código:"));
        painelVeiculo.add(codigoVeiculo);
        painelVeiculo.add(new JLabel("Modelo:"));
        painelVeiculo.add(modeloVeiculo);
        painelVeiculo.add(new JLabel("Estação:"));
        painelVeiculo.add(estacaoVeiculo);
        painelVeiculo.add(new JLabel("Bateria:"));
        painelVeiculo.add(bateriaVeiculo);
        painelVeiculo.add(new JLabel("Tipo:"));
        painelVeiculo.add(tipoVeiculo);
        JButton salvarVeiculo = new JButton("Cadastrar");
        painelVeiculo.add(new JLabel(""));
        painelVeiculo.add(salvarVeiculo);

        salvarVeiculo.addActionListener(evento -> executar(() -> {
            Estacao estacao = plataforma.buscarEstacaoPorNome(estacaoVeiculo.getText());
            if (estacao == null)
                throw new IllegalArgumentException("Estação não encontrada.");

            Veiculo veiculo;
            String tipo = (String) tipoVeiculo.getSelectedItem();
            if ("Bicicleta mecânica".equals(tipo)) {
                veiculo = new BicicletaMecanica(codigoVeiculo.getText(), modeloVeiculo.getText());
            } else if ("Bicicleta elétrica".equals(tipo)) {
                veiculo = new BicicletaEletrica(codigoVeiculo.getText(), modeloVeiculo.getText(),
                        Integer.parseInt(bateriaVeiculo.getText().trim()));
            } else {
                veiculo = new PatineteEletrico(codigoVeiculo.getText(), modeloVeiculo.getText(),
                        Integer.parseInt(bateriaVeiculo.getText().trim()));
            }
            plataforma.cadastrarVeiculo(veiculo, estacao);
            mostrar("Veículo cadastrado.");
        }));

        JPanel painelUsuario = new JPanel(new GridLayout(6, 2, 5, 5));
        painelUsuario.add(new JLabel("Cadastro de usuário"));
        painelUsuario.add(new JLabel(""));
        JTextField nomeUsuario = new JTextField();
        JTextField emailUsuario = new JTextField();
        JComboBox<String> planoUsuario = new JComboBox<>(new String[] {
                "Avulso", "Mensal", "Universitário" });
        painelUsuario.add(new JLabel("Nome:"));
        painelUsuario.add(nomeUsuario);
        painelUsuario.add(new JLabel("E-mail:"));
        painelUsuario.add(emailUsuario);
        painelUsuario.add(new JLabel("Plano:"));
        painelUsuario.add(planoUsuario);
        JButton salvarUsuario = new JButton("Cadastrar");
        painelUsuario.add(new JLabel(""));
        painelUsuario.add(salvarUsuario);

        salvarUsuario.addActionListener(evento -> executar(() -> {
            Usuario usuario;
            String plano = (String) planoUsuario.getSelectedItem();
            if ("Mensal".equals(plano))
                usuario = new Usuario(nomeUsuario.getText(), emailUsuario.getText(), new PlanoMensal());
            else if ("Universitário".equals(plano))
                usuario = new Usuario(nomeUsuario.getText(), emailUsuario.getText(), new PlanoUniversitario());
            else
                usuario = new Usuario(nomeUsuario.getText(), emailUsuario.getText(), new PlanoAvulso());
            plataforma.cadastrarUsuario(usuario);
            mostrar("Usuário cadastrado.");
        }));

        painel.add(painelEstacao);
        painel.add(painelVeiculo);
        painel.add(painelUsuario);
        return painel;
    }

    private JPanel criarRecarga() {
        JPanel painel = new JPanel(new GridLayout(4, 2, 5, 5));
        JTextField email = new JTextField();
        JTextField valor = new JTextField();
        JTextField data = new JTextField();
        painel.add(new JLabel("E-mail:"));
        painel.add(email);
        painel.add(new JLabel("Valor:"));
        painel.add(valor);
        painel.add(new JLabel("Data/hora AAAA-MM-DDTHH:MM:"));
        painel.add(data);
        JButton salvar = new JButton("Creditar");
        painel.add(new JLabel(""));
        painel.add(salvar);

        salvar.addActionListener(evento -> executar(() -> {
            Usuario usuario = plataforma.buscarUsuarioPorEmail(email.getText());
            if (usuario == null)
                throw new IllegalArgumentException("Usuário não encontrado.");
            plataforma.recarregarCarteira(usuario,
                    LocalDateTime.parse(data.getText().trim()),
                    Double.parseDouble(valor.getText().trim()));
            mostrar("Recarga realizada. Saldo: R$ " + Dinheiro.formatar(usuario.getCarteira().getSaldo()));
        }));
        return painel;
    }

    private JPanel criarViagem() {
        JPanel painel = new JPanel(new GridLayout(1, 2, 10, 10));

        JPanel inicio = new JPanel(new GridLayout(5, 2, 5, 5));
        JTextField emailInicio = new JTextField();
        JTextField codigoInicio = new JTextField();
        JTextField origemInicio = new JTextField();
        JTextField dataInicio = new JTextField();
        inicio.add(new JLabel("E-mail:"));
        inicio.add(emailInicio);
        inicio.add(new JLabel("Código do veículo:"));
        inicio.add(codigoInicio);
        inicio.add(new JLabel("Estação de origem:"));
        inicio.add(origemInicio);
        inicio.add(new JLabel("Data/hora:"));
        inicio.add(dataInicio);
        JButton iniciar = new JButton("Iniciar viagem");
        inicio.add(iniciar);
        inicio.add(new JLabel(""));

        iniciar.addActionListener(evento -> executar(() -> {
            Usuario usuario = plataforma.buscarUsuarioPorEmail(emailInicio.getText());
            if (usuario == null)
                throw new IllegalArgumentException("Usuário não encontrado.");
            Veiculo veiculo = plataforma.buscarVeiculoPorCodigo(codigoInicio.getText());
            if (veiculo == null)
                throw new IllegalArgumentException("Veículo não encontrado.");
            Estacao origem = plataforma.buscarEstacaoPorNome(origemInicio.getText());
            if (origem == null)
                throw new IllegalArgumentException("Estação não encontrada.");
            Viagem viagem = plataforma.iniciarViagem(usuario, veiculo, origem,
                    LocalDateTime.parse(dataInicio.getText().trim()));
            mostrar("Viagem iniciada: " + viagem.getVeiculo().getCodigo());
        }));

        JPanel fim = new JPanel(new GridLayout(4, 2, 5, 5));
        JTextField emailFim = new JTextField();
        JTextField destinoFim = new JTextField();
        JTextField dataFim = new JTextField();
        fim.add(new JLabel("E-mail:"));
        fim.add(emailFim);
        fim.add(new JLabel("Estação de destino:"));
        fim.add(destinoFim);
        fim.add(new JLabel("Data/hora:"));
        fim.add(dataFim);
        JButton encerrar = new JButton("Encerrar viagem");
        fim.add(encerrar);
        fim.add(new JLabel(""));

        encerrar.addActionListener(evento -> executar(() -> {
            Usuario usuario = plataforma.buscarUsuarioPorEmail(emailFim.getText());
            if (usuario == null)
                throw new IllegalArgumentException("Usuário não encontrado.");
            Estacao destino = plataforma.buscarEstacaoPorNome(destinoFim.getText());
            if (destino == null)
                throw new IllegalArgumentException("Estação não encontrada.");
            Viagem viagem = plataforma.buscarViagemEmAndamento(usuario);
            if (viagem == null)
                throw new IllegalArgumentException("Não há viagem em andamento.");
            plataforma.encerrarViagem(viagem, destino, LocalDateTime.parse(dataFim.getText().trim()));
            mostrar("Viagem encerrada. Valor: R$ " + Dinheiro.formatar(viagem.getValorCobrado()));
        }));

        painel.add(inicio);
        painel.add(fim);
        return painel;
    }

    private JPanel criarOperacoes() {
        JPanel painel = new JPanel(new GridLayout(5, 2, 5, 5));
        JTextField codigo = new JTextField();
        painel.add(new JLabel("Código do veículo:"));
        painel.add(codigo);

        JButton manutencao = new JButton("Enviar para manutenção");
        JButton voltar = new JButton("Voltar da manutenção");
        JButton recarregar = new JButton("Recarregar bateria");
        painel.add(manutencao);
        painel.add(voltar);
        painel.add(recarregar);
        painel.add(new JLabel(""));
        painel.add(new JLabel(""));
        painel.add(new JLabel(""));

        manutencao.addActionListener(evento -> executar(() -> {
            Veiculo veiculo = buscarVeiculo(codigo.getText());
            plataforma.enviarParaManutencao(veiculo);
            mostrar("Veículo enviado para manutenção.");
        }));
        voltar.addActionListener(evento -> executar(() -> {
            Veiculo veiculo = buscarVeiculo(codigo.getText());
            plataforma.voltarDaManutencao(veiculo);
            mostrar("Veículo voltou da manutenção.");
        }));
        recarregar.addActionListener(evento -> executar(() -> {
            Veiculo veiculo = buscarVeiculo(codigo.getText());
            plataforma.recarregarBateria(veiculo);
            mostrar("Bateria recarregada para 100%. ");
        }));
        return painel;
    }

    private JPanel criarConsulta() {
        JPanel painel = new JPanel(new BorderLayout(5, 5));
        JPanel topo = new JPanel();
        JTextField estacao = new JTextField(20);
        JButton consultar = new JButton("Consultar");
        topo.add(new JLabel("Estação:"));
        topo.add(estacao);
        topo.add(consultar);
        JTextArea resultado = new JTextArea();
        resultado.setEditable(false);

        consultar.addActionListener(evento -> executar(() -> {
            Estacao estacaoEncontrada = plataforma.buscarEstacaoPorNome(estacao.getText());
            if (estacaoEncontrada == null)
                throw new IllegalArgumentException("Estação não encontrada.");
            String texto = "";
            for (Veiculo veiculo : plataforma.consultarVeiculosDisponiveis(estacaoEncontrada))
                texto += veiculo + System.lineSeparator();
            resultado.setText(texto.isEmpty() ? "Nenhum veículo disponível." : texto);
        }));

        painel.add(topo, BorderLayout.NORTH);
        painel.add(new JScrollPane(resultado), BorderLayout.CENTER);
        return painel;
    }

    private JPanel criarExtrato() {
        JPanel painel = new JPanel(new BorderLayout(5, 5));
        JPanel topo = new JPanel();
        JTextField email = new JTextField(25);
        JButton consultar = new JButton("Consultar");
        topo.add(new JLabel("E-mail:"));
        topo.add(email);
        topo.add(consultar);
        JTextArea resultado = new JTextArea();
        resultado.setEditable(false);

        consultar.addActionListener(evento -> executar(() -> {
            Usuario usuario = plataforma.buscarUsuarioPorEmail(email.getText());
            if (usuario == null)
                throw new IllegalArgumentException("Usuário não encontrado.");
            String texto = "";
            for (Lancamento lancamento : plataforma.consultarExtrato(usuario))
                texto += lancamento.getDataHora() + " | " + lancamento.getDescricao()
                        + " | R$ " + Dinheiro.formatar(lancamento.getValor())
                        + " | Saldo R$ " + Dinheiro.formatar(lancamento.getSaldoDepois())
                        + System.lineSeparator();
            resultado.setText(texto.isEmpty() ? "Nenhum lançamento." : texto);
        }));

        painel.add(topo, BorderLayout.NORTH);
        painel.add(new JScrollPane(resultado), BorderLayout.CENTER);
        return painel;
    }

    private JPanel criarFaturamento() {
        JPanel painel = new JPanel(new BorderLayout(5, 5));
        JPanel topo = new JPanel();
        JTextField inicio = new JTextField(18);
        JTextField fim = new JTextField(18);
        JButton consultar = new JButton("Consultar");
        topo.add(new JLabel("Início:"));
        topo.add(inicio);
        topo.add(new JLabel("Fim:"));
        topo.add(fim);
        topo.add(consultar);
        JTextArea resultado = new JTextArea();
        resultado.setEditable(false);

        consultar.addActionListener(evento -> executar(() -> {
            Faturamento faturamento = plataforma.calcularFaturamento(
                    LocalDateTime.parse(inicio.getText().trim()),
                    LocalDateTime.parse(fim.getText().trim()));
            String texto = "";
            for (Faturamento.ItemFaturamento item : faturamento.getItens())
                texto += item.getTipo() + " | Viagens: " + item.getQuantidade()
                        + " | R$ " + Dinheiro.formatar(item.getValor()) + System.lineSeparator();
            texto += "TOTAL | Viagens: " + faturamento.getQuantidadeTotal()
                    + " | R$ " + Dinheiro.formatar(faturamento.getValorTotal());
            resultado.setText(texto);
        }));

        painel.add(topo, BorderLayout.NORTH);
        painel.add(new JScrollPane(resultado), BorderLayout.CENTER);
        return painel;
    }

    private Veiculo buscarVeiculo(String codigo) {
        Veiculo veiculo = plataforma.buscarVeiculoPorCodigo(codigo);
        if (veiculo == null)
            throw new IllegalArgumentException("Veículo não encontrado.");
        return veiculo;
    }

    private void executar(Runnable acao) {
        try {
            acao.run();
        } catch (GiroBluException | IllegalArgumentException | DateTimeParseException erro) {
            JOptionPane.showMessageDialog(this, erro.getMessage(),
                    "Erro no GiroBlu", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void mostrar(String mensagem) {
        JOptionPane.showMessageDialog(this, mensagem,
                "GiroBlu", JOptionPane.INFORMATION_MESSAGE);
    }
}
