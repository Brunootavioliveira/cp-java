# CP6 - API Ocorrências Escolares

API RESTful desenvolvida em **Java 17** com **Spring Boot 3** e **Spring Data JPA** para o Checkpoint 6 da disciplina **Arquitetura SOA e Web Services**.

## Integrantes do grupo

| Nome | RM |
| --- | --- |
| Bruno Otavio Silva De Oliveira | RM556196 |
| Guilherme Flores Pereira de Almeida | RM554948 |
| Luiz Fernando de Aragão Souza | RM555561 |
| Marcello de Freitas Moreira | RM557531 |
| Leonardo Gonçalves Novaes | RM554807 |

## Contexto do projeto

A **API de Ocorrências Escolares** é o back-end do sistema de registro e acompanhamento de ocorrências de alunos de uma instituição de ensino. Ela permite cadastrar, consultar, atualizar e remover **ocorrências**, além de manter os cadastros de apoio previstos no MER: **alunos**, **turmas** (com vínculo de alunos), **categorias** e **tipos de ocorrência**.

**Problema que resolve:** o controle de ocorrências feito de forma manual gera registros dispersos e dificulta consultas e reuniões pedagógicas. A API centraliza essas informações, valida os dados na entrada e expõe uma interface padronizada para ser consumida por qualquer aplicação web ou mobile.

**Público-alvo:** professores, analistas de qualidade, coordenadores e professores administrativos da instituição, além de desenvolvedores que precisem integrar o sistema.

## Regras de negócio

- O aluno da ocorrência deve estar vinculado à turma informada.
- O tipo de ocorrência deve pertencer à categoria informada.
- Somente usuários com perfil Professor registram ocorrências.
- Somente o professor que registrou a ocorrência pode alterá-la.
- Um aluno só pode ser vinculado uma vez à mesma turma.

## Tecnologias

- Java 17
- Spring Boot 3.3 (Web, Data JPA, Validation)
- Banco de dados H2 em memória
- Maven

## Como executar

Pré-requisitos: JDK 17 ou superior e Maven.

```bash
git clone <url-do-repositorio>
cd gestao-ocorrencias
mvn spring-boot:run
```

A API sobe em `http://localhost:8080`. O console do banco fica em `http://localhost:8080/h2-console` (JDBC URL `jdbc:h2:mem:ocorrencias`, usuário `sa`, senha vazia).

Ao iniciar, a aplicação carrega dados de exemplo: perfis, quatro usuários (um por perfil), três categorias, três tipos, uma turma e dois alunos vinculados. O professor de exemplo tem `id` 1, e os demais registros começam no `id` 1 de cada tabela.

## Modelo de dados

`PERFIL` 1:N `USUARIO` · `USUARIO` 1:N `OCORRENCIA` · `ALUNO` N:M `TURMA` · `ALUNO` 1:N `OCORRENCIA` · `TURMA` 1:N `OCORRENCIA` · `CATEGORIA` 1:N `TIPO_OCORRENCIA` · `CATEGORIA` 1:N `OCORRENCIA` · `TIPO_OCORRENCIA` 1:N `OCORRENCIA`

## Endpoints

### Ocorrências

| Método | Rota | Descrição | Retorno |
| --- | --- | --- | --- |
| GET | `/ocorrencias` | Lista ocorrências com filtros opcionais: `alunoId`, `turmaId`, `categoriaId`, `tipoId`, `dataInicio`, `dataFim` (`AAAA-MM-DD`) | 200 |
| GET | `/ocorrencias/{id}` | Detalha uma ocorrência | 200 / 404 |
| POST | `/ocorrencias` | Registra uma ocorrência | 201 / 400 / 404 / 422 |
| PUT | `/ocorrencias/{id}` | Atualiza uma ocorrência | 200 / 400 / 404 / 422 |
| DELETE | `/ocorrencias/{id}` | Exclui uma ocorrência | 204 / 404 |

Exemplo de corpo para `POST /ocorrencias`:

```json
{
  "turmaId": 1,
  "alunoId": 1,
  "usuarioId": 1,
  "categoriaId": 1,
  "tipoId": 1,
  "data": "2026-11-03",
  "hora": "19:30:00",
  "descricao": "Aluno foi advertido por conversa excessiva durante a aula."
}
```

### Cadastros de apoio

| Recurso | Rotas |
| --- | --- |
| Alunos | `GET /alunos?nome=`, `GET /alunos/{id}`, `POST /alunos`, `PUT /alunos/{id}`, `DELETE /alunos/{id}` |
| Turmas | `GET /turmas`, `GET /turmas/{id}`, `POST /turmas`, `PUT /turmas/{id}`, `DELETE /turmas/{id}` |
| Vínculo aluno/turma | `GET /turmas/{id}/alunos`, `POST /turmas/{id}/alunos/{alunoId}`, `DELETE /turmas/{id}/alunos/{alunoId}` |
| Categorias | `GET /categorias`, `GET /categorias/{id}`, `POST /categorias`, `PUT /categorias/{id}`, `DELETE /categorias/{id}` |
| Tipos de ocorrência | `GET /tipos-ocorrencia?categoriaId=`, `GET /tipos-ocorrencia/{id}`, `POST /tipos-ocorrencia`, `PUT /tipos-ocorrencia/{id}`, `DELETE /tipos-ocorrencia/{id}` |

Corpos de exemplo:

```json
{ "nome": "Maria Santos", "dataNascimento": "2007-07-02", "foto": "maria.png" }
```

```json
{ "nome": "Turma B", "turno": "MANHA", "ano": 2026, "semestre": 2 }
```

```json
{ "nome": "Disciplinar" }
```

```json
{ "nome": "Indisciplina", "categoriaId": 1 }
```

## Tratamento de erros

As respostas de erro seguem o formato:

```json
{
  "status": 400,
  "mensagem": "Dados inválidos",
  "detalhes": ["descricao: A descrição é obrigatória"],
  "timestamp": "2026-11-03T19:30:00"
}
```

| Status | Situação |
| --- | --- |
| 400 | Dados inválidos ou corpo malformado |
| 404 | Recurso não encontrado |
| 409 | Registro em uso por outro cadastro |
| 422 | Regra de negócio violada |
