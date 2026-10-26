# API da escola

Implementação das atividades de 21/09 a 19/10 do roteiro da disciplina.
Requer Java 25 e uma instância MongoDB.

## Executar

Na pasta `api`, copie `src/main/resources/application.properties.example` para
`src/main/resources/application.properties` e preencha `spring.mongodb.uri` com a
URI do MongoDB, incluindo o banco `escola`. O arquivo local é ignorado pelo Git.

```sh
./mvnw spring-boot:run
```

A API atende em `http://localhost:8888`.

## Endpoints

As rotas `/alunos`, `/cursos`, `/instrutores`, `/turmas` e `/matriculas` oferecem:

| Método | Caminho | Resultado |
| --- | --- | --- |
| GET | `/` da entidade | 200 com a lista |
| GET | `/{id}` | 200 com o documento ou 404 |
| POST | `/` da entidade | 201 com o documento criado |
| PUT | `/{id}` | 200 com o documento substituído ou 404 |
| DELETE | `/{id}` | 204 após excluir ou 404 |

No PUT, envie todos os campos do documento. O identificador usado é o da URL.
Endereços são incorporados em alunos e instrutores; módulos em cursos;
horários em turmas; notas em matrículas. Esses subdocumentos são mantidos
pelo CRUD da entidade principal.

Consultas por vínculo:

- `GET /matriculas/aluno/{alunoId}`
- `GET /matriculas/turma/{turmaId}`
- `GET /turmas/curso/{cursoId}`

Essas consultas retornam 200 com a lista (inclusive `[]`) quando a entidade
consultada existe, ou 404 quando não existe.

POST e PUT de matrículas exigem `alunoId` e `turmaId` existentes. POST e PUT
de turmas exigem `cursoId` existente. Identificadores obrigatórios ausentes,
vazios ou compostos apenas de espaços resultam em 400; referências inexistentes
resultam em 404.

A exclusão de alunos ou turmas com matrículas vinculadas retorna 409, assim
como a exclusão de cursos com turmas vinculadas. O bloqueio considera todas
as matrículas, independentemente de sua situação.

## Testes

```sh
./mvnw test -Dspring.mongodb.uri=mongodb://localhost:27017/escola_test
```

Os testes HTTP usam MockMvc com serviços reais e repositórios simulados,
cobrindo CRUD, subdocumentos, consultas e falhas de integridade. Não gravam no
MongoDB. O teste de inicialização carrega o contexto da aplicação; ele não
valida operações de persistência em uma instância MongoDB real.
