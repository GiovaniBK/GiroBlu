# GiroBlu

Sistema de retaguarda em Java/Swing para bicicletas e patinetes compartilhados.

## Requisitos

- Java 17 ou superior
- VS Code Java

## Estrutura

- `src/`: código-fonte das camadas de apresentação e negócio
- `test/`: testes JUnit
- `lib/`: jar do JUnit usado nos testes
- `data/`: dados persistentes da aplicação

## Persistência

Os dados ficam em `data/` em arquivos CSV separados para estações, veículos, usuários, recargas e viagens. Ao iniciar, o sistema carrega esses arquivos e toda operação relevante salva o estado atualizado.

## Regra de duração

A duração das viagens é calculada em minutos completos. Quando uma viagem possui segundos excedentes, esses segundos são descartados; por exemplo, 12 minutos e 30 segundos contam como 12 minutos.

## Interface gráfica

A janela principal usa abas para cadastros, recargas, viagens, manutenção e recarga de bateria, consultas, extrato e faturamento. Todas as operações passam pela mesma `Plataforma` carregada a partir de `data/`.

## Executar

Abra o projeto no VS Code e execute a classe `apresentacao.Main`.

Também é possível compilar pelo terminal:

```powershell
javac -d bin -cp "lib/junit-platform-console-standalone-1.11.4.jar" (Get-ChildItem -Recurse src,test -Filter *.java | ForEach-Object { $_.FullName })
java -cp "bin;lib/junit-platform-console-standalone-1.11.4.jar" apresentacao.Main
```

## Testar

```powershell
java -jar lib/junit-platform-console-standalone-1.11.4.jar execute --scan-class-path
```
