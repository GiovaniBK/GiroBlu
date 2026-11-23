# Plano de testes — GiroBlu Fase 1

| Caso | Descrição | Entrada | Saída esperada | Método JUnit |
|---|---|---|---|---|
| PL01 | Cálculo avulso | Bicicleta mecânica, 12 minutos | R$ 2,80 | PlanoAvulsoTest.calculaPrecoCompletoDaBicicletaMecanica |
| PL02 | Cálculo universitário elétrico | Bicicleta elétrica, 25 minutos | R$ 4,75 | PlanoUniversitarioTest.aplicaDescontoNaBicicletaEletrica |
| PL03 | Cálculo mensal | Patinete, 20 minutos | R$ 2,00 | PlanoMensalTest.cobraApenasMinutosExcedentesDoPatinete |
| PL04 | Patinete universitário | 10 minutos | R$ 7,00 | PlanoUniversitarioTest.patineteNaoRecebeDesconto |
| PL05 | Mensal com faixa gratuita | Mecânica, 10 minutos | R$ 0,00 | PlanoMensalTest.primeiraFaixaGratuita |
| PL06 | Limite de bateria elétrica | 19% e 20% | 19% rejeitado; 20% aceito | BicicletaEletricaTest.liberaCom20ENaoLiberaCom19 |
| PL07 | Devolução em estação lotada | Destino sem vagas | Estado e bateria não mudam | BicicletaEletricaTest.devolucaoAtualizaBateriaNaoAlteraEmLotada |
| PL08 | Estado em uso e manutenção | Veículo estacionado | Em uso não ocupa vaga; manutenção ocupa | VeiculoTest.emUsoNaoPermaneceNaEstacao |
| PL09 | Estação lotada | Uma vaga ocupada | EstacaoLotadaException | EstacaoTest.lotadaNaoEstacionaVeiculo |
| PL10 | Carteira negativa | Crédito R$ 10, débito R$ 12,50 | Saldo R$ -2,50 | CarteiraTest.carteiraCreditaEDebitaPermitindoSaldoNegativo |
| PL11 | Arredondamento universitário | Mecânica, 1 minuto | R$ 0,58 | PlanoUniversitarioTest.arredondaComHalfUp |
| PL12 | Piso da bateria do patinete | 20% por 20 minutos | 0% | PatineteEletricoTest.consumoDePatineteEValorMinimoDaBateria |
