# Projeto Labels & Keywords: API RESTful com Spring Boot e Spring Data JPA

Este repositório apresenta a implementação de uma **API RESTful completa** desenvolvida em **Java** com **Spring Boot**, voltada ao gerenciamento e vinculação de **Labels** (rótulos) e **Keywords** (palavras-chave).

O projeto aborda a aplicação prática de uma arquitetura em camadas desacoplada (*Skinny Controller, Fat Service*), mapeamento objeto-relacional (ORM) de relacionamentos **Muitos-para-Muitos (M:N)** com tabela associativa, tratamento global de exceções customizadas e persistência de dados em banco relacional **MySQL**.

---

# Objetivo do Projeto

O objetivo deste projeto é demonstrar de forma prática a construção de uma API backend robusta, aplicando as melhores práticas do ecossistema Spring Boot, incluindo:

- Arquitetura em camadas com separação clara de responsabilidades (Model, Repo, Service, Controller e Exceptions)
- Mapeamento JPA/Hibernate de relacionamentos N:N com `@ManyToMany` e `@JoinTable`
- Abstração de persistência e geração de *Derived Queries* via `ListCrudRepository`
- Injeção de dependências via construtor com atributos `private final` (imutabilidade e testabilidade)
- Validações defensivas na camada de serviço utilizando a classe `Optional`
- Tratamento global e centralizado de exceções HTTP utilizando `@ControllerAdvice`
- Mapeamento e controle preciso de status HTTP com `ResponseEntity`

---

# Estrutura e Arquitetura da Aplicação

A aplicação foi estruturada seguindo o padrão de arquitetura em camadas, onde cada componente possui uma responsabilidade única e bem definida:

- **Model Layer (`.model`):** Mapeamento O/R das tabelas `tbl_label`, `tbl_keyword` e da tabela associativa `tbl_label_keyword`.
- **Repository Layer (`.repo`):** Interfaces estendendo `ListCrudRepository`, encarregadas da comunicação direta com o banco de Dados.
- **Service Layer (`.service`):** O coração do sistema. Concentra toda a lógica de negócio, validações de unicidade/existência e orquestração do CRUD.
- **Controller Layer (`.controller`):** Porta de entrada da API. Trata rotas, parâmetros (`@PathVariable` e `@RequestParam`) e monta respostas HTTP.
- **Exceptions Layer (`.exceptions`):** Escudo de proteção global com exceções de domínio não-checadas e interceptação centralizada via `@ControllerAdvice`.

---

# Estrutura das Tabelas e Mapeamento ORM

O banco de dados relacional gerido pelo Hibernate é composto pela seguinte estrutura:

| Tabela | Descrição | Mapeamento JPA |
|:---|:---|:---|
| `tbl_label` | Cadastro único de rótulos | Entidade `LabelModel` (Lado proprietário) |
| `tbl_keyword` | Cadastro único de palavras-chave | Entidade `KeywordModel` (Lado passivo) |
| `tbl_label_keyword` | Tabela associativa que mapeia o relacionamento M:N | Configurada via `@JoinTable` na `LabelModel` |

### Diretrizes de Mapeamento:
- **Unidirecionalidade:** A entidade `LabelModel` possui a coleção `List<KeywordModel>`, permitindo navegar até as palavras-chave. A `KeywordModel` é totalmente desacoplada e não conhece a existência de `LabelModel`.
- **Tabela Associativa:** Mapeada de forma transparente com `@JoinTable` e `@JoinColumn`, conectando as chaves estrangeiras (`tbl_label_id_label` e `tbl_keyword_id_keyword`) sem a necessidade de criar uma classe de entidade intermediária manual.

---

# Tecnologias Utilizadas

- **Java 25**
- **Spring Boot 4.1.0**
- **Spring Web**
- **Spring Data JPA & Hibernate**
- **MySQL Driver**
- **Spring Boot DevTools**
- **MySQL Server**
- **IntelliJ IDEA**
- **VS Code**

---

# Conteúdos Demonstrados

O projeto exemplifica padrões avançados de desenvolvimento backend:

## Mapeamento e Persistência (JPA / Hibernate)

- Mapeamento de Entidades (`@Entity`, `@Table`, `@Id`, `@GeneratedValue`)
- Relacionamentos Associativos (`@ManyToMany`, `@JoinTable`, `@JoinColumn`)
- Consultas Derivadas (*Query Methods*) para filtros e navegação por relacionamentos (`findByKeyword`, `findByKeywordsKeyword`)
- Validação incremental do schema via `spring.jpa.hibernate.ddl-auto`

## Camada de Negócio e Serviços

- Injeção de dependência defensiva via construtor
- Uso idiomático de `Optional.orElseThrow()` para manipulação limpa de ausência de dados
- Mapeamento defensivo M:N (distinção semântica entre busca por Keyword inexistente e Keyword sem Labels vinculadas)

## Tratamento Global de Erros e Rest API

- Interceptador global `@ControllerAdvice` (`ExceptionsController`)
- Mapeamento de exceções customizadas (`NotFoundException` -> 404, `ConflictException` -> 409, `Exception` -> 500)
- Aplicação do princípio *Skinny Controller, Fat Service*
- Uso semântico de `@PathVariable` (identificação de recurso) vs `@RequestParam` (filtros e busca)

---

# Conclusão

Este projeto demonstra na prática a construção de uma aplicação Java backend moderna, organizada e aderente aos princípios do ecossistema Spring Boot. Através da divisão estrita de responsabilidades em camadas, a aplicação garante baixo acoplamento, alta testabilidade, tratamento amigável de falhas HTTP e gerenciamento nativo de relacionamentos relacionais complexos M:N.

O código-fonte completo com todas as classes de Model, Repo, Service, Controller e Exceptions está devidamente versionado neste repositório.

---

# Documentação Complementar

A documentação completa e oficial do projeto, contendo o detalhamento passo a passo da inicialização, mapa mental do fluxo da requisição, anotações detalhadas e notas de arquitetura, está disponível no link abaixo:

**[Documentação Oficial do Projeto: Projeto_Labels_Keywords](https://app.notion.com/p/Projeto_Labels_Keywords-3a6f4d816bcb8068b1a2f5185e65cf57?source=copy_link)**