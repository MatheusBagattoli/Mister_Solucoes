CREATE DATABASE IF NOT EXISTS agendamento_senai
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

USE agendamento_senai;

-- A tabela usuarios será criada/atualizada pelo Hibernate ao iniciar o projeto.
-- Se quiser entrar imediatamente, execute também:
INSERT INTO usuarios (nome, data_nascimento, matricula, email, senha)
VALUES ('Administrador', '2000-01-01', '0001', 'admin@teste.com', 'Admin@123');
