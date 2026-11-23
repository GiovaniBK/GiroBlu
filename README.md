# GiroBlu

Sistema de retaguarda em Java/Swing para bicicletas e patinetes compartilhados.

## Requisitos

- Java 17 ou superior
- VS Code Java

## Estrutura

- `src/`: código-fonte das camadas de apresentação e negócio
- `test/`: testes JUnit
- `lib/`: jar do JUnit usado nos testes
- `dados-gb26/`: dados persistentes da aplicação

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
