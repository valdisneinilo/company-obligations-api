ALTER TABLE obrigacoes
MODIFY COLUMN descricao TEXT NOT NULL;

/*
ESSA ALTERAÇÃO FOI FEITA PARA SEGUIR AS INSTRUÇÕES DO TESTE, UMA VEZ QUE NELE DIZ:
Cada obrigação deverá possuir: 
	Título  
	Descrição  
	Empresa responsável  
	Usuário responsável  
	Status  
	Data de criação  

APESAR DE NÃO FALAR DE OBRIGATORIEDADE, ACREDITO QUE POSSIVELMENTE ESSES CAMPOS SÃO TODOS OBRIGATÓRIOS, NÃO PODENDO SER NULL
*/
