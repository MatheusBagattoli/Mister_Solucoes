# Plano de testes — Mister Soluções (Aula 10 / Entrega 8)

> **Importante:** os casos abaixo estão planejados com base no documento de requisitos e no código recebido. O campo "Resultado obtido" permanece como **Não executado** porque os testes de interface precisam ser executados no ambiente local real. Não marque como aprovado sem executar e registrar evidência.

## Identificação da entrega

- **Sistema:** Mister Soluções — Sistema de Gerenciamento de Ferramentas e Estoque
- **Versão/commit:** preencher com o commit realmente testado
- **Data da execução:** preencher após executar
- **Responsáveis:** preencher com os integrantes
- **Turma:** preencher

## 8.1 Ferramentas e ambiente de teste

Preencha as versões reais da máquina em que os testes serão executados:

| Item | Ambiente |
|---|---|
| Sistema operacional | Windows; preencher a versão exata |
| Navegador | Chrome; preencher a versão exata |
| Aplicação e endereço | Spring Boot local: `http://localhost:8080`; confirmar ao executar |
| Linguagem e framework | Java (projeto configurado para Java 21), Spring Boot 4.1.0, Maven |
| SGBD e banco | MySQL; banco configurado como `almoxarifado`; preencher versão |
| Execução | Testes automatizados JUnit 5/Mockito e testes manuais pela interface |
| Ferramentas complementares | IntelliJ IDEA, Maven e MySQL Workbench/cliente utilizado; preencher versões reais |
| Dados | Usuários e ferramentas fictícios; nunca registrar senhas reais |

## 8.2 Relação entre requisitos e testes

Os identificadores foram preservados conforme o documento de requisitos fornecido. O documento lista RF01–RF05 e deixa RF06 sem descrição; também contém RF-001 a RF-005 para login e produtos. Não atribua comportamento a RF06 até esclarecer o requisito original.

| Requisito | Casos relacionados | Cenários previstos |
|---|---|---|
| RF01 — Somente ADM pode editar/excluir itens | CT-01, CT-02, CT-03 | ADM autorizado; funcionário bloqueado; usuário sem sessão bloqueado |
| RF02 — Listar funcionários cadastrados | CT-04 | Listagem com usuários cadastrados |
| RF03 — Listar ferramentas cadastradas | CT-05 | Listagem de ferramentas |
| RF04 — Histórico de cada movimentação | CT-10, CT-11 | Entrada/saída geram histórico; filtros do histórico |
| RF05 — Registrar responsável e data da operação | CT-10, CT-11 | Nome do responsável e data/hora registrados |
| RF-001 — Realizar login | CT-06, CT-07, CT-08 | Credenciais válidas; senha incorreta; campos vazios |
| RF-002 — Cadastro de produto | CT-09, CT-12, CT-13 | Cadastro válido; campos obrigatórios; preço inválido/duplicidade |
| RF-003 — Editar produto | CT-14, CT-15 | Alteração válida; funcionário tenta editar |
| RF-004 — Excluir produto | CT-16, CT-17 | Exclusão autorizada; exclusão com histórico/sem permissão |
| RN-001 — CPF deve ser único | CT-18 | CPF duplicado recusado |
| RN-002 — Estoque nunca pode ser negativo | CT-11, CT-19, CT-20 | Saída válida; saída maior que saldo; quantidade inválida |
| Regra de estoque mínimo descrita no escopo | CT-21, CT-22, CT-23 | Quantidade abaixo, igual e acima do mínimo |

**Limitações encontradas ao comparar documentação e código:** o código atual verifica CPF duplicado no serviço, mas a validação amigável de e-mail duplicado não aparece no serviço; o cadastro de produto não valida explicitamente nome duplicado nem preço negativo; e o cálculo do estoque baixo usa `quantidade <= estoqueMinimo`. A execução dos casos deve confirmar o comportamento real e revelar o que precisa ser corrigido.

## 8.3 Fichas de caso de teste

Campos comuns a todas as fichas:
- **Resultado obtido:** Não executado.
- **Status:** Não executado.
- **Evidência:** anexar captura de tela ou registro de execução após testar.
- **Responsável/data:** preencher após a execução.
- **Problema e reteste:** preencher se houver falha.

### CT-01 — ADM edita um produto
- **Requisito:** RF01 / RF-003.
- **Tipo:** Sucesso.
- **Pré-condição:** existir um produto cadastrado; usuário ADM válido.
- **Dados:** alterar a descrição de um produto de teste.
- **Passos:** 1. Entrar como ADM. 2. Abrir Produtos. 3. Selecionar edição do produto. 4. Alterar a descrição e salvar. 5. Conferir a listagem/banco.
- **Esperado:** alteração salva e apresentada ao consultar o produto.

### CT-02 — Funcionário tenta editar produto
- **Requisito:** RF01 / RF-003.
- **Tipo:** Entrada inválida/permissão.
- **Pré-condição:** existir produto; sessão de funcionário ativa.
- **Dados:** ID de um produto existente.
- **Passos:** 1. Entrar como funcionário. 2. Tentar abrir `/produto/editar/{id}`. 3. Tentar enviar uma requisição de edição.
- **Esperado:** acesso/alteração negados; produto permanece inalterado.

### CT-03 — Usuário sem sessão tenta acessar edição
- **Requisito:** RF01.
- **Tipo:** Entrada inválida/permissão.
- **Pré-condição:** sessão encerrada.
- **Passos:** 1. Abrir diretamente `/produto/editar/{id}`.
- **Esperado:** redirecionamento para login; nenhuma alteração ocorre.

### CT-04 — Listar funcionários
- **Requisito:** RF02.
- **Tipo:** Sucesso.
- **Pré-condição:** existir mais de um funcionário cadastrado.
- **Passos:** 1. Entrar no sistema com usuário autorizado. 2. Abrir a área de usuários/funcionários.
- **Esperado:** usuários cadastrados são listados conforme as permissões definidas.
- **Observação:** confirmar primeiro qual rota/página implementa esta listagem; não foi identificada uma página de listagem de usuários no ZIP analisado.

### CT-05 — Listar ferramentas
- **Requisito:** RF03.
- **Tipo:** Sucesso.
- **Pré-condição:** existir ao menos três ferramentas fictícias cadastradas.
- **Passos:** 1. Entrar no sistema. 2. Abrir cadastro/listagem de produtos. 3. Conferir nomes e dados.
- **Esperado:** ferramentas cadastradas são exibidas.

### CT-06 — Login com credenciais válidas
- **Requisito:** RF-001.
- **Tipo:** Sucesso.
- **Pré-condição:** usuário fictício cadastrado; sessão encerrada.
- **Dados:** e-mail e senha de teste válidos.
- **Passos:** 1. Abrir `/login`. 2. Informar e-mail. 3. Informar senha. 4. Clicar Entrar.
- **Esperado:** sessão criada e redirecionamento para `/home`.

### CT-07 — Login com senha incorreta
- **Requisito:** RF-001.
- **Tipo:** Entrada inválida.
- **Pré-condição:** usuário fictício existente; sessão encerrada.
- **Dados:** e-mail válido e senha incorreta.
- **Passos:** repetir login usando senha incorreta.
- **Esperado:** permanecer no login e exibir mensagem de credenciais inválidas, sem autenticar.

### CT-08 — Login com campos vazios
- **Requisito:** RF-001.
- **Tipo:** Entrada inválida.
- **Pré-condição:** sessão encerrada.
- **Dados:** e-mail vazio, senha vazia; repetir também com apenas um campo vazio.
- **Passos:** 1. Abrir login. 2. Deixar os campos vazios. 3. Enviar.
- **Esperado:** login não é realizado e campos obrigatórios são indicados. Confirmar validação no servidor, não apenas no navegador.

### CT-09 — Cadastrar ferramenta válida
- **Requisito:** RF-002.
- **Tipo:** Sucesso.
- **Pré-condição:** ADM autenticado.
- **Dados:** nome `Alicate universal teste`, descrição `Ferramenta fictícia`, preço `25.90`, estoque mínimo `5`.
- **Passos:** 1. Abrir `/produto/cadastro`. 2. Preencher dados. 3. Salvar. 4. Consultar listagem e estoque.
- **Esperado:** produto persistido e linha de estoque criada com quantidade inicial zero.

### CT-10 — Registrar entrada e conferir histórico
- **Requisito:** RF04 e RF05.
- **Tipo:** Sucesso.
- **Pré-condição:** ADM autenticado; ferramenta com saldo 0.
- **Dados:** entrada de 10 unidades.
- **Passos:** 1. Abrir estoque. 2. Registrar entrada de 10. 3. Abrir histórico. 4. Conferir produto, tipo, quantidade, saldo anterior/atual, responsável e data/hora.
- **Esperado:** saldo 10; histórico registra ENTRADA, quantidade 10, saldo anterior 0, atual 10, responsável e data/hora.

### CT-11 — Registrar saída válida e conferir histórico
- **Requisito:** RF04, RF05 e RN-002.
- **Tipo:** Sucesso.
- **Pré-condição:** ADM autenticado; ferramenta com saldo 10.
- **Dados:** saída de 3 unidades.
- **Passos:** 1. Registrar saída de 3. 2. Abrir histórico. 3. Conferir dados.
- **Esperado:** saldo 7 e movimentação SAÍDA com saldo anterior 10, atual 7, responsável e data/hora.

### CT-12 — Cadastro de produto com nome vazio
- **Requisito:** RF-002.
- **Tipo:** Entrada inválida.
- **Pré-condição:** ADM autenticado.
- **Dados:** nome vazio; demais campos preenchidos.
- **Passos:** enviar o formulário sem nome.
- **Esperado:** sistema rejeita o cadastro e informa o campo obrigatório; nenhum produto é persistido.

### CT-13 — Cadastro com preço negativo ou nome duplicado
- **Requisito:** RF-002.
- **Tipo:** Entrada inválida.
- **Pré-condição:** ADM autenticado; produto existente para testar duplicidade.
- **Dados:** testar preço `-1.00` e, separadamente, repetir nome já cadastrado.
- **Passos:** enviar cada cenário separadamente.
- **Esperado:** valor negativo e duplicidade devem ser rejeitados conforme o documento de requisitos. Registrar qualquer comportamento diferente como falha.

### CT-14 — ADM altera um campo do produto
- **Requisito:** RF-003.
- **Tipo:** Sucesso.
- **Pré-condição:** ADM autenticado; produto existente.
- **Dados:** alterar somente a descrição.
- **Passos:** abrir edição, mudar descrição, salvar e consultar.
- **Esperado:** descrição atualizada e demais dados preservados.

### CT-15 — Funcionário tenta alterar produto
- **Requisito:** RF01 e RF-003.
- **Tipo:** Entrada inválida/permissão.
- **Pré-condição:** funcionário autenticado; produto existente.
- **Passos:** tentar abrir e enviar edição.
- **Esperado:** alteração bloqueada e dados preservados.

### CT-16 — Excluir produto sem movimentações
- **Requisito:** RF-004 e RF01.
- **Tipo:** Sucesso.
- **Pré-condição:** ADM autenticado; produto de teste sem histórico.
- **Passos:** excluir o produto pela interface e confirmar.
- **Esperado:** produto removido da listagem e registro de estoque associado removido.

### CT-17 — Tentar excluir produto com histórico
- **Requisito:** RF-004.
- **Tipo:** Entrada inválida/regra de negócio.
- **Pré-condição:** ADM autenticado; produto com movimentação registrada.
- **Passos:** tentar excluir o produto.
- **Esperado no código atual:** exclusão bloqueada com indicação de que existe histórico.

### CT-18 — Cadastrar CPF duplicado
- **Requisito:** RN-001.
- **Tipo:** Entrada inválida.
- **Pré-condição:** existir usuário com CPF fictício `12345678901`.
- **Dados:** tentar cadastrar outro usuário com o mesmo CPF.
- **Passos:** preencher cadastro e enviar.
- **Esperado:** cadastro recusado e usuário duplicado não persistido.

### CT-19 — Tentar saída maior que o saldo
- **Requisito:** RN-002.
- **Tipo:** Entrada inválida.
- **Pré-condição:** ferramenta com saldo 5; ADM autenticado.
- **Dados:** saída de 6 unidades.
- **Passos:** registrar saída de 6.
- **Esperado:** operação recusada; saldo continua 5 e não é criada movimentação de saída.

### CT-20 — Quantidade de movimentação igual a zero ou negativa
- **Requisito:** RN-002.
- **Tipo:** Entrada inválida.
- **Pré-condição:** ferramenta existente; ADM autenticado.
- **Dados:** testar quantidade 0 e -1 separadamente.
- **Passos:** enviar cada movimentação.
- **Esperado:** sistema rejeita as quantidades e não altera o saldo nem cria histórico.

### CT-21 — Estoque abaixo do mínimo
- **Requisito:** regra de estoque mínimo descrita no escopo.
- **Tipo:** Valor de limite.
- **Pré-condição:** produto com estoque mínimo 5.
- **Dados:** saldo final 4.
- **Passos:** realizar movimentação até deixar saldo 4 e abrir a página de estoque.
- **Esperado:** produto contado/identificado como estoque baixo.

### CT-22 — Estoque exatamente no mínimo
- **Requisito:** regra de estoque mínimo descrita no escopo.
- **Tipo:** Valor de limite.
- **Pré-condição:** produto com estoque mínimo 5.
- **Dados:** saldo final 5.
- **Passos:** deixar saldo exatamente 5 e abrir a página de estoque.
- **Esperado conforme a implementação atual:** produto é contado como estoque baixo, pois a condição é menor ou igual ao mínimo. Confirmar se essa regra coincide com a definição aprovada.

### CT-23 — Estoque acima do mínimo
- **Requisito:** regra de estoque mínimo descrita no escopo.
- **Tipo:** Valor de limite.
- **Pré-condição:** produto com estoque mínimo 5.
- **Dados:** saldo final 6.
- **Passos:** deixar saldo 6 e abrir a página de estoque.
- **Esperado:** produto não é contado como estoque baixo.

## Testes automatizados implementados

Os testes automatizados estão em `src/test/java/com/senai/almoxarifado_Mister/services/`:
- `EstoqueServiceTest`: entrada/saída, quantidade inválida, saldo insuficiente, estoque inexistente, registro de movimentação e contagens de estoque baixo/zerado.
- `UsuarioServiceTest`: login válido/inválido, cadastro válido e CPF duplicado.

Executar na raiz do projeto:
```bash
mvn test
```

## 8.4 Resumo da execução

Preencher depois de executar todos os casos manuais e automatizados.

| Total planejado | Passou | Falhou | Bloqueado | Não executado |
|---:|---:|---:|---:|---:|
| 23 casos manuais + testes automatizados | preencher | preencher | preencher | preencher |

### Pendências para resolver/confirmar

1. O documento de requisitos deixa RF06 sem descrição.
2. Confirmar a regra exata de estoque mínimo: o código atual considera alerta/estoque baixo quando saldo é igual ou inferior ao mínimo.
3. Confirmar se existe página de listagem de funcionários; ela não foi identificada no ZIP.
4. Implementar/validar a rejeição amigável de e-mail duplicado, nome de produto duplicado e preço negativo.
5. Executar cada caso manual, anexar evidências, preencher resultado obtido/status e completar versões reais do ambiente.
6. Exportar o documento final para PDF após remover campos de instrução e preencher os resultados reais.

## Reflexões

**1. Por que a pré-condição precisa estar explícita?**  
Porque define o estado inicial necessário para repetir o teste de forma confiável e comparar os resultados.

**2. Qual é a diferença entre resultado esperado e resultado obtido?**  
O resultado esperado é definido antes da execução com base no requisito. O resultado obtido descreve o que aconteceu de verdade durante o teste. A comparação entre os dois determina se passou ou falhou.
