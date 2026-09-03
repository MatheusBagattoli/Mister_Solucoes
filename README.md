# SA - Login + Home + MySQL

Esta é uma versão mínima extraída do projeto original para deixar funcionando somente:

- Tela de Login
- Autenticação consultando o MySQL
- Sessão do usuário
- Tela Home
- Logout
- Spring Boot + Thymeleaf
- MySQL local
- Sem Docker

## 1. Criar o banco

Abra o MySQL Workbench e execute:

```sql
CREATE DATABASE IF NOT EXISTS agendamento_senai
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;
```

Depois, ao iniciar o Spring Boot pela primeira vez, o Hibernate criará a tabela `usuarios`.

Para criar um usuário de teste, depois que a tabela existir, execute:

```sql
USE agendamento_senai;

INSERT INTO usuarios (nome, data_nascimento, matricula, email, senha)
VALUES ('Administrador', '2000-01-01', '0001', 'admin@teste.com', 'Admin@123');
```

## 2. Configurar a senha do MySQL

Abra:

`src/main/resources/application.properties`

Altere:

```properties
spring.datasource.password=COLOQUE_AQUI_A_SENHA_DO_SEU_MYSQL
```

para a senha que você usa no MySQL.

Se o seu `root` não possui senha, deixe:

```properties
spring.datasource.password=
```

## 3. Abrir no IntelliJ

1. Extraia o ZIP.
2. Abra a pasta do projeto no IntelliJ IDEA.
3. Aguarde o Maven baixar as dependências.
4. Confirme que o projeto está usando Java 21.
5. Confirme que o MySQL Server está iniciado.
6. Rode a classe:

`SolucaoAgendamentoSaApplication.java`

## 4. Abrir no navegador

Acesse:

http://localhost:8080

Você será redirecionado para o Login.

Usuário de teste:

E-mail: `admin@teste.com`
Senha: `Admin@123`

Depois do login, será aberta a Home.

## Observação

Nesta versão, a senha do usuário é comparada diretamente com o valor salvo no banco, exatamente como o projeto original fazia. Para uma versão definitiva, o ideal é usar BCrypt/Spring Security para não armazenar senhas em texto puro.
