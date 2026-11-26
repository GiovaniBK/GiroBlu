# Plano de estudos — GiroBlu

Use cada etapa separadamente em uma conversa nova com uma IA. Envie o conteúdo da etapa e peça explicações antes de continuar.

## Etapa 1 — Visão geral e estrutura

Antes de analisar o código, peça à IA para explicar:

- Como o projeto foi organizado em `src/apresentacao`, `src/negocio`, `src/negocio/excecao`, `src/persistencia`, `test/` e `data/`.
- Qual é a responsabilidade de cada camada.
- Como `Main` inicia `JanelaPrincipal`.
- Como `JanelaPrincipal` recebe e utiliza `Plataforma`.
- Como as classes se comunicam sem misturar Swing com a camada de negócio.
- Como executar o projeto e executar a suíte JUnit.

Código para enviar:

- `src/apresentacao/Main.java`
- `src/apresentacao/JanelaPrincipal.java`
- `src/negocio/Plataforma.java`
- `src/persistencia/PersistenciaArquivo.java`

Perguntas mínimas:

1. Explique o projeto como uma explicação para alguém que nunca viu Java.
2. Descreva o caminho completo de uma ação do botão até a alteração dos dados.
3. Liste todas as classes e diga por que cada uma existe.
4. Liste os métodos públicos e explique o que cada um recebe e retorna.
5. Identifique conceitos novos para mim e explique um por vez.

Não avance até conseguir explicar a arquitetura sem olhar o código.

## Etapa 2 — Base da camada de negócio

Estude uma classe por vez nesta ordem:

1. `StatusVeiculo` foi removido; `Veiculo` usa textos simples como `DISPONIVEL`, `EM_USO` e `EM_MANUTENCAO`.
2. `Veiculo`.
3. `VeiculoEletrico`.
4. `BicicletaMecanica`.
5. `BicicletaEletrica`.
6. `PatineteEletrico`.
7. `Estacao`.
8. `Recarga`.
9. `Carteira`.
10. `Lancamento`.
11. `Dinheiro`.

Para cada classe, peça à IA:

- Explicação da classe em uma frase.
- Significado dos atributos e por que são privados.
- Significado de cada construtor.
- Explicação de cada método público.
- Explicação de cada validação `if`.
- Explicação de como os objetos mudam de estado.
- Relação da classe com `Plataforma`.
- Um exemplo pequeno de uso da classe.

Conceitos desta etapa:

- Classe abstrata.
- Herança.
- Método abstrato.
- Sobrescrita com `@Override`.
- Encapsulamento.
- `protected`.
- `List` e `ArrayList`.
- `List.copyOf` para devolver uma cópia que não pode ser modificada.
- Comparação de textos com `equals` e `equalsIgnoreCase`.
- Atributos `final`.
- Métodos `static`.
- `double` e arredondamento.
- Duração em minutos completos.

Peça exemplos que não usem termos abstratos sem explicar o que o termo faz no código.

## Etapa 3 — Planos, viagens, faturamento e regras

Estude nesta ordem:

1. `Plano`.
2. `PlanoAvulso`.
3. `PlanoMensal`.
4. `PlanoUniversitario`.
5. `Usuario`.
6. `Viagem`.
7. `Faturamento`.
8. `Plataforma`.
9. Todas as classes de `negocio/excecao`.

Para `Plano` e seus implementadores, peça:

- Por que existe uma interface.
- Como cada plano calcula o valor.
- Por que quinze minutos são gratuitos no plano mensal.
- Por que bicicletas recebem desconto universitário e patinetes não.
- Como a tarifa completa é calculada.

Para `Usuario` e `Viagem`, peça:

- Como o usuário é identificado pelo e-mail.
- Como a viagem guarda origem, destino, usuário e veículo.
- Como uma viagem em andamento fica diferente de uma encerrada.
- Como o término posterior ao início é validado.
- Como duração e cobrança são calculadas.
- Quando o veículo muda de estado.
- Como o saldo pode ficar negativo.

Para `Plataforma`, peça:

- Por que ela centraliza cadastros e operações.
- Como duplicidade de estação, usuário e veículo é detectada.
- Como uma viagem é iniciada.
- Como uma viagem é encerrada.
- Como manutenção e recarga de bateria funcionam.
- Como extrato e faturamento são consultados.

Para `Faturamento`, peça:

- Como cada viagem encerrada entra no período.
- Como os totais são separados por tipo de veículo.
- Como `ItemFaturamento` substitui `Map`.
- Como os totais geral e por tipo são calculados.

Conceitos desta etapa:

- Interface implementada por várias classes.
- Polimorfismo.
- Referências entre objetos.
- `LocalDateTime`.
- `Duration`.
- `IllegalArgumentException`.
- Exceções próprias.
- `RuntimeException`.
- Controle de viagem em andamento.
- Cálculo de valor sem `BigDecimal`.

Exercício: peça à IA para simular cada exemplo dos testes `PL01` a `PL25` passo a passo sem escrever código.

## Etapa 4 — Persistência, Swing, testes e apresentação

Estude nesta ordem:

1. `PersistenciaArquivo`.
2. Arquivos em `data/`.
3. `JanelaPrincipal`.
4. `Main`.
5. Classes da pasta `test/`.
6. `test/planos-de-teste.md`.
7. README e plano de entrega.

Para persistência, peça:

- Como `File` representa o diretório e os arquivos CSV.
- Como `FileInputStream`, `InputStreamReader` e `BufferedReader` leem texto em UTF-8.
- Como `FileOutputStream`, `OutputStreamWriter` e `BufferedWriter` gravam texto em UTF-8.
- Como cada arquivo CSV é separado por ponto e vírgula.
- Como o leitor reconhece ponto e vírgula dentro de um campo entre aspas.
- Como o sistema lê cada linha.
- Como classes internas simples guardam os dados lidos, sem usar `record`.
- Como recargas e viagens são ordenadas por data e hora usando loops.
- Como os objetos são reconstruídos.
- Como nome, e-mail e código ligam referências entre classes.
- Como estado, bateria e viagens em andamento são preservados.
- Como recargas e viagens são colocadas no extrato ao carregar.
- Quando `Plataforma` chama `salvarDados()`.
- Como fechar e abrir sem perder dados.

Para Swing, peça:

- Como `JFrame` cria a janela.
- Como `JTabbedPane` cria as abas.
- Como `JTextField` recebe dados.
- Como `JButton` executa uma ação.
- Como `JTextArea` mostra consultas.
- Como `JOptionPane` mostra erros e confirmações.
- Como a tela lê dados e chama `Plataforma`.
- Por que não existem regras de cobrança dentro da tela.
- O que o método `executar` faz.

Para testes, peça:

- Como JUnit organiza uma classe de teste.
- O significado de `@Test` e `@BeforeEach`.
- Como `assertEquals` compara valores.
- Como `assertThrows` verifica exceções.
- Como interpretar uma falha apresentada pelo JUnit.
- Como os cinco exemplos da viagem são testados pelo fluxo completo.
- Como o teste de persistência verifica encerrada, em andamento e recargas.

Preparação final:

1. Peça à IA um diagrama de classes da camada de negócio.
2. Peça um roteiro curto de demonstração da aplicação.
3. Peça cinco perguntas que o professor pode fazer sobre encapsulamento e herança.
4. Peça cinco perguntas sobre persistência e viagens.
5. Peça cinco perguntas sobre Swing e separação de camadas.
6. Peça um resumo oral de dois minutos sobre o projeto.
7. Faça o mesmo resumo sem olhar o código.

## Modelo pronto para enviar à IA

Copie e substitua `[ARQUIVO]` e `[ASSUNTO]`:

```text
Estou aprendendo Java usando o projeto GiroBlu.

Leia o arquivo ou trecho abaixo e explique o assunto passo a passo:
- Arquivo: [ARQUIVO]
- Assunto: [ASSUNTO]

Primeiro explique o objetivo da classe.
Depois explique cada atributo, construtor e método, um por vez.
Explique também a relação com outras classes.
Não pule palavras técnicas: defina `private`, `public`, `static`, `final`, `protected`, `override`, `interface`, `extends` e `implements` quando aparecerem.
Use exemplos pequenos que possam ser executados mentalmente.
Indique quais regras vêm do enunciado e quais são decisões técnicas minhas.
Não use apenas teoria: associe cada explicação a uma linha real do código.
Ao final:
1. Liste conceitos novos.
2. Liste dúvidas que eu deveria testar.
3. Faça três perguntas para eu responder.
4. Diga como eu explicaria o assunto em português em 30 segundos.
```

## Ordem final recomendada

- Etapa 1: arquitetura.
- Etapa 2: classes e regras básicas.
- Etapa 3: regras completas e fluxo de viagem.
- Etapa 4: persistência, Swing e testes.
- Depois: diagrama UML, plano PDF, slides e ensaio de apresentação.
