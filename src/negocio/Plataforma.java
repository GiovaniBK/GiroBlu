package negocio;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import negocio.excecao.SaldoInsuficienteException;

/** CAMADA DE NEGÓCIO Reúne cadastros e operações da plataforma GiroBlu. */
public class Plataforma {
  private final List<Estacao> estacoes = new ArrayList<>();
  private final List<Veiculo> veiculos = new ArrayList<>();
  private final List<Usuario> usuarios = new ArrayList<>();
  private final List<Viagem> viagens = new ArrayList<>();
  private persistencia.PersistenciaArquivo persistencia;
  private boolean carregando;

  /** Cria uma plataforma vazia para uso em memória e nos testes. */
  public Plataforma() {
  }

  /**
   * Cria uma plataforma usando um diretório de dados.
   * 
   * @param diretorioDados diretório dos arquivos CSV
   */
  public Plataforma(String diretorioDados) {
    if (diretorioDados == null || diretorioDados.isBlank())
      throw new IllegalArgumentException("O diretório de dados não pode ser vazio.");
    carregando = true;
    persistencia = new persistencia.PersistenciaArquivo(diretorioDados);
    persistencia.carregar(this);
    carregando = false;
  }

  private void salvarDados() {
    if (!carregando && persistencia != null)
      persistencia.salvar(this);
  }

  /**
   * Restaura uma viagem lida da persistência. @param viagem viagem já
   * reconstruída
   */
  public void restaurarViagem(Viagem viagem) {
    if (viagem == null)
      throw new IllegalArgumentException("A viagem não pode ser nula.");
    viagens.add(viagem);
  }

  /** Cadastra uma estação. @param estacao estação a cadastrar */
  public void cadastrarEstacao(Estacao estacao) {
    if (estacao == null)
      throw new IllegalArgumentException("A estação não pode ser nula.");
    for (Estacao existente : estacoes) {
      if (existente.getNome().equalsIgnoreCase(estacao.getNome()))
        throw new IllegalArgumentException("Já existe uma estação com este nome.");
    }
    estacoes.add(estacao);
    salvarDados();
  }

  /**
   * Cadastra um veículo e opcionalmente o estaciona.
   * 
   * @param veiculo        veículo a cadastrar
   * @param estacaoInicial estação inicial ou null
   */
  public void cadastrarVeiculo(Veiculo veiculo, Estacao estacaoInicial) {
    if (veiculo == null)
      throw new IllegalArgumentException("O veículo não pode ser nulo.");
    for (Veiculo existente : veiculos) {
      if (existente.getCodigo().equalsIgnoreCase(veiculo.getCodigo())) {
        throw new IllegalArgumentException("Já existe um veículo com este código.");
      }
    }
    if (estacaoInicial != null)
      estacaoInicial.estacionar(veiculo);
    veiculos.add(veiculo);
    salvarDados();
  }

  /** Cadastra um usuário. @param usuario usuário a cadastrar */
  public void cadastrarUsuario(Usuario usuario) {
    if (usuario == null)
      throw new IllegalArgumentException("O usuário não pode ser nulo.");
    for (Usuario existente : usuarios) {
      if (existente.getEmail().equalsIgnoreCase(usuario.getEmail())) {
        throw new IllegalArgumentException("Já existe um usuário com este e-mail.");
      }
    }
    usuarios.add(usuario);
    salvarDados();
  }

  /**
   * Credita uma recarga na carteira do usuário.
   * 
   * @param usuario  usuário
   * @param dataHora momento da recarga
   * @param valor    valor positivo
   */
  public void recarregarCarteira(Usuario usuario, LocalDateTime dataHora, double valor) {
    if (usuario == null)
      throw new IllegalArgumentException("O usuário não pode ser nulo.");
    usuario.getCarteira().creditar(new Recarga(dataHora, valor));
    salvarDados();
  }

  /**
   * Inicia uma viagem.
   * 
   * @param usuario usuário
   * @param veiculo veículo
   * @param origem  estação de origem
   * @param inicio  data e hora de início
   * @return viagem iniciada
   */
  public Viagem iniciarViagem(Usuario usuario, Veiculo veiculo, Estacao origem, LocalDateTime inicio) {
    if (usuario == null || veiculo == null || origem == null || inicio == null) {
      throw new IllegalArgumentException("Os dados da viagem não podem ser nulos.");
    }
    if (usuario.getCarteira().getSaldo() < 5.00) {
      throw new SaldoInsuficienteException("O usuário precisa ter ao menos R$ 5,00 para iniciar uma viagem.");
    }
    for (Viagem viagem : viagens) {
      if (viagem.getUsuario() == usuario && viagem.estaEmAndamento()) {
        throw new IllegalArgumentException("O usuário já possui uma viagem em andamento.");
      }
    }
    if (veiculo.getEstacaoAtual() != origem) {
      throw new IllegalArgumentException("O veículo não está na estação de origem informada.");
    }
    veiculo.liberarParaViagem();
    Viagem viagem = new Viagem(usuario, veiculo, origem, inicio);
    viagens.add(viagem);
    salvarDados();
    return viagem;
  }

  /**
   * Encerra uma viagem.
   * 
   * @param viagem  viagem em andamento
   * @param destino estação de destino
   * @param termino data e hora de término
   */
  public void encerrarViagem(Viagem viagem, Estacao destino, LocalDateTime termino) {
    if (viagem == null)
      throw new IllegalArgumentException("A viagem não pode ser nula.");
    if (!viagens.contains(viagem))
      throw new IllegalArgumentException("A viagem não pertence a esta plataforma.");
    if (!viagem.estaEmAndamento())
      throw new IllegalArgumentException("A viagem já foi encerrada.");
    viagem.encerrar(destino, termino);
    salvarDados();
  }

  /** Envia um veículo para manutenção. @param veiculo veículo */
  public void enviarParaManutencao(Veiculo veiculo) {
    if (veiculo == null)
      throw new IllegalArgumentException("O veículo não pode ser nulo.");
    veiculo.enviarParaManutencao();
    salvarDados();
  }

  /** Retorna um veículo da manutenção. @param veiculo veículo */
  public void voltarDaManutencao(Veiculo veiculo) {
    if (veiculo == null)
      throw new IllegalArgumentException("O veículo não pode ser nulo.");
    veiculo.voltarDaManutencao();
    salvarDados();
  }

  /** Recarrega um veículo elétrico. @param veiculo veículo elétrico */
  public void recarregarBateria(Veiculo veiculo) {
    if (veiculo == null)
      throw new IllegalArgumentException("O veículo não pode ser nulo.");
    veiculo.recarregar();
    salvarDados();
  }

  /**
   * Consulta veículos disponíveis em uma estação.
   * 
   * @param estacao estação consultada
   * @return veículos disponíveis
   */
  public List<Veiculo> consultarVeiculosDisponiveis(Estacao estacao) {
    if (estacao == null)
      throw new IllegalArgumentException("A estação não pode ser nula.");
    return estacao.consultarDisponiveis();
  }

  /**
   * Busca uma estação pelo nome. @param nome nome da estação @return estação
   * encontrada ou null
   */
  public Estacao buscarEstacaoPorNome(String nome) {
    if (nome == null || nome.isBlank())
      return null;
    for (Estacao estacao : estacoes)
      if (estacao.getNome().equalsIgnoreCase(nome.trim()))
        return estacao;
    return null;
  }

  /**
   * Busca um usuário pelo e-mail. @param email e-mail do usuário @return usuário
   * encontrado ou null
   */
  public Usuario buscarUsuarioPorEmail(String email) {
    if (email == null || email.isBlank())
      return null;
    for (Usuario usuario : usuarios)
      if (usuario.getEmail().equalsIgnoreCase(email.trim()))
        return usuario;
    return null;
  }

  /**
   * Busca um veículo pelo código. @param codigo código do veículo @return veículo
   * encontrado ou null
   */
  public Veiculo buscarVeiculoPorCodigo(String codigo) {
    if (codigo == null || codigo.isBlank())
      return null;
    for (Veiculo veiculo : veiculos)
      if (veiculo.getCodigo().equalsIgnoreCase(codigo.trim()))
        return veiculo;
    return null;
  }

  /**
   * Busca a viagem em andamento de um usuário.
   * 
   * @param usuario usuário
   * @return viagem em andamento ou null
   */
  public Viagem buscarViagemEmAndamento(Usuario usuario) {
    if (usuario == null)
      return null;
    for (Viagem viagem : viagens)
      if (viagem.getUsuario() == usuario && viagem.estaEmAndamento())
        return viagem;
    return null;
  }

  /** Obtém o extrato de um usuário. @param usuario usuário @return lançamentos */
  public List<Lancamento> consultarExtrato(Usuario usuario) {
    if (usuario == null)
      throw new IllegalArgumentException("O usuário não pode ser nulo.");
    return usuario.getCarteira().getExtrato();
  }

  /**
   * Calcula o faturamento de um período.
   * 
   * @param inicio início inclusivo
   * @param fim    fim inclusivo
   * @return faturamento consolidado
   */
  public Faturamento calcularFaturamento(LocalDateTime inicio, LocalDateTime fim) {
    return new Faturamento(viagens, inicio, fim);
  }

  /** @return cópia das estações */
  public List<Estacao> getEstacoes() {
    return Collections.unmodifiableList(new ArrayList<>(estacoes));
  }

  /** @return cópia dos veículos */
  public List<Veiculo> getVeiculos() {
    return Collections.unmodifiableList(new ArrayList<>(veiculos));
  }

  /** @return cópia dos usuários */
  public List<Usuario> getUsuarios() {
    return Collections.unmodifiableList(new ArrayList<>(usuarios));
  }

  /** @return cópia das viagens */
  public List<Viagem> getViagens() {
    return Collections.unmodifiableList(new ArrayList<>(viagens));
  }
}
