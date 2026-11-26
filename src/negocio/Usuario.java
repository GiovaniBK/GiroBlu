package negocio;

/** CAMADA DE NEGÓCIO Representa um usuário da plataforma GiroBlu. */
public class Usuario {
  private final String nome;
  private final String email;
  private final Plano plano;
  private final Carteira carteira;

  /**
   * Cria um usuário com carteira zerada.
   * 
   * @param nome  nome do usuário
   * @param email e-mail único
   * @param plano plano contratado
   */
  public Usuario(String nome, String email, Plano plano) {
    if (nome == null || nome.isBlank())
      throw new IllegalArgumentException("O nome do usuário não pode ser vazio.");
    if (email == null || email.isBlank())
      throw new IllegalArgumentException("O e-mail do usuário não pode ser vazio.");
    if (plano == null)
      throw new IllegalArgumentException("O plano do usuário não pode ser nulo.");
    this.nome = nome;
    this.email = email;
    this.plano = plano;
    this.carteira = new Carteira();
  }

  /** @return nome */
  public String getNome() {
    return nome;
  }

  /** @return e-mail */
  public String getEmail() {
    return email;
  }

  /** @return plano */
  public Plano getPlano() {
    return plano;
  }

  /** @return carteira */
  public Carteira getCarteira() {
    return carteira;
  }
}
