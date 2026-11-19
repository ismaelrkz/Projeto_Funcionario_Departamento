# Projeto Funcionário & Departamento: API RESTful com Spring Boot e Spring Data JPA

API REST desenvolvida com Java e Spring Boot para gerenciar departamentos, funcionários e projetos, incluindo os relacionamentos entre essas entidades. Atualmente, oferece operações de cadastro, consulta, atualização e exclusão, além de buscas e operações para consultar relações entre funcionários, departamentos e projetos.

A aplicação utiliza arquitetura em camadas: controllers recebem as requisições HTTP, services aplicam as regras de negócio e repositories acessam os dados persistidos no MySQL por meio do Spring Data JPA. Entidades JPA representam os dados persistidos, DTOs definem as informações recebidas e retornadas pela API, e exceções personalizadas tratam erros previstos, como dados inválidos, conflitos e recursos não encontrados.

O projeto está em desenvolvimento e faz parte do meu portfólio de estudos em desenvolvimento backend com Java e Spring Boot. Como próximos passos, planejo ampliar os testes automatizados, evoluir a modelagem e versionar as alterações do banco com migrations, preparar a execução com Docker e estudar autenticação e autorização. Um frontend para consumir a API poderá ser desenvolvido em uma etapa posterior.

---

# Objetivo do Projeto

O objetivo deste projeto é demonstrar de forma prática a construção de uma API backend, aplicando conceitos fundamentais do ecossistema Spring Boot, incluindo:

- Arquitetura em camadas com separação de responsabilidades (Model, Repository, Service, Controller e Exceptions).
- Mapeamento JPA/Hibernate de relacionamentos entre entidades.
- Relacionamentos **1:N** entre Departamentos e Funcionários.
- Relacionamento **M:N** entre Projetos e Funcionários, utilizando tabela associativa.
- Auto-relacionamento entre Funcionários para representar a hierarquia de supervisão.
- Abstração de persistência por meio do Spring Data JPA e `ListCrudRepository`.
- Consultas personalizadas com JPQL para buscas parciais e verificações de duplicidade.
- Injeção de dependências via construtor, utilizando atributos `private final`.
- Separação entre entidades JPA e dados de entrada e saída por meio de DTOs.
- Validações de regras de negócio na camada de serviço.
- Tratamento global e centralizado de exceções HTTP utilizando `@ControllerAdvice`.
- Controle de respostas HTTP por meio de `ResponseEntity`.

---

# Estrutura e Arquitetura da Aplicação

A aplicação foi estruturada seguindo o padrão de arquitetura em camadas, no qual cada componente possui uma responsabilidade específica:

- **Model Layer (`.model`):** Representa as entidades persistidas no banco de dados. Contém o mapeamento ORM das tabelas `tbl_departamento`, `tbl_funcionario`, `tbl_projeto` e da tabela associativa `tbl_funcionario_projeto`.
- **Repository Layer (`.repository`):** Interfaces que estendem `ListCrudRepository`, responsáveis pelo acesso e pela consulta dos dados persistidos. Também concentram consultas personalizadas utilizando JPQL.
- **Service Layer (`.service`):** Concentra as regras de negócio, validações, verificações de existência e duplicidade, operações CRUD e gerenciamento dos relacionamentos entre as entidades.
- **Controller Layer (`.controller`):** Porta de entrada da API. Recebe as requisições HTTP, encaminha os dados aos Services e define as respostas utilizando DTOs e `ResponseEntity`.
- **Exceptions Layer (`.exceptions`):** Contém exceções personalizadas utilizadas para representar erros de negócio, como requisições inválidas, conflitos e recursos não encontrados. O tratamento global é centralizado no `ExceptionsController`, por meio de `@ControllerAdvice`.
- **DTO Layer (`.dto`):** Contém os objetos utilizados na comunicação da API, separando os dados recebidos nas requisições das informações devolvidas nas respostas.

---

# Estrutura das Tabelas e Mapeamento ORM

O banco de dados relacional gerenciado pelo Hibernate utiliza três entidades principais e uma tabela associativa para representar os relacionamentos:

| Tabela | Descrição | Mapeamento JPA |
|:---|:---|:---|
| `tbl_departamento` | Cadastro de departamentos | Entidade `DepartamentoModel` |
| `tbl_funcionario` | Cadastro de funcionários | Entidade `FuncionarioModel` |
| `tbl_projeto` | Cadastro de projetos | Entidade `ProjetoModel` |
| `tbl_funcionario_projeto` | Associação entre funcionários e projetos | Configurada via `@JoinTable` em `ProjetoModel` |

### Diretrizes de Mapeamento:

- **Departamento e Funcionários (1:N):** Um departamento pode possuir vários funcionários. A associação é representada por `@OneToMany` em `DepartamentoModel` e `@ManyToOne` em `FuncionarioModel`. O lado proprietário é o funcionário, que mantém a chave estrangeira `depto_func`.
- **Funcionário e Supervisor (N:1):** Um funcionário pode possuir um supervisor, que também é representado pela entidade `FuncionarioModel`. O relacionamento é implementado por meio de `@ManyToOne` e da chave estrangeira `id_supervisor` na própria tabela `tbl_funcionario`.
- **Projetos e Funcionários (M:N):** Um projeto pode possuir vários funcionários em sua equipe, e um funcionário pode participar de vários projetos. O relacionamento é controlado por `ProjetoModel`, utilizando `@ManyToMany` e `@JoinTable`.
- **Tabela Associativa:** A tabela `tbl_funcionario_projeto` armazena os vínculos entre projetos e funcionários por meio das colunas `id_projeto` e `id_func`. Como o modelo atual utiliza essa tabela apenas para representar a associação, não é necessária uma entidade intermediária própria.

---

# Tecnologias Utilizadas

- **Java**
- **Spring Boot**
- **Spring Web**
- **Spring Data JPA**
- **Hibernate**
- **MySQL Driver**
- **MySQL Server**

---

# Conteúdos Demonstrados

O projeto exemplifica conceitos importantes do desenvolvimento backend com Java e Spring Boot.

## Mapeamento e Persistência (JPA / Hibernate)

- Mapeamento de entidades utilizando `@Entity`, `@Table`, `@Id`, `@GeneratedValue` e `@Column`.
- Relacionamentos 1:N utilizando `@OneToMany` e `@ManyToOne`.
- Auto-relacionamento entre entidades por meio de `@ManyToOne`.
- Relacionamentos M:N utilizando `@ManyToMany`, `@JoinTable` e `@JoinColumn`.
- Definição dos lados proprietário e inverso dos relacionamentos com `mappedBy`.
- Abstração das operações de persistência por meio de `ListCrudRepository`.
- Consultas personalizadas utilizando JPQL e `@Query`.
- Configuração do gerenciamento do schema por meio de `spring.jpa.hibernate.ddl-auto`.

## Camada de Negócio e Serviços

- Separação das regras de negócio na camada Service.
- Injeção de dependências via construtor.
- Utilização de atributos `private final` para dependências.
- Validações de campos obrigatórios e dados inválidos.
- Verificação de existência e duplicidade de registros.
- Atualizações de registros com preservação dos campos não alterados.
- Gerenciamento da equipe de funcionários associada aos projetos.
- Conversão de entidades JPA em DTOs para as respostas da API.

## DTOs e Transferência de Dados

- Separação entre objetos de requisição e resposta.
- Utilização de `record` para representar DTOs.
- DTOs simplificados para controlar a exposição de informações relacionadas.
- Prevenção da exposição direta das entidades JPA nas requisições e respostas.

## Tratamento Global de Erros e API REST

- Interceptação global de exceções utilizando `@ControllerAdvice`.
- Tratamento de exceções personalizadas:
    - `BadRequestException` → HTTP `400 Bad Request`.
    - `ConflictException` → HTTP `409 Conflict`.
    - `NotFoundException` → HTTP `404 Not Found`.
- Tratamento de falhas de desserialização com `HttpMessageNotReadableException`.
- Tratamento de exceções não previstas com resposta HTTP `500 Internal Server Error`.
- Uso de `ResponseEntity` para controlar os status HTTP e os corpos das respostas.
- Utilização de `@PathVariable` para identificação de recursos e `@RequestParam` para buscas por nome.

---

# Endpoints da API

A API disponibiliza operações para gerenciamento de departamentos, funcionários e projetos.

| Método | Endpoint | Operação |
|:---|:---|:---|
| `POST` | `/departamentos` | Cadastrar departamento |
| `GET` | `/departamentos` | Listar departamentos |
| `GET` | `/departamentos/{id}` | Consultar departamento por ID |
| `GET` | `/departamentos/search?departamento=...` | Buscar departamentos por nome |
| `GET` | `/departamentos/{id}/funcionarios` | Consultar funcionários do departamento |
| `PUT` | `/departamentos/{id}` | Atualizar departamento |
| `DELETE` | `/departamentos/{id}` | Excluir departamento |
| `POST` | `/funcionarios` | Cadastrar funcionário |
| `GET` | `/funcionarios` | Listar funcionários |
| `GET` | `/funcionarios/{id}` | Consultar funcionário por ID |
| `GET` | `/funcionarios/search?funcionario=...` | Buscar funcionários por nome |
| `GET` | `/funcionarios/{id}/projetos` | Consultar projetos do funcionário |
| `PUT` | `/funcionarios/{id}` | Atualizar funcionário |
| `DELETE` | `/funcionarios/{id}` | Excluir funcionário |
| `POST` | `/projetos` | Cadastrar projeto |
| `GET` | `/projetos` | Listar projetos |
| `GET` | `/projetos/{id}` | Consultar projeto e sua equipe |
| `GET` | `/projetos/search?projeto=...` | Buscar projetos por nome |
| `PUT` | `/projetos/{id}` | Atualizar projeto |
| `PUT` | `/projetos/equipe/{id}` | Adicionar funcionários à equipe do projeto |
| `DELETE` | `/projetos/{id}` | Excluir projeto |

---

# Configuração do Ambiente

A aplicação utiliza o MySQL como banco de dados. Para configurar a conexão local:

1. Crie um schema no MySQL.
2. Copie o arquivo `application.properties.example` para `application.properties`.
3. Informe o nome do schema, usuário e senha utilizados na conexão.
4. Execute a aplicação Spring Boot.

O arquivo `application.properties.example` serve como modelo de configuração, permitindo que as credenciais locais permaneçam fora do controle de versão.

---

# Conclusão

Este projeto demonstra na prática a construção de uma API backend com Java e Spring Boot, aplicando conceitos de arquitetura em camadas, persistência relacional, mapeamento ORM, DTOs, regras de negócio e tratamento global de exceções.

A implementação permite gerenciar departamentos, funcionários e projetos, incluindo relacionamentos entre as entidades e operações específicas para consulta e composição de equipes.

O projeto está em evolução, e sua estrutura foi organizada para permitir a incorporação gradual de novos recursos, melhorias arquiteturais e práticas de desenvolvimento backend.

---

# Documentação Complementar

A documentação técnica completa do projeto, contendo o detalhamento das entidades, repositories, services, controllers, exceções, DTOs e regras de negócio implementadas, está disponível na documentação complementar do projeto.

**[Documentação Técnica do Projeto: Projeto Funcionário & Departamento](https://app.notion.com/p/Projeto_Funcionario_Departamento-3a6f4d816bcb80699d9ec2a70edcda32?source=copy_link)**

**[Projeto Banco de Dados Funcionário & Departamento](https://github.com/ismaelrkz/Banco_De_Dados_Projetos/tree/main/01_Projeto_BD_Funcionarios_Departamentos)**


