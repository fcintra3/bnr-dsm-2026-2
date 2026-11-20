# Trabalho 1 — Guia de execução e prints

**Disciplina:** Banco de Dados Não Relacional — 2026/2
**Projeto:** API da escola (Spring Boot + MongoDB)
**Aluno:** Richard Santos

Este guia usa linguagem técnica simplificada, com base no ASD-STE100:
frases curtas, voz ativa e uma ação por frase.

---

## 1. Objetivo

Este guia tem três partes:

1. Preparar o VS Code e a API.
2. Executar as requisições na ordem correta.
3. Tirar e salvar os 20 prints exigidos.

A coleção `colecao-echoapi.json` contém todas as requisições.
A ordem da coleção é a ordem dos prints.

---

## 2. Antes de começar

Verifique estes itens:

- Java 25 ou superior instalado.
- Acesso à internet. A API usa o MongoDB Atlas.
- O arquivo `api/src/main/resources/application.properties` existe e tem a URI do MongoDB.
- A extensão EchoAPI instalada no VS Code.
- A porta 8888 livre.

Para instalar a EchoAPI, execute:

```powershell
code --install-extension EchoAPI.echoapi-for-vscode
```

Para liberar a porta 8888, feche o outro programa que usa essa porta.

> Não faça commit do arquivo `application.properties`.
> Ele contém a senha do banco e já está no `.gitignore`.

---

## 3. Iniciar a API

Se a API já estiver em execução, pule esta seção.

Abra um terminal na pasta `api`.

Execute:

```powershell
.\mvnw.cmd spring-boot:run
```

Aguarde a mensagem:

```
Tomcat started on port 8888 (http)
```

Teste a API no navegador:

```
http://localhost:8888/
```

A resposta deve ser:

```
API em funcionamento!
```

---

## 4. Preparar o layout do VS Code

Siga os passos do documento do professor:

1. Pressione `Ctrl+B`. O painel esquerdo fecha.
2. Abra a EchoAPI. Mude o layout para **Two pane view**.
3. Pressione `` Ctrl+` ``. O terminal abre na parte inferior.
4. Use `Ctrl+-` e `Ctrl++` para ajustar o zoom.
5. Deixe o VS Code maximizado.

O print deve mostrar a tela inteira do VS Code.
O print deve mostrar a requisição e a resposta.

---

## 5. Importar a coleção

1. Abra a EchoAPI.
2. Clique em **Import**.
3. Selecione o arquivo `api/PRINTS/colecao-echoapi.json`.
4. Confirme a importação.

A coleção tem seis pastas:

- `00 Preparação`
- `Alunos`
- `Turmas`
- `Matrículas`
- `Exclusões`
- `Consultas`

Se a importação falhar, use o arquivo `api/PRINTS/requisicoes.http`.
Ele funciona com a extensão REST Client.

---

## 6. Executar a sequência

Execute as requisições **na ordem da coleção**.
Não pule nenhuma requisição.
As requisições sem print preparam os dados para as próximas.

| # | Requisição | Status | Print | Item do trabalho |
|---|---|---|---|---|
| 00.1 | POST /cursos | 201 | — | preparação |
| 00.2 | POST /instrutores | 201 | — | preparação |
| 00.3 | POST /instrutores | 201 | — | preparação |
| 01 | POST /alunos | 201 | PRINT 01 | 3a |
| 02 | POST /alunos (inválido) | 400 | PRINT 02 | 3b |
| 03 | POST /alunos (Ana) | 201 | — | preparação |
| 04 | POST /alunos (Carlos) | 201 | — | preparação |
| 05 | GET /alunos | 200 | PRINT 03 | 3c |
| 06 | PUT /alunos/{id} | 200 | PRINT 04 | 3d |
| 07 | POST /turmas | 201 | PRINT 05 | 5a |
| 08 | POST /turmas (inválida) | 400 | PRINT 06 | 5b |
| 09 | POST /turmas (B) | 201 | — | preparação |
| 10 | POST /turmas (C) | 201 | — | preparação |
| 11 | GET /turmas | 200 | PRINT 07 | 5c |
| 12 | PUT /turmas/{id} | 200 | PRINT 08 | 5d |
| 13 | POST /matriculas | 201 | PRINT 09 | 4a |
| 14 | POST /matriculas (inválida) | 400 | PRINT 10 | 4b |
| 15 | POST /matriculas (Ana/B) | 201 | — | preparação |
| 16 | POST /matriculas (Ana/C) | 201 | — | preparação |
| 17 | GET /matriculas | 200 | PRINT 11 | 4c |
| 18 | PUT /matriculas/{id} | 200 | PRINT 12 | 4d |
| 19 | DELETE /matriculas/{id} | 204 | PRINT 13 | 4e |
| 20 | DELETE /alunos/{id} (Carlos) | 204 | PRINT 14 | 3e |
| 21 | DELETE /alunos/{id} (Richard) | 409 | PRINT 15 | 3f |
| 22 | DELETE /turmas/{id} (C) | 204 | PRINT 16 | 5e |
| 23 | DELETE /turmas/{id} (A) | 409 | PRINT 17 | 5f |
| 24 | GET /matriculas/aluno/{id} | 200 | PRINT 18 | 6a |
| 25 | GET /matriculas/curso/{id} | 200 | PRINT 19 | 6b |
| 26 | GET /turmas/curso/{id} | 200 | PRINT 20 | 6c |

Para cada requisição com print:

1. Clique em **Send**.
2. Confira o status da resposta.
3. Tire o print.
4. Salve o arquivo com o nome da tabela da seção 7.

---

## 7. Nomes dos arquivos

Salve os prints na pasta `api/PRINTS/`.

| Print | Nome do arquivo | Conteúdo esperado |
|---|---|---|
| 01 | `01-aluno-criado-201.png` | Aluno Richard com 2 endereços, status 201 |
| 02 | `02-aluno-invalido-400.png` | Erros de validação, status 400 |
| 03 | `03-alunos-listados-200.png` | Lista de alunos, status 200 |
| 04 | `04-aluno-atualizado-200.png` | Aluno atualizado, status 200 |
| 05 | `05-turma-criada-201.png` | Turma com curso e instrutor, status 201 |
| 06 | `06-turma-invalida-400.png` | Erros de validação, status 400 |
| 07 | `07-turmas-listadas-200.png` | Lista de turmas, status 200 |
| 08 | `08-turma-atualizada-200.png` | Turma atualizada, status 200 |
| 09 | `09-matricula-criada-201.png` | Matrícula criada, status 201 |
| 10 | `10-matricula-invalida-400.png` | Erros de validação, status 400 |
| 11 | `11-matriculas-listadas-200.png` | Lista com aluno e turma completos, status 200 |
| 12 | `12-matricula-atualizada-200.png` | Matrícula atualizada, status 200 |
| 13 | `13-matricula-excluida-204.png` | Exclusão, status 204 |
| 14 | `14-aluno-excluido-204.png` | Exclusão de aluno sem matrícula, status 204 |
| 15 | `15-aluno-com-matricula-409.png` | Conflito, status 409 |
| 16 | `16-turma-excluida-204.png` | Exclusão de turma sem matrícula, status 204 |
| 17 | `17-turma-com-matricula-409.png` | Conflito, status 409 |
| 18 | `18-matriculas-do-aluno-200.png` | Matrículas do aluno, status 200 |
| 19 | `19-matriculas-do-curso-200.png` | Matrículas do curso, status 200 |
| 20 | `20-turmas-do-curso-200.png` | Turmas do curso, status 200 |

### Listas longas

A lista pode passar do tamanho da tela.
Role o painel direito da EchoAPI.
Tire prints adicionais.
Use os sufixos `-b`, `-c` e assim por diante.

Exemplo:

```
03-alunos-listados-200.png
03-alunos-listados-200-b.png
```

Não corte a barra de endereço, o método, o status e o corpo da resposta.

---

## 8. Checklist final

Confira antes de começar:

- [ ] A API responde em `http://localhost:8888/`.
- [ ] Os 20 prints estão na pasta `api/PRINTS/`.
- [ ] Cada print mostra método, URL, status e corpo.
- [ ] O print 01 mostra o nome completo do aluno.
- [ ] O print 02 mostra as mensagens de validação.
- [ ] O print 15 mostra status 409.
- [ ] O print 17 mostra status 409.
- [ ] Os prints 18, 19 e 20 mostram as listas completas.

---

## 9. Problemas comuns

| Problema | Causa | Solução |
|---|---|---|
| `Connection refused` | A API não está em execução | Inicie a API com `.\mvnw.cmd spring-boot:run` |
| `Port 8888 was already in use` | Outro programa usa a porta | Feche o outro programa |
| `404` no POST de matrícula | O aluno ou a turma não existe | Execute as etapas 00 e 03 a 12 |
| Lista vazia no GET | A sequência foi executada fora de ordem | Limpe o banco e repita a sequência |
| `409` no DELETE de aluno | O aluno tem matrícula | Esse é o resultado esperado do PRINT 15 |
| `409` no DELETE de turma | A turma tem matrícula | Esse é o resultado esperado do PRINT 17 |
| Erro de conexão com o MongoDB | Internet ou URI incorreta | Confira `application.properties` e a rede |

Para limpar o banco e repetir a sequência, execute:

```powershell
.\scripts\limpar-banco.ps1
```


