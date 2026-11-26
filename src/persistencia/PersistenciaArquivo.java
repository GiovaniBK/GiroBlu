package persistencia;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import negocio.BicicletaEletrica;
import negocio.BicicletaMecanica;
import negocio.Dinheiro;
import negocio.Estacao;
import negocio.PatineteEletrico;
import negocio.Plano;
import negocio.PlanoAvulso;
import negocio.PlanoMensal;
import negocio.PlanoUniversitario;
import negocio.Plataforma;
import negocio.Recarga;
import negocio.Usuario;
import negocio.Veiculo;
import negocio.Viagem;

/** CAMADA DE PERSISTÊNCIA Grava e restaura os dados do GiroBlu em CSV. */
public class PersistenciaArquivo {
  private final Path diretorio;

  /** Cria o gravador no diretório informado. @param diretorioDados diretório */
  public PersistenciaArquivo(String diretorioDados) {
    if (diretorioDados == null || diretorioDados.isBlank())
      throw new IllegalArgumentException("O diretório de dados não pode ser vazio.");
    diretorio = Paths.get(diretorioDados);
    try {
      Files.createDirectories(diretorio);
    } catch (IOException excecao) {
      throw new IllegalArgumentException("Não foi possível acessar o diretório de dados.", excecao);
    }
  }

  /**
   * Carrega automaticamente todos os registros existentes. @param plataforma
   * plataforma a preencher
   */
  public void carregar(Plataforma plataforma) {
    if (plataforma == null)
      throw new IllegalArgumentException("A plataforma não pode ser nula.");

    for (String[] campos : ler("estacoes.csv")) {
      plataforma.cadastrarEstacao(new Estacao(campos[0], Integer.parseInt(campos[1])));
    }

    for (String[] campos : ler("veiculos.csv")) {
      Veiculo veiculo;
      String tipo = campos[2];
      String status = campos[4];
      if ("BICICLETA_MECANICA".equals(tipo))
        veiculo = new BicicletaMecanica(campos[0], campos[1]);
      else if ("BICICLETA_ELETRICA".equals(tipo))
        veiculo = new BicicletaEletrica(campos[0], campos[1], Integer.parseInt(campos[3]));
      else if ("PATINETE_ELETRICO".equals(tipo))
        veiculo = new PatineteEletrico(campos[0], campos[1], Integer.parseInt(campos[3]));
      else
        throw new IllegalArgumentException("Tipo de veículo desconhecido: " + tipo);

      Estacao estacao = buscarEstacao(plataforma, campos[5]);
      plataforma.cadastrarVeiculo(veiculo, Veiculo.EM_USO.equals(status) ? null : estacao);
      veiculo.restaurarStatus(status);
      if (Veiculo.EM_MANUTENCAO.equals(status))
        plataforma.enviarParaManutencao(veiculo);
    }

    for (String[] campos : ler("usuarios.csv")) {
      plataforma.cadastrarUsuario(new Usuario(campos[0], campos[1], criarPlano(campos[2])));
    }

    List<RecargaPersistida> recargas = new ArrayList<>();
    for (String[] campos : ler("recargas.csv")) {
      Usuario usuario = buscarUsuario(plataforma, campos[0]);
      recargas.add(new RecargaPersistida(usuario,
          LocalDateTime.parse(campos[1]), Double.parseDouble(campos[2])));
    }

    List<ViagemPersistida> viagens = new ArrayList<>();
    for (String[] campos : ler("viagens.csv")) {
      Usuario usuario = buscarUsuario(plataforma, campos[0]);
      Veiculo veiculo = buscarVeiculo(plataforma, campos[1]);
      Estacao origem = buscarEstacao(plataforma, campos[2]);
      LocalDateTime inicio = LocalDateTime.parse(campos[4]);
      viagens.add(new ViagemPersistida(usuario, veiculo, origem, campos[3], inicio,
          campos[5].isBlank() ? null : LocalDateTime.parse(campos[5]),
          campos[6].isBlank() ? 0 : Integer.parseInt(campos[6]),
          campos[7].isBlank() ? 0.00 : Double.parseDouble(campos[7])));
    }

    List<EventoPersistido> eventos = new ArrayList<>();
    for (RecargaPersistida recarga : recargas)
      eventos.add(new EventoPersistido(recarga.dataHora, 0,
          recarga.usuario, null, recarga.valor));
    for (ViagemPersistida dados : viagens) {
      if (dados.termino != null)
        eventos.add(new EventoPersistido(dados.termino, 1,
            dados.usuario, null, dados.valor));
    }
    eventos.sort(Comparator.comparing(EventoPersistido::dataHora)
        .thenComparing(EventoPersistido::ordem));

    for (EventoPersistido evento : eventos) {
      if (evento.ordem == 0)
        evento.usuario.getCarteira().creditar(new Recarga(evento.dataHora, evento.valor));
      else
        evento.usuario.getCarteira().debitarViagem(evento.dataHora,
            "Viagem - " + buscarViagemPorUsuarioETermino(viagens, evento.usuario, evento.dataHora),
            evento.valor);
    }

    for (ViagemPersistida dados : viagens) {
      Viagem viagem;
      if (dados.termino == null) {
        viagem = new Viagem(dados.usuario, dados.veiculo, dados.origem, dados.inicio);
      } else {
        Estacao destino = buscarEstacao(plataforma, dados.destino);
        viagem = new Viagem(dados.usuario, dados.veiculo, dados.origem, destino,
            dados.inicio, dados.termino, dados.duracao, dados.valor);
      }
      plataforma.restaurarViagem(viagem);
    }
  }

  /**
   * Grava todos os dados alterados da plataforma. @param plataforma plataforma a
   * gravar
   */
  public void salvar(Plataforma plataforma) {
    if (plataforma == null)
      throw new IllegalArgumentException("A plataforma não pode ser nula.");
    List<String[]> estacoes = new ArrayList<>();
    for (Estacao estacao : plataforma.getEstacoes())
      estacoes.add(new String[] { estacao.getNome(), String.valueOf(estacao.getQuantidadeVagas()) });
    escrever("estacoes.csv", estacoes);

    List<String[]> veiculos = new ArrayList<>();
    for (Veiculo veiculo : plataforma.getVeiculos()) {
      String bateria = String.valueOf(veiculo.getCargaPercentual());
      String estacao = veiculo.getEstacaoAtual() == null
          ? ""
          : veiculo.getEstacaoAtual().getNome();
      veiculos.add(new String[] { veiculo.getCodigo(), veiculo.getModelo(),
          tipoVeiculo(veiculo), bateria, veiculo.getStatus(), estacao });
    }
    escrever("veiculos.csv", veiculos);

    List<String[]> usuarios = new ArrayList<>();
    List<String[]> recargas = new ArrayList<>();
    for (Usuario usuario : plataforma.getUsuarios()) {
      usuarios.add(new String[] { usuario.getNome(), usuario.getEmail(), tipoPlano(usuario.getPlano()) });
      for (Recarga recarga : usuario.getCarteira().getRecargas())
        recargas.add(
            new String[] { usuario.getEmail(), recarga.getDataHora().toString(),
                Dinheiro.formatar(recarga.getValor()) });
    }
    escrever("usuarios.csv", usuarios);
    escrever("recargas.csv", recargas);

    List<String[]> viagens = new ArrayList<>();
    for (Viagem viagem : plataforma.getViagens()) {
      viagens.add(new String[] { viagem.getUsuario().getEmail(), viagem.getVeiculo().getCodigo(),
          viagem.getEstacaoOrigem().getNome(),
          viagem.getEstacaoDestino() == null ? "" : viagem.getEstacaoDestino().getNome(),
          viagem.getInicio().toString(), viagem.getTermino() == null ? "" : viagem.getTermino().toString(),
          viagem.estaEmAndamento() ? "" : String.valueOf(viagem.getDuracaoMinutos()),
          viagem.estaEmAndamento() ? "" : Dinheiro.formatar(viagem.getValorCobrado()) });
    }
    escrever("viagens.csv", viagens);
  }

  private List<String[]> ler(String nome) {
    Path arquivo = diretorio.resolve(nome);
    List<String[]> linhas = new ArrayList<>();
    if (!Files.exists(arquivo))
      return linhas;
    try (BufferedReader leitor = Files.newBufferedReader(arquivo, StandardCharsets.UTF_8)) {
      String linha;
      while ((linha = leitor.readLine()) != null) {
        if (!linha.isBlank())
          linhas.add(separar(linha));
      }
    } catch (IOException excecao) {
      throw new IllegalArgumentException("Não foi possível ler " + nome + ".", excecao);
    }
    return linhas;
  }

  private void escrever(String nome, List<String[]> linhas) {
    Path arquivo = diretorio.resolve(nome);
    try (BufferedWriter escritor = Files.newBufferedWriter(arquivo, StandardCharsets.UTF_8)) {
      for (String[] campos : linhas) {
        List<String> formatados = new ArrayList<>();
        for (String campo : campos)
          formatados.add(citar(campo == null ? "" : campo));
        escritor.write(String.join(";", formatados));
        escritor.newLine();
      }
    } catch (IOException excecao) {
      throw new IllegalArgumentException("Não foi possível gravar " + nome + ".", excecao);
    }
  }

  private String citar(String valor) {
    if (valor.contains(";") || valor.contains("\"") || valor.contains("\n") || valor.contains("\r"))
      return "\"" + valor.replace("\"", "\"\"") + "\"";
    return valor;
  }

  private String[] separar(String linha) {
    List<String> campos = new ArrayList<>();
    StringBuilder atual = new StringBuilder();
    boolean aspas = false;
    for (int i = 0; i < linha.length(); i++) {
      char caractere = linha.charAt(i);
      if (caractere == '"') {
        if (aspas && i + 1 < linha.length() && linha.charAt(i + 1) == '"') {
          atual.append('"');
          i++;
        } else
          aspas = !aspas;
      } else if (caractere == ';' && !aspas) {
        campos.add(atual.toString());
        atual.setLength(0);
      } else
        atual.append(caractere);
    }
    campos.add(atual.toString());
    return campos.toArray(new String[0]);
  }

  private Plano criarPlano(String tipo) {
    if ("AVULSO".equals(tipo))
      return new PlanoAvulso();
    if ("MENSAL".equals(tipo))
      return new PlanoMensal();
    if ("UNIVERSITARIO".equals(tipo))
      return new PlanoUniversitario();
    throw new IllegalArgumentException("Tipo de plano desconhecido: " + tipo);
  }

  private String tipoPlano(Plano plano) {
    String classe = plano.getClass().getSimpleName();
    if ("PlanoAvulso".equals(classe))
      return "AVULSO";
    if ("PlanoMensal".equals(classe))
      return "MENSAL";
    if ("PlanoUniversitario".equals(classe))
      return "UNIVERSITARIO";
    throw new IllegalArgumentException("Tipo de plano desconhecido.");
  }

  private String tipoVeiculo(Veiculo veiculo) {
    String classe = veiculo.getClass().getSimpleName();
    if ("BicicletaMecanica".equals(classe))
      return "BICICLETA_MECANICA";
    if ("BicicletaEletrica".equals(classe))
      return "BICICLETA_ELETRICA";
    if ("PatineteEletrico".equals(classe))
      return "PATINETE_ELETRICO";
    throw new IllegalArgumentException("Tipo de veículo desconhecido.");
  }

  private Estacao buscarEstacao(Plataforma plataforma, String nome) {
    if (nome == null || nome.isBlank())
      return null;
    for (Estacao estacao : plataforma.getEstacoes())
      if (estacao.getNome().equals(nome))
        return estacao;
    throw new IllegalArgumentException("Estação não encontrada: " + nome);
  }

  private Usuario buscarUsuario(Plataforma plataforma, String email) {
    for (Usuario usuario : plataforma.getUsuarios())
      if (usuario.getEmail().equalsIgnoreCase(email))
        return usuario;
    throw new IllegalArgumentException("Usuário não encontrado: " + email);
  }

  private Veiculo buscarVeiculo(Plataforma plataforma, String codigo) {
    for (Veiculo veiculo : plataforma.getVeiculos())
      if (veiculo.getCodigo().equalsIgnoreCase(codigo))
        return veiculo;
    throw new IllegalArgumentException("Veículo não encontrado: " + codigo);
  }

  private String buscarViagemPorUsuarioETermino(List<ViagemPersistida> viagens,
      Usuario usuario, LocalDateTime termino) {
    for (ViagemPersistida viagem : viagens)
      if (viagem.usuario == usuario && termino.equals(viagem.termino))
        return viagem.veiculo.getCodigo();
    return "";
  }

  private record RecargaPersistida(Usuario usuario, LocalDateTime dataHora, double valor) {
  }

  private record ViagemPersistida(Usuario usuario, Veiculo veiculo, Estacao origem,
      String destino, LocalDateTime inicio, LocalDateTime termino,
      int duracao, double valor) {
  }

  private record EventoPersistido(LocalDateTime dataHora, int ordem, Usuario usuario,
      String descricao, double valor) {
  }
}
