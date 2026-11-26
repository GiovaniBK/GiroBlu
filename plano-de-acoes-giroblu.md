# GiroBlu — Plano de ações do projeto

Repositório: https://github.com/GiovaniBK/GiroBlu
Entrega no AVA: **29/11/2026 às 23:59** · Apresentações: Turma 001 em 30/11 e 03/12; Turma 004 em 01/12 e 03/12.

Legenda: `[x]` feito · `[ ]` a fazer

---

## 1. Base do projeto (FEITO)

- [x] Estrutura no padrão do professor: `src/` (código), `test/` (JUnit), `lib/` (jar do JUnit), sem Maven
- [x] Pacotes `apresentacao`, `negocio` e `negocio.excecao`
- [x] Pasta `data/` na raiz do projeto
- [x] `Main` como ponto de entrada da aplicação
- [x] `JanelaPrincipal` aberta com o título exato `GiroBlu`
- [x] `README.md` e `.gitignore`

## 2. Camada de negócio — base (FEITO)

- [x] `Veiculo` (classe abstrata) + `StatusVeiculo` (disponível, em uso, em manutenção)
- [x] `BicicletaMecanica`, `VeiculoEletrico` (bateria), `BicicletaEletrica`, `PatineteEletrico`
- [x] Regras de bateria: mínimo de 20% para liberar, piso de 0%, recarga só fora de uso, consumo por minuto
- [x] Liberar, devolver (com consumo de bateria), enviar para manutenção e voltar da manutenção
- [x] `Estacao` com controle de vagas (manutenção ocupa vaga, veículo em uso não) e consulta de disponíveis
- [x] `Carteira` e `Recarga` (valor positivo, débito pode deixar saldo negativo)
- [x] `Plano` (interface) + `PlanoAvulso`, `PlanoMensal`, `PlanoUniversitario`, com `BigDecimal` e arredondamento em 2 casas
- [x] Exceções: `GiroBluException`, `EstacaoLotadaException`, `SaldoInsuficienteException`, `VeiculoIndisponivelException`
- [x] `toString()` polimórfico nos veículos (tipo e carga)

## 3. Testes — base (FEITO)

- [x] Uma classe de teste por classe de negócio, em `test/`
- [x] `test/planos-de-teste.md` com os casos da base

---

## 4. Camada de negócio — o que falta (FEITO)

- [x] **`Usuario`**: nome, e-mail, plano e carteira (começa zerada)
- [x] **`Viagem`**: usuário, veículo, estação de origem e de destino, data/hora de início e de término, duração em minutos e valor cobrado
- [x] **Iniciar viagem**: usuário sem outra viagem em andamento, saldo mínimo de R$ 5,00, veículo disponível e (se elétrico) com 20% ou mais de carga
- [x] **Encerrar viagem**: término posterior ao início, destino com vaga, cálculo da duração e do valor pelo plano, débito na carteira (mesmo que fique negativo), veículo na estação de destino com a bateria descontada
- [x] **Extrato**: recargas e viagens em ordem cronológica (viagem entra na data/hora de término), com valor como crédito ou débito e saldo depois de cada lançamento
- [x] **Faturamento por período**: quantidade de viagens encerradas e valor arrecadado por tipo de veículo + total geral
- [x] **Classe que reúne as operações** (`Plataforma`): listas de estações, veículos, usuários e viagens; cadastros, recargas, viagens, manutenção, consultas, extrato e faturamento
- [x] Javadoc em todos os métodos públicos novos
- [x] Testes JUnit das classes novas, incluindo os 5 exemplos pelo fluxo completo de viagem
- [x] Atualizar `test/planos-de-teste.md` com os novos casos (IDs `PLxx`)

## 5. Persistência em arquivo (FEITO)

- [x] Formato escolhido: CSV (texto), em uma classe própria da camada de persistência
- [x] Gravação em `data/` de estações, veículos (com status e bateria), usuários (com plano e recargas) e viagens, **inclusive as viagens em andamento**
- [x] Carga automática ao abrir o programa, com reconstrução das referências por nome, e-mail e código
- [x] Gravação a cada operação que altera dados da plataforma
- [x] A camada de negócio não importa Swing
- [x] Arquivos de dados com registros de exemplo para abertura inicial do sistema
- [x] Testes da persistência

## 6. Interface gráfica — Swing (FEITO)

- [x] Telas/abas simples (rótulos, campos de texto, botões e área de texto): cadastro de estação, veículo e usuário; recarga; iniciar e encerrar viagem; manutenção e recarga de bateria; consulta de veículos da estação; extrato; faturamento
- [x] Resultados das consultas em área de texto, sem tabelas
- [x] Violações de regra (`GiroBluException` e `IllegalArgumentException`) aparecem em `JOptionPane`, sem travar ou perder dados
- [x] Nenhuma regra de negócio dentro das telas: a tela lê campos, chama a camada de negócio e mostra o resultado
- [x] Cabeçalho de Javadoc indicando a camada, como nos exemplos do professor

## 7. Entrega (A FAZER)

- [ ] **Diagrama de classes UML** da camada de negócio (draw.io), igual ao código, exportado em **PDF**
- [ ] **Plano de testes** em **PDF**
- [ ] **Slides** no template do AVA (todos os integrantes apresentam pelo menos um slide)
- [ ] Revisar a lista dos itens de POO: encapsulamento, herança/classe/método abstrato, polimorfismo (sem `instanceof`), interface própria implementada por mais de uma classe, exceção própria, persistência, coleções
- [ ] Montar o `.zip` com `src`, `test`, `data` (com exemplos), `README.md` e `.vscode/settings.json`, sem `bin/`, `target/` ou arquivos de apoio (PDF do enunciado, planos de IA)
- [ ] Enviar no AVA até 29/11 às 23:59

---

## Sugestão de ordem

1. **Quinta e sexta:** seção 4 (negócio) com testes. As telas e a persistência dependem dela.
2. **Sábado:** persistência (seção 5) e telas (seção 6), já integradas.
3. **Domingo (antes das 23:59):** UML, plano de testes em PDF, slides, revisão geral e envio.

## Regras para manter o padrão do professor

- Código simples, um item por vez, com testes ao final de cada um.
- Validação de dado inválido com `IllegalArgumentException`; regra de negócio com as exceções próprias.
- Mensagens e comentários em português.
- Todos os integrantes precisam saber explicar qualquer parte do código (`enum`, `BigDecimal`, `LocalDateTime`, `protected` e métodos package-private entram nisso).

## Dúvidas para levar ao professor

1. A duração em minutos trunca ou arredonda para cima os segundos excedentes?
2. Devolver na mesma estação de onde saiu conta como vaga livre? (Em tese sim, pois o veículo em uso não ocupa vaga.)
3. No extrato, em empate de data/hora entre recarga e fim de viagem, qual vem primeiro?
4. A persistência deve ficar na camada de negócio ou em uma camada própria? (O enunciado fala em duas camadas.)

## Ponto de atenção de modelagem

`Veiculo.aceitaDescontoUniversitario()` resolve o "exceto patinete" sem `instanceof`, mas faz o veículo conhecer uma regra de um plano específico. Funciona, mas preparem a justificativa para o slide "Decisões de modelagem".
