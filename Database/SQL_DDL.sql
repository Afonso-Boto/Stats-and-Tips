CREATE SCHEMA SAT
GO


-- Tabelas

CREATE TABLE SAT.Equipa (
  id_equipa varchar(10) NOT NULL, 
  treinador varchar(255) NULL, 
  ngolos    int NULL, 
  epoca     varchar(9) NULL,
  Clubeid_clube varchar(10) NULL,
  PRIMARY KEY (id_equipa));

CREATE TABLE SAT.Jogador (
  Equipaid      varchar(10) NOT NULL, 
  id_jogador    varchar(10) NOT NULL, 
  nome          varchar(255) NULL, 
  amarelos      int NULL, 
  vermelhos     int NULL, 
  njogos        int NULL, 
  nacionalidade varchar(100) NULL, 
  posicao       VARCHAR(3) NULL,
  Jogoid_jogo   varchar(10) NOT NULL, 
  PRIMARY KEY (id_jogador));

CREATE TABLE SAT.Competicao (
  id_competicao VARCHAR(10) NOT NULL, 
  nome          varchar(10) NULL, 
  nequipas      int NULL, 
  PRIMARY KEY (id_competicao));

CREATE TABLE SAT.Odd (
  id_odd               varchar(10) NOT NULL, 
  vitoria              int NULL, 
  empate               int NULL, 
  derrota              int NULL, 
  vitoria_empate       int NULL, 
  total_golos          int NULL, 
  ambas_marcam         int NULL,
  Jogoid_jogo          varchar(10) NOT NULL, 
  PRIMARY KEY (id_odd));

CREATE TABLE SAT.Registo (
  nome        varchar(10) NOT NULL, 
  nfaltas     int NULL, 
  namarelos   int NULL, 
  ncantos     int NULL, 
  ngolos      int NULL, 
  Jogoid_jogo varchar(10) NOT NULL,
  Arbitroid_arbitro varchar(10) NOT NULL,
  PRIMARY KEY (nome));

CREATE TABLE SAT.Clube (
  id_clube      varchar(10) NOT NULL, 
  nome          varchar(50) NULL, 
  data_fundacao int NULL,
  PRIMARY KEY (id_clube));

CREATE TABLE SAT.Equipa_Competicao (
  Equipaid_equipa varchar(10) NOT NULL, 
  Competicaoid    VARCHAR(10) NOT NULL, 
  PRIMARY KEY (Equipaid_equipa, 
  Competicaoid));


CREATE TABLE SAT.Arbitro (
  id_arbitro varchar(10) NOT NULL, 
  nome       varchar(50) NULL,
  PRIMARY KEY(id_arbitro));

CREATE TABLE SAT.Liga (
  classificacoes          varchar(255) NULL, 
  Competicaoid_competicao varchar(10) NOT NULL,
  PRIMARY KEY(Competicaoid_competicao));

CREATE TABLE SAT.Taca (
  rondas                  varchar(255) NULL, 
  Competicaoid_competicao varchar(10) NOT NULL,
  PRIMARY KEY(Competicaoid_competicao));


CREATE TABLE SAT.Jogador_Jogo(
  Jogadorid_jogador varchar(10) ,
  Jogoid_jogo       varchar(10) ,
  PRIMARY KEY(Jogadorid_jogador, Jogoid_jogo));

CREATE TABLE SAT.Jogo (
  localizacao            varchar(10) NULL, 
  data_hora             varchar(10) NULL, 
  id_jogo          varchar(10) NOT NULL, 
  resultado        varchar(10) NULL, 
  Competicaoid     varchar(10) NOT NULL, 
  Equipaid_equipa  varchar(10) NOT NULL, 
  Equipaid_equipa2 varchar(10) NOT NULL, 
  PRIMARY KEY (id_jogo));

CREATE TABLE SAT.Cliente (
  id_cliente    VARCHAR(10),
  nome          VARCHAR(100),
  email         VARCHAR(255),
  pass          VARCHAR(16),
  PRIMARY KEY (id_cliente)
);

CREATE TABLE SAT.Equipa_Cliente (
  client_id     VARCHAR(10),
  equipa_id     VARCHAR(10),
  PRIMARY KEY (client_id, equipa_id)
);

-- Integridade

ALTER TABLE SAT.Jogador ADD CONSTRAINT FKJogadoreEquipaid FOREIGN KEY (Equipaid) REFERENCES SAT.Equipa (id_equipa);

ALTER TABLE SAT.Equipa ADD CONSTRAINT FKEquipa401218 FOREIGN KEY (Clubeid_clube) REFERENCES SAT.Clube (id_clube);

ALTER TABLE SAT.Equipa_Competicao ADD CONSTRAINT FKEquipa_Com999010 FOREIGN KEY (Equipaid_equipa) REFERENCES SAT.Equipa (id_equipa);
ALTER TABLE SAT.Equipa_Competicao ADD CONSTRAINT FKEquipa_Com810297 FOREIGN KEY (Competicaoid) REFERENCES SAT.Competicao (id_competicao);

ALTER TABLE SAT.Jogador_Jogo ADD CONSTRAINT FKJogador_Jogo1 FOREIGN KEY (Jogadorid_jogador) REFERENCES SAT.Jogador (id_jogador);
ALTER TABLE SAT.Jogador_Jogo ADD CONSTRAINT FKJogador_Jogo2 FOREIGN KEY (Jogoid_jogo) REFERENCES SAT.Jogo (id_jogo);


ALTER TABLE SAT.Registo ADD CONSTRAINT FKRegisto532354 FOREIGN KEY (Arbitroid_arbitro) REFERENCES SAT.Arbitro (id_arbitro);
ALTER TABLE SAT.Registo ADD CONSTRAINT FKRegisto787259 FOREIGN KEY (Jogoid_jogo) REFERENCES SAT.Jogo (id_jogo);

ALTER TABLE SAT.Jogo ADD CONSTRAINT FKJogo557265 FOREIGN KEY (Competicaoid) REFERENCES SAT.Competicao (id_competicao);

ALTER TABLE SAT.Odd ADD CONSTRAINT FKOdd68218 FOREIGN KEY (Jogoid_jogo) REFERENCES SAT.Jogo (id_jogo);

ALTER TABLE SAT.Liga ADD CONSTRAINT FKLiga446850 FOREIGN KEY (Competicaoid_competicao) REFERENCES SAT.Competicao (id_competicao);

ALTER TABLE SAT.Taca ADD CONSTRAINT FKTaca681458 FOREIGN KEY (Competicaoid_competicao) REFERENCES SAT.Competicao (id_competicao);

ALTER TABLE SAT.Jogador ADD CONSTRAINT FKJogador732794 FOREIGN KEY (Equipaid) REFERENCES SAT.Equipa (id_equipa);

ALTER TABLE SAT.Jogo ADD CONSTRAINT FKJogo252043 FOREIGN KEY (Equipaid_equipa) REFERENCES SAT.Equipa (id_equipa);
ALTER TABLE SAT.Jogo ADD CONSTRAINT FKJogo861332 FOREIGN KEY (Equipaid_equipa2) REFERENCES SAT.Equipa (id_equipa);
 
ALTER TABLE SAT.Equipa_Cliente ADD CONSTRAINT FKEquipaCliente001 FOREIGN KEY(client_id) REFERENCES SAT.Cliente (id_cliente)
ALTER TABLE SAT.Equipa_Cliente ADD CONSTRAINT FKEquipaCliente002 FOREIGN KEY(equipa_id) REFERENCES SAT.Equipa (id_equipa)


-- INDEXAÇÂO
CREATE INDEX ixJogadorNome ON SAT.Jogador(nome, Equipaid)
CREATE INDEX ixEquipaClube ON SAT.Equipa(id_equipa, Clubeid_clube)
CREATE INDEX ixJogoEquipas ON SAT.Jogo(id_jogo, Equipaid_equipa, Equipaid_equipa2)

-- VIEWS
GO
CREATE VIEW ClienteView AS 
SELECT nome, email FROM SAT.Cliente
WHERE nome != 'Admin'
GO

CREATE VIEW ClubView AS 
SELECT nome, data_fundacao, treinador, ngolos FROM (SAT.Clube JOIN SAT.Equipa ON Clubeid_clube=id_clube)
GO

-- Store Procedure

CREATE PROCEDURE SAT.Get_Clubes
AS  
    SELECT id_clube ,nome FROM SAT.Clube
GO

CREATE PROCEDURE SAT.Get_Equipas @Clube_id VARCHAR(10) = NULL 
AS  
    IF @Clube_id IS NULL
    BEGIN
        PRINT 'Club ID required!'
        RETURN
    END

    SELECT treinador, ngolos, nome, data_fundacao 
    FROM (SAT.Equipa JOIN SAT.Clube ON Clubeid_clube=id_clube)
    WHERE id_clube=@Clube_id
    ORDER BY data_fundacao

GO
CREATE PROCEDURE SAT.Get_Clubes
AS  
    SELECT id_clube ,nome FROM SAT.Clube
GO

CREATE PROCEDURE SAT.Get_Jogador @Jogador_id VARCHAR(10) = NULL
AS
    IF @Jogador_id IS NULL
    BEGIN
        PRINT 'Jogador ID is required!'
        RETURN
    END
    SELECT SAT.Jogador.nome , amarelos, vermelhos, njogos AS 'numero de jogos', nacionalidade, posicao AS 'Posição', SAT.Clube.nome AS 'Clube'
    FROM ((SAT.Clube JOIN SAT.Equipa ON id_clube=Clubeid_clube) 
        JOIN SAT.Jogador ON Equipaid=id_equipa)
    WHERE id_jogador=@Jogador_id;
GO

<<<<<<< HEAD
DROP 
=======
-- INDEXAÇÂO
CREATE INDEX ixJogadorNome ON SAT.Jogador(nome, Equipaid)
CREATE INDEX ixEquipaClube ON SAT.Equipa(id_equipa, Clubeid_clube)
CREATE INDEX ixJogoEquipas ON SAT.Jogo(id_jogo, Equipaid_equipa, Equipaid_equipa2)
>>>>>>> 8d5f0f2fa9ef737ef7ac8c3782ac15ef8a972ce3
