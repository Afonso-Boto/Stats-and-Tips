import random

# SAT.Clube
def generate_clube(id_clube, nome, data_fundacao):
	return f"INSERT INTO SAT.Clube(id_clube, nome, data_fundacao) VALUES(\'{id_clube}\', \'{nome}\', {data_fundacao})"

# SAT.Equipa
def generate_equipa(id_equipa, treinador, ngolos, epoca, clube_id):
	return f"INSERT INTO SAT.Equipa(id_equipa, treinador, ngolos, epoca, Clubeid_clube) VALUES(\'{id_equipa}\', \'{treinador}\', {ngolos}, \'{epoca}\', \'{clube_id}\')"

# SAT.Competicao
def generate_competicao(id_competicao, nome, nequipas):
	return f"INSERT INTO SAT.Competicao(id_competicao, nome, nequipas) VALUES(\'{id_competicao}\', \'{nome}\', {nequipas})"

# SAT.Liga
def generate_liga(classificacoes):
	return f"INSERT INTO SAT.Liga(classificacoes) VALUES(\'{classificacoes}\')"

# SAT.Taca
def generate_taca(rondas):
	return f"INSERT INTO SAT.Taca(rondas) VALUES(\'{rondas}\')"

# SAT.Jogador
def generate_jogador(equipa_id ,id_jogador, nome, amarelos, vermelhos, njogos, nacionalidade, posicao):
	return f"INSERT INTO SAT.Jogador( Equipaid , id_jogador, nome, amarelos, vermelhos, njogos, nacionalidade, posicao) VALUES(\'{equipa_id}\', \'{id_jogador}\', \'{nome}\', {amarelos}, {vermelhos}, {njogos}, \'{nacionalidade}\', \'{posicao}\')"

# SAT.Jogo
def generate_jogo(localizacao, data_hora, id_jogo, resultado, competicao_id, id_equipa1, id_equipa2):
	return f"INSERT INTO SAT.Jogo(localizacao, data_hora, id_jogo, resultado, Competicaoid, Equipaid_equipa, Equipaid_equipa2) VALUES(\'{localizacao}\', \'{data_hora}\', \'{id_jogo}\', \'{resultado}\', \'{competicao_id}\', \'{id_equipa1}\', \'{id_equipa2}\')"

# SAT.Registo
def generate_registo(nome, nfaltas, namarelos, ncantos, ngolos, id_jogo, id_arbitro):
	return f"INSERT INTO SAT.Registo(nome, nfaltas, namarelos, ncantos, ngolos) VALUES(\'{nome}\', {nfaltas}, {namarelos}, {ncantos}, {ngolos}, \'{id_jogo}\', \'{id_arbitro}\')"

# SAT.Arbitro
def generate_arbitro(id_arbitro, nome):
	return f"INSERT INTO SAT.Arbitro(id_arbitro, nome) VALUES(\'{id_arbitro}\', \'{nome}\')"

# SAT.Odd
def generate_odd(id_odd, vitoria, empate, derrota, vitoria_empate, total_golos, ambas_marcam, id_jogo):
	return f"INSERT INTO SAT.Odd(id_odd, vitoria, empate, derrota, vitoria_empate, total_golos, ambas_marcam, Jogoid_jogo) VALUES(\'{id_odd}\', {vitoria}, {empate}, {derrota}, {vitoria_empate}, {total_golos}, {ambas_marcam}, \'{id_jogo}\')"

def generate_jogojogador(Jogadorid_jogador, Jogoid_jogo):
	return f"INSERT INTO SAT.Jogador_Jogo(Jogadorid_jogador, Jogoid_jogo) VALUES(\'{Jogadorid_jogador}\', \'{Jogoid_jogo}\')"

# SAT.Cliente
def generate_cliente(id_cliente, nome, email, password):
	return f"INSERT INTO SAT.Cliente(id_cliente, nome, email, pass) VALUES(\'{id_cliente}\', \'{nome}\', \'{email}\', \'{password}\')"

#---------------------------------------------------------------------------------------------------------------------------------------

def generate_random_name(prefix_names, suffix_names):
	return prefix_names[random.randint(0, len(prefix_names)-1)] + " " + suffix_names[random.randint(0, len(suffix_names)-1)]

def generate_ID(pref):
	return pref + str(random.randint(0, 1000))

def writeInserIntoFile(fname, lst):
	with open(fname, "w") as f:
		for insert in lst:
			f.write(insert + "\n")


def main():

	# Lists of inserts
	competicao = list()
	clubes = list()
	equipas = list()
	jogadores = list()
	arbitros = list()
	jogos = list()
	registos = list()
	odds = list()
	jogo_jogador = list()
	jogador_equipa = list()


	# Sets
	id_clube = set()
	id_equipa = set()
	id_jogador = set()
	id_arbitros = set()
	id_jogos = set()
	id_registos = set()
	id_odds = set()
	nomes = set()
	
	
	prefix_clube = ["Football Club", "Sport Club"]
	sufix_clube = ["Braga", "Porto", "Aveiro", "Coimbra", "Leiria", "Lisboa", "Setubal", "Evora", "Beja", "Faro"]
	
	prefix_name = ["Antonio", "Eduardo", "Tomas", "Afonso", "Goncalo", "Francisco", "Andre", "Carlos", "Xavier", "Joaquim", "Joao", "Caetano"]
	sufix_name = ["Oliveira", "Silva", "Souza", "Santos", "Vieira", "Rodrigues", "Goncalves", "Ferreira", "Pereira", "Gomes", "Alves", "Almeida", "Carvalho", "Pinhao", "Silveira", "Vilarinho"]

	
	# Competicao
	print("Generating SAT.Competicao...")
	competicao.append(generate_competicao("LIGA PT", "Liga Portugal", 10))


	# Arbitro
	print("Generating SAT.Arbitro...")
	for i in range(3):
		while True:
			id = generate_ID("AR")
			nome = generate_random_name(prefix_name, sufix_name)
			if nome not in nomes and id not in id_arbitros:
				nomes.add(nome)
				id_arbitros.add(id)
				arbitros.append(generate_arbitro(id, nome))
				break


	for i in range(10):
		# Clube
		print("Generating SAT.Clube")
		while True:
			idclube = generate_ID("C")
			if idclube not in id_clube:
				id_clube.add(idclube)
				break

		
		name = generate_random_name(prefix_clube, sufix_clube)
		data_fundacao = random.randint(1890, 1950)
		
		clubes.append(generate_clube(idclube, name, data_fundacao))
		
		# Equipa
		print("Generating SAT.Equipa...")
		while True:
			idequipa = generate_ID("E")
			treinador = generate_random_name(prefix_name, sufix_name)
			if idequipa not in id_equipa and treinador not in nomes:
				id_equipa.add(idequipa)
				nomes.add(treinador)
				break
		
		
		equipas.append(generate_equipa(idequipa, treinador, 0, "2020/2021", idclube))
		
		# Jogador
		print("Generating SAT.Jogador...")
		for i in range(11):
			while True:
				idjogador = generate_ID("J")
				nome = generate_random_name(prefix_name, sufix_name)
				if idjogador not in id_jogador and nome not in nomes:
					id_jogador.add(idjogador)
					nomes.add(nome)
					break

			amarelos = random.randint(0,5)
			vermelhos = random.randint(0,2)
			njogos = 18
			nacionalidade = ["Portugues", "Brasileiro", "Columbiano", "Argentino", "Espanhol", "Frances"][random.randint(0, 5)]
			
			if 0 <= i < 1:
				jogadores.append(generate_jogador(idequipa, idjogador, nome, amarelos, vermelhos, njogos, nacionalidade, "GR"))
			elif 1 <= i < 5:
				jogadores.append(generate_jogador(idequipa, idjogador, nome, amarelos, vermelhos, njogos, nacionalidade, "DEF"))
			elif 5 <= i < 9:
				jogadores.append(generate_jogador(idequipa, idjogador, nome, amarelos, vermelhos, njogos, nacionalidade, "MED"))
			else:
				jogadores.append(generate_jogador(idequipa, idjogador, nome, amarelos, vermelhos, njogos, nacionalidade, "AV"))
			
			jogador_equipa.append((idequipa, idjogador))
	
	for i in range(len(id_equipa)):
		for j in range(i+1, len(id_equipa)):
			while True:
				idjogo = generate_ID("G")
				if idjogo not in id_jogos:
					id_jogos.add(idjogo)
					break
			
			while True:
				idregisto = generate_ID("R")
				if idregisto not in id_registos:
					id_registos.add(idregisto)
					break
			
			while True:
				idodd = generate_ID("O")
				if idodd not in id_odds:
					id_odds.add(idodd)
					break
			
			# JOGO
			localizacao = ["Porto", "Braga", "Aveiro", "Lisboa", "Algarve", "Coimbra"][random.randint(0, 5)]
			resultado = ["0-0", "1-0", "0-1", "1-1", "2-1", "1-2", "2-2"][random.randint(0, 5)]
			jogos.append(generate_jogo(localizacao, "15:00", idjogo, resultado, "LIGA PT", list(id_equipa)[i], list(id_equipa)[j]))
			
			# JOGO JOGADOR
			for eq, jog in jogador_equipa:
				if eq == list(id_equipa)[i] or eq == list(id_equipa)[j]:
					jogo_jogador.append(generate_jogojogador(jog, idjogo))

			# REGISTO e ODD
			idarbitro = list(id_arbitros)[random.randint(0,2)]
			
			vitoria = random.randint(0, 90)
			empate = random.randint(0, 90 - vitoria)
			derrota = 100 - vitoria - empate
			vitoria_empate = vitoria + 5
			
			ngolos = 0 
			for r in resultado.split("-"):
				ngolos += int(r)
			
			ambas_marcam = random.randint(0,1)

			registos.append(generate_registo(idregisto, random.randint(0, 10), random.randint(0,4), random.randint(5, 15), ngolos, idjogo, idarbitro))
			odds.append(generate_odd(idodd, vitoria, empate, derrota, vitoria_empate, ngolos, ambas_marcam, idjogo))
	
	cliente = [generate_cliente("CL123" , "ambrosio", "ambrosio@ua.pt", "123654")]	

	# Save inserts into files
	#writeInserIntoFile("Insert_Competicao.sql", competicao)
	#writeInserIntoFile("Insert_Clube.sql", clubes)
	#writeInserIntoFile("Insert_Equipas.sql", equipas)
	#writeInserIntoFile("Insert_Jogador.sql", jogadores)
	#writeInserIntoFile("Insert_Jogos.sql", jogos)
	#writeInserIntoFile("Insert_Arbitro.sql", arbitros)
	#writeInserIntoFile("Insert_Registos.sql", registos)
	#writeInserIntoFile("Insert_Odds.sql", odds)
	#writeInserIntoFile("Insert_JogoJogador.sql", jogo_jogador)
	writeInserIntoFile("Insert_Client.sql", cliente)

# Call main function
main() 