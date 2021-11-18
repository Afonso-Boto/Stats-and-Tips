-- Store Procedure

-- receber equipa consoante id do clube
GO
CREATE PROCEDURE SAT.Get_ClubebyID @Clube_id VARCHAR(10) = NULL 
AS  
    IF @Clube_id IS NULL
    BEGIN
        PRINT 'Club ID required!'
        RETURN
    END

    SELECT treinador, ngolos, nome, data_fundacao, id_clube 
    FROM (SAT.Equipa JOIN SAT.Clube ON Clubeid_clube=id_clube)
    WHERE id_clube=@Clube_id
    ORDER BY data_fundacao

GO

-- Dado um id de um jogador retornar jogador
CREATE PROCEDURE SAT.Get_Jogador @Jogador_id VARCHAR(10) = NULL
AS
    IF @Jogador_id IS NULL
    BEGIN
        PRINT 'Jogador ID is required!'
        RETURN
    END
    SELECT SAT.Jogador.nome , njogos, nacionalidade, posicao, amarelos, vermelhos, SAT.Clube.id_clube
    FROM ((SAT.Clube JOIN SAT.Equipa ON id_clube=Clubeid_clube) 
        JOIN SAT.Jogador ON Equipaid=id_equipa)
    WHERE id_jogador=@Jogador_id;
GO

-- dado um nome de um clube retornar , treinador, ngolos, nome e id_clube
GO
CREATE PROCEDURE SAT.Get_Clube_byName @nome_sc VARCHAR(50) = NULL 
AS  
    IF @nome_sc IS NULL
    BEGIN
        PRINT 'Name of club required!'
        RETURN
    END

    SELECT  treinador, ngolos, SAT.Clube.nome, data_fundacao, id_clube
    FROM (SAT.Equipa JOIN SAT.Clube ON Clubeid_clube=id_clube)
    WHERE nome=@nome_sc
    ORDER BY data_fundacao
GO
-- Dado um nome de um cliente listar todos os clubes a que este deu favorito
CREATE PROC SAT.Get_Favourites @name_client VARCHAR(100)
AS
        IF @name_client IS NULL
        BEGIN
            PRINT 'Name of Client required!'
            RETURN
        END

        SELECT  treinador, ngolos, SAT.Clube.nome, data_fundacao, id_clube
        FROM ((SAT.Cliente JOIN SAT.Equipa_Cliente ON id_cliente=client_id )
            JOIN SAT.Equipa ON equipa_id=id_equipa) JOIN SAT.Clube ON Clubeid_clube=id_clube
        WHERE SAT.Cliente.nome=@name_client
GO
-- Receber um id_clube e devolver os nomes dos jogadores e as suas posicoes

CREATE PROC SAT.Get_Jogador_Of_Club @clube_id VARCHAR(10)
AS
    IF @clube_id IS NULL
    BEGIN
        PRINT 'Club ID required!'
        RETURN
    END

    SELECT SAT.Jogador.nome , posicao, id_jogador
    FROM ((SAT.Clube JOIN SAT.Equipa ON id_clube=Clubeid_clube) JOIN SAT.Jogador ON id_equipa=Equipaid)
    WHERE id_clube=@clube_id
GO

-- Retornar lista de todas as odds
CREATE PROC SAT.List_ODDS
AS
        SELECT Equipaid_equipa, Equipaid_equipa2, localizacao, data_hora, vitoria, empate, derrota, vitoria_empate, total_golos, ambas_marcam 
        FROM ((SAT.Odd JOIN SAT.Jogo ON Jogoid_jogo=id_jogo) JOIN SAT.Equipa ON Equipaid_equipa=id_equipa);
GO

-- retornar nome do clube pelo ID do Clube
CREATE PROC SAT.Get_TeamName_byID @equipa_id VARCHAR(10)
AS
        SELECT nome
        FROM (SAT.Equipa JOIN SAT.Clube ON Clubeid_clube=id_clube)
        WHERE id_equipa=@equipa_id
GO

-- retornar nome e email dado um email de um cliente
CREATE PROC SAT.Get_Cliente_byEmail @email_sc VARCHAR(255)
AS
    SELECT id_cliente, nome, email
    FROM SAT.Cliente
    WHERE email=@email_sc;
GO

-- listar todos os nome e ids dos clubes na fantasy league
CREATE PROC SAT.List_Clubs
AS
    SELECT id_clube, nome
    FROM SAT.Clube;
GO

CREATE PROC SAT.List_ClubeIDs
AS
    SELECT id_clube, id_equipa
    FROM (SAT.Equipa JOIN SAT.Clube ON id_clube=Clubeid_clube)
GO

CREATE PROC SAT.Add_Clube @clube_id VARCHAR(10) = NULL , @name_clube VARCHAR(50) = NULL, @fundacao INT = NULL,@equipa_id VARCHAR(10) = NULL, @manager VARCHAR(255) = NULL
AS
    IF @clube_id IS NULL OR @name_clube IS NULL OR @fundacao IS NULL OR @equipa_id is NULL OR @manager is NULL
    BEGIN
        PRINT 'UPS SOMETHING WENT WRONG! NULL VALUE FOUND!'
        RETURN
    END
    INSERT INTO SAT.Clube(id_clube, nome, data_fundacao) VALUES(@clube_id, @name_clube, @fundacao)
    INSERT INTO SAT.Equipa(id_equipa, treinador, ngolos, epoca, Clubeid_clube) VALUES (@equipa_id, @manager, 0, '2020/2021', @clube_id)

GO

CREATE PROC SAT.Add_Jogador @player_id VARCHAR(10) = NULL, @player_name VARCHAR(255) = NULL, @player_nac VARCHAR(100) = NULL, @player_pos VARCHAR(3) = NULL, @club_name VARCHAR(255) = NULL
AS
    IF @player_name IS NULL OR @player_name IS NULL OR @player_nac IS NULL OR @player_pos is NULL OR @club_name is NULL
    BEGIN
        PRINT 'UPS SOMETHING WENT WRONG! NULL VALUE FOUND!'
        RETURN
    END
    DECLARE @equipa_id VARCHAR(10);
    SELECT @equipa_id = id_equipa 
    FROM (SAT.Equipa JOIN SAT.Clube ON id_clube=Clubeid_clube)
    WHERE SAT.Clube.nome=@club_name;

    INSERT INTO SAT.Jogador(Equipaid, id_jogador, nome, amarelos, vermelhos, njogos, nacionalidade, posicao)
    VALUES(@equipa_id, @player_id, @player_name, 0, 0, 0, @player_nac, @player_pos)

GO
CREATE PROC SAT.Add_JogadorBy_ClubID @player_id VARCHAR(10) = NULL, @player_name VARCHAR(255) = NULL, @player_nac VARCHAR(100) = NULL, @player_pos VARCHAR(3) = NULL, @club_id VARCHAR(10) = NULL
AS
    IF @player_name IS NULL OR @player_name IS NULL OR @player_nac IS NULL OR @player_pos is NULL OR @club_id is NULL
    BEGIN
        PRINT 'UPS SOMETHING WENT WRONG! NULL VALUE FOUND!'
        RETURN
    END
    DECLARE @equipa_id VARCHAR(10);
    SELECT @equipa_id = id_equipa 
    FROM (SAT.Equipa JOIN SAT.Clube ON id_clube=Clubeid_clube)
    WHERE SAT.Clube.id_clube=@club_id;

    INSERT INTO SAT.Jogador(Equipaid, id_jogador, nome, amarelos, vermelhos, njogos, nacionalidade, posicao)
    VALUES(@equipa_id, @player_id, @player_name, 0, 0, 0, @player_nac, @player_pos)

GO

EXEC SAT.Add_Clube 'C420', 'Club Of Luf', 1808;
SELECT nome, data_fundacao, treinador FROM (SAT.Clube JOIN SAT.Equipa ON id_clube=Clubeid_clube)

SELECT * FROM SAT.Jogador ORDER BY nome
DELETE FROM SAT.Clube WHERE id_clube='C72'
SELECT * FROM SAT.C

EXEC SAT.Add_Jogador 'J01', 'Joel Fernandes', 'Nigeriano', 'AV', 'Football Club Porto'

