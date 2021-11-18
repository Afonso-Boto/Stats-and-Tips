-- INDEXAÇÂO
CREATE INDEX ixJogadorEquipa ON SAT.Jogador(Equipaid, id_jogador)
CREATE INDEX ixEquipaClube ON SAT.Equipa(id_equipa, Clubeid_clube)
CREATE INDEX ixJogoEquipas ON SAT.Jogo(id_jogo, Equipaid_equipa, Equipaid_equipa2)
