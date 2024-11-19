CREATE DATABASE IF NOT EXISTS DefesaCivil;
USE DefesaCivil;

CREATE TABLE Users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL,
    UNIQUE(username)
);

CREATE TABLE IF NOT EXISTS Endereco(
    idEndereco INT AUTO_INCREMENT,
    logradouro VARCHAR(100) NOT NULL,
    numero INT NOT NULL,
    CEP CHAR(8) NOT NULL, 
    cidade VARCHAR(50) NOT NULL,
    Complemento VARCHAR(100),
    PRIMARY KEY (idEndereco)
);

CREATE TABLE IF NOT EXISTS TipoRisco(
    idRisco INT AUTO_INCREMENT,
    categoria VARCHAR(100) NOT NULL,
    PRIMARY KEY (idRisco)
);

CREATE TABLE IF NOT EXISTS TipoEmergencia(
    idEmergencia INT AUTO_INCREMENT,
    categoria VARCHAR(100) NOT NULL,
    PRIMARY KEY (idEmergencia)
);

CREATE TABLE IF NOT EXISTS CadastroFamilia(
    idFamilia INT AUTO_INCREMENT,
    descricao VARCHAR(100) NOT NULL,
    TipoRisco_id INT, 
    TipoEmergencia_id INT, 
    PRIMARY KEY (idFamilia),
    FOREIGN KEY (TipoRisco_id) REFERENCES TipoRisco (idRisco),
    FOREIGN KEY (TipoEmergencia_id) REFERENCES TipoEmergencia (idEmergencia)
);

CREATE TABLE IF NOT EXISTS Membro(
    CPF CHAR(11), 
    nome VARCHAR(100) NOT NULL,
    numTelefone VARCHAR(15) NOT NULL, 
    dataNasc date,
    email VARCHAR(100) NOT NULL,
    telEmergencia VARCHAR(15) NOT NULL,
    Endereco_id INT,  
    CadastroFamilia_id INT, 
	foto LONGBLOB,
    PRIMARY KEY (CPF),
    FOREIGN KEY (Endereco_id) REFERENCES Endereco (idEndereco),
    FOREIGN KEY (CadastroFamilia_id) REFERENCES CadastroFamilia (idFamilia)
);


INSERT INTO TipoRisco (categoria) VALUES
('Em risco'),
('Alto Risco'),
('Extremo Risco');

INSERT INTO TipoEmergencia (categoria) VALUES
('Inundação'),
('Vendaval'),
('Desmoronamentos'),
('Incendio'),
('Tsuname'),
('Tempestade');

INSERT INTO Users (username, password, role) VALUES ('admin', '12345', 'ADMIN');

-- Desabilitar modo de segurança da query (permite usar update e delete nos dados do banco)
SET SQL_SAFE_UPDATES = 0;

-- comando para zerar ID
Alter table endereco auto_increment = 1;

-- Insere um dado diretamente no bando, em um lugar especifico 
update endereco set Complemento = 'Apartamento' where idEndereco in (4);

SELECT*
FROM endereco;
SELECT*
FROM users;
SELECT*
FROM membro;
