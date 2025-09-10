📦 Estoque — Aplicação Java (JDBC) com Service/DAO e Singleton

Aplicação de controle de estoque com arquitetura limpa:
Domínio (herança/polimorfismo) + DAO (JDBC/SQL) + Service (regras/orquestração) + Singleton de conexão.

Objetivo principal: registrar no banco toda vez que um novo item é criado, mantendo o domínio desacoplado de JDBC/SQL.

🔍 Sumário

Arquitetura (visão geral)

Estrutura do projeto

Por que cada classe existe

Requisitos

Como executar

Opção A) Rodar no IDE

Opção B) Linha de comando (H2 em memória)

Opção C) Linha de comando (Oracle)

O que você verá ao rodar

Personalizações comuns

DDL (Oracle)

Integrantes

🧭 Arquitetura (visão geral)
[ UI / Main ] ──▶ [ Service (regras) ] ──▶ [ DAO (JDBC/SQL) ] ──▶ [ Banco de Dados ]
                         ▲                            │
                         └──────── ConexaoBD (Singleton) ───────┘


Separação de responsabilidades → manutenção e testes mais fáceis.

Service centraliza regras de negócio e orquestra o CRUD.

DAO encapsula SQL, PreparedStatement e mapeamento ResultSet → Objeto.

ConexaoBD provê uma Connection única e configurada.

🗂️ Estrutura do projeto
src/
└─ br/com/restaurante/estoque/
   ├─ ConexaoBD.java          # Singleton da Connection (H2/Oracle)
   ├─ Item.java               # Entidade abstrata (id, nome, qtd, capacidade)
   ├─ Louca.java              # Subtipo de Item (limpo, material)
   ├─ Alimentos.java          # Subtipo de Item (validadeDias)
   ├─ EstoqueDAO.java         # Contrato do CRUD
   ├─ EstoqueDAOImpl.java     # JDBC/SQL + mapItem(ResultSet → Objeto)
   ├─ EstoqueService.java     # Regras/orquestração (criarERegistrar, listar, etc.)
   └─ App.java                # Exemplo de execução (main)
script.sql                    # DDL para criar a tabela ESTOQUE (Oracle)

🧠 Por que cada classe existe
Componente	Responsabilidade-chave
ConexaoBD	Singleton da conexão JDBC; centraliza URL/credenciais; facilita trocar de banco.
Item	Entidade abstrata com campos comuns (id, nome, qtd, capacidade).
Louca	Subtipo de Item com limpo/material.
Alimentos	Subtipo de Item com validadeDias.
EstoqueDAO	Contrato do CRUD (interface).
EstoqueDAOImpl	Implementação JDBC/SQL + mapeamento ResultSet → Louca/Alimentos.
EstoqueService	Regras de negócio e orquestração (ex.: criarERegistrar(item), validações).
App	Ponto de entrada: demonstra criação, listagem, atualização e exclusão.

💡 Regra de ouro: SQL e ResultSet ficam no DAO; validações, transação e orquestração ficam no Service.

⚙️ Requisitos

Java 17 (ou 11+) instalado e no PATH.

Driver JDBC do banco que você usará:

H2 (em memória): arquivo h2*.jar;

Oracle: arquivo ojdbc8.jar.

Coloque o .jar do driver no classpath ao compilar/rodar.

▶️ Como executar
Opção A) Rodar no IDE

Abra a pasta do projeto (não apenas arquivos soltos).

Adicione o driver JDBC do seu banco ao projeto/execução.

Abra br.com.restaurante.estoque.App e clique em Run.

Veja no console o CRUD completo (criar, listar, atualizar, excluir).

ℹ️ Usando H2: a ConexaoBD pode criar a tabela automaticamente.
ℹ️ Usando Oracle: ajuste ConexaoBD e rode o script.sql antes.

Opção B) Linha de comando (H2 em memória)

1) Baixe h2*.jar e deixe na pasta do projeto (ou use caminho completo).
2) Compile:

Windows (PowerShell/CMD):

javac -cp ".;h2.jar" -d out src/br/com/restaurante/estoque/*.java


Linux/macOS (bash/zsh):

javac -cp ".:h2.jar" -d out src/br/com/restaurante/estoque/*.java


3) Execute:

Windows:

java -cp "out;h2.jar" br.com.restaurante.estoque.App


Linux/macOS:

java -cp "out:h2.jar" br.com.restaurante.estoque.App

Opção C) Linha de comando (Oracle)

1) Ajuste ConexaoBD.java (URL/USUÁRIO/SENHA).
Exemplo de URL: jdbc:oracle:thin:@localhost:1521:XE
2) Execute script.sql no seu schema.
3) Compile:

Windows:

javac -cp ".;ojdbc8.jar" -d out src/br/com/restaurante/estoque/*.java


Linux/macOS:

javac -cp ".:ojdbc8.jar" -d out src/br/com/restaurante/estoque/*.java


4) Rode:

Windows:

java -cp "out;ojdbc8.jar" br.com.restaurante.estoque.App


Linux/macOS:

java -cp "out:ojdbc8.jar" br.com.restaurante.estoque.App

✅ O que você verá ao rodar

Exemplo de saída:

Itens cadastrados no banco:
ID=1 | TIPO=LOUCA    | NOME=Prato de Porcelana | QTD=50 | CAP=25
ID=2 | TIPO=ALIMENTO | NOME=Arroz 5kg          | QTD=20 | CAP=5
...


A aplicação demonstra: criação → listagem → atualização → exclusão.

🧩 Personalizações comuns

Trocar de banco: altere apenas ConexaoBD (URL/credenciais/DDL).

Validações de negócio: adicione no EstoqueService (ex.: qtd >= 0, capacidade > 0).

Novos tipos de Item: crie outra subclasse e adapte mapItem(rs) no DAO.

Logs/auditoria: centralize no EstoqueService (ex.: registrar eventos após salvar).

✅ Checklist de qualidade:

 Entidades sem dependência de JDBC

 DAO sem regras de negócio

 Service com validações e orquestração

 ConexaoBD única e configurável

🗄️ DDL (Oracle)

Arquivo script.sql:

CREATE TABLE ESTOQUE (
    ID             NUMBER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    TIPO           VARCHAR2(20)    NOT NULL,
    NOME           VARCHAR2(100)   NOT NULL,
    QTD            NUMBER          NOT NULL,
    CAPACIDADE     NUMBER          NOT NULL,
    LIMPO          CHAR(1),
    MATERIAL       VARCHAR2(50),
    VALIDADE_DIAS  NUMBER
);

👥 Integrantes
Nome	RM
Eduardo Tomazela	556807
Léo Kenzo	557768
Luiz Henrique	555235
