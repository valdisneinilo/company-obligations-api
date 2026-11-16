# Company Obligations API

API REST para gerenciamento de obrigações contábeis, permitindo o cadastro e gerenciamento de empresas, usuários, obrigações e seus respectivos anexos.

O projeto foi desenvolvido como parte de um teste técnico, utilizando Java e Spring Boot, com foco em organização, separação de responsabilidades, validação de dados e persistência relacional.

## 1. Tecnologias utilizadas

- **Java 21** — linguagem de programação.
- **Spring Boot 4** — framework para desenvolvimento da API REST.
- **Maven** — gerenciamento de dependências e build.
- **MySQL 8.4** — banco de dados relacional.
- **JdbcTemplate** — acesso e execução de operações SQL.
- **Flyway** — versionamento e gerenciamento de migrations.
- **Springdoc OpenAPI / Swagger UI** — documentação interativa da API.
- **JUnit** — testes automatizados.
- **Docker** — conteinerização da aplicação.
- **Docker Compose** — orquestração dos containers.

## 2. Arquitetura e decisões técnicas

O projeto utiliza os princípios da **Clean Architecture**, buscando separar as regras de negócio dos detalhes de infraestrutura e exposição da API.

A aplicação está organizada em quatro camadas principais:

- **Domain:** contém as entidades, enums e regras de negócio.
- **Application:** contém os casos de uso, DTOs e contratos necessários para executar as operações da aplicação.
- **Infrastructure:** contém as implementações de infraestrutura, como repositórios JDBC e configurações.
- **Web:** responsável pela exposição dos endpoints REST, controllers, DTOs de entrada e saída, documentação e tratamento de exceções.

### Decisões técnicas

- Utilização de `JdbcTemplate` para acesso ao banco de dados, sem utilização de JPA ou Spring Data JPA.
- Utilização de UUID como identificador das entidades.
- Utilização de migrations Flyway para versionamento da estrutura do banco.
- Separação entre DTOs da camada de aplicação e DTOs da camada Web.
- Validação de dados nas entradas da API e nas entidades de domínio.
- Tratamento centralizado de exceções, com respostas padronizadas.
- Paginação nas listagens.
- Documentação dos endpoints utilizando OpenAPI e Swagger UI.
- Utilização de Docker Compose para executar a API e o banco de dados em containers.

## 3. Estrutura de diretórios

```text
br/com/obligations/obligations/
├── domain/
├── application/
├── infrastructure/
└── web/
```

Arquivos principais na raiz:

```text

├── .env
├── .env.example
├── .gitignore
├── .dockerignore
├── Dockerfile
├── docker-compose.yml
├── mvnw
├── pom.xml
└── README.md
```

## 4. Pré-requisitos

Para executar o projeto, é necessário ter:

- Java 21, para execução local.
- Docker Engine.
- Docker Compose.
- Git.

Para execução local, também é necessário ter uma instância do MySQL 8.4 disponível.

## 5. Configuração das variáveis de ambiente

As configurações da aplicação são realizadas por variáveis de ambiente.

Crie um arquivo `.env` na raiz do projeto, utilizando o exemplo abaixo:

```env
API_VERSION=v1

# Banco de dados
DB_HOST=localhost
DB_PORT=3306
DB_NAME=company_obligations
DB_USERNAME=obligations
DB_PASSWORD=sua_senha

# Credenciais do MySQL
MYSQL_ROOT_PASSWORD=sua_senha_root
MYSQL_DATABASE=company_obligations
MYSQL_USER=obligations
MYSQL_PASSWORD=sua_senha
```

**Importante:** o arquivo `.env` não deve ser versionado. Ele está incluído no `.gitignore`.

O Spring Boot utiliza essas variáveis para configurar a conexão com o banco de dados.

Ao executar pelo Docker Compose, o endereço `DB_HOST` é sobrescrito para `mysql`, que corresponde ao nome do serviço do banco na rede interna do Docker.

## 6. Como executar com Docker

Com o Docker Engine e o Docker Compose instalados, execute os comandos na raiz do projeto.

### Construir e iniciar os containers

```bash
docker compose up -d --build
```

Esse comando constrói a imagem da API e inicia os serviços da aplicação e do MySQL.

### Verificar os containers

```bash
docker compose ps
```

### Consultar os logs da API

```bash
docker compose logs -f api
```

### Parar os containers

```bash
docker compose down
```

Os dados do MySQL são armazenados em um volume Docker chamado `mysql_data`, permitindo preservar os registros após a remoção e recriação dos containers.

Para remover também o volume e os dados persistidos, seria necessário executar:

```bash
docker compose down -v
```

**Atenção:** esse último comando remove permanentemente o volume do banco de dados.

## 7. Como executar localmente

Para executar a aplicação diretamente na máquina, é necessário ter Java 21 e MySQL 8.4 instalados ou disponíveis em containers.

### Iniciar somente o MySQL com Docker

```bash
docker compose up -d mysql
```

### Carregar as variáveis de ambiente

No Linux, execute:

```bash
set -a
source .env
set +a
```

### Executar a aplicação

```bash
./mvnw spring-boot:run
```

A aplicação ficará disponível em:

```text
http://localhost:8080
```

O Flyway executará automaticamente as migrations pendentes durante a inicialização da aplicação.

## 8. Como executar os testes

Para executar todos os testes automatizados, utilize:

```bash
./mvnw test
```

Para executar a compilação completa:

```bash
./mvnw clean package
```

Os testes podem ser executados sem iniciar a aplicação principal.

## 9. Documentação Swagger

A API possui documentação interativa gerada com OpenAPI e Springdoc.

Após iniciar a aplicação, acesse:

**Swagger UI:**

[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

**OpenAPI JSON:**

[http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)

A documentação apresenta os endpoints, parâmetros, modelos de requisição, respostas e códigos HTTP utilizados pela API.

## 10. Módulos e funcionalidades

A API contempla os seguintes módulos:

### Empresas

- Cadastro de empresas.
- Consulta por identificador.
- Listagem paginada.
- Atualização dos dados cadastrais.
- Alteração da situação da empresa.

### Usuários

- Cadastro de usuários.
- Consulta por identificador.
- Listagem paginada.
- Atualização dos dados cadastrais.
- Alteração da situação do usuário.

### Obrigações

- Cadastro de obrigações vinculadas a empresas e responsáveis.
- Consulta por identificador.
- Listagem paginada com informações resumidas.
- Atualização de obrigações.
- Controle de situação das obrigações.

### Anexos

- Cadastro de metadados dos anexos vinculados às obrigações.
- Listagem de anexos por obrigação.

**Observação:** nesta versão, o módulo de anexos armazena os metadados dos arquivos no banco de dados. O armazenamento físico dos arquivos não faz parte do escopo implementado.

## 11. Melhorias futuras

Algumas evoluções que podem ser consideradas em versões futuras:

- Implementação de autenticação e autorização.
- Implementação de upload e armazenamento físico dos anexos.
- Implementação de cache.
- Inclusão de filtros e parâmetros de pesquisa nas listagens.
- Ampliação da cobertura de testes automatizados.
- Implementação de pipeline CI/CD.
- Configuração específica para ambientes de produção.

---

Desenvolvido como parte de um teste técnico.
