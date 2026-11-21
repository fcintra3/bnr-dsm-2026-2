# Teste do CRUD — Curso e Modulo

Testes executados em 19/10/2026 contra a API local (`http://localhost:8888`) conectada ao MongoDB Atlas (banco `escola`, coleção `cursos`). O `Modulo` é um documento embutido (lista `modulos`) dentro de `Curso`.

## 1. Create (POST)

### Criar curso 1

```http
POST /cursos
Content-Type: application/json

{"nome":"Banco de Dados Não Relacional","cargaHoraria":80,"valor":1500.00,"modulos":[{"titulo":"Introdução ao MongoDB","conteudo":"Conceitos de NoSQL, documentos e coleções","cargaHoraria":20},{"titulo":"Modelagem NoAM","conteudo":"Agregados, entidades e relacionamentos","cargaHoraria":30},{"titulo":"API REST com Spring Boot","conteudo":"CRUD com Spring Data MongoDB","cargaHoraria":30}]}
```

**Resposta:** HTTP 201

```json
{
  "id": "6ad6a6077893ffec9230b97b",
  "nome": "Banco de Dados Não Relacional",
  "cargaHoraria": 80,
  "valor": 1500.0,
  "modulos": [
    {
      "titulo": "Introdução ao MongoDB",
      "conteudo": "Conceitos de NoSQL, documentos e coleções",
      "cargaHoraria": 20
    },
    {
      "titulo": "Modelagem NoAM",
      "conteudo": "Agregados, entidades e relacionamentos",
      "cargaHoraria": 30
    },
    {
      "titulo": "API REST com Spring Boot",
      "conteudo": "CRUD com Spring Data MongoDB",
      "cargaHoraria": 30
    }
  ]
}
```

### Criar curso 2

```http
POST /cursos
Content-Type: application/json

{"nome":"Algoritmos e Lógica de Programação","cargaHoraria":60,"valor":980.50,"modulos":[{"titulo":"Fundamentos","conteudo":"Variáveis, tipos e operadores","cargaHoraria":30},{"titulo":"Estruturas de controle","conteudo":"Condicionais e repetições","cargaHoraria":30}]}
```

**Resposta:** HTTP 201

```json
{
  "id": "6ad6a6087893ffec9230b97c",
  "nome": "Algoritmos e Lógica de Programação",
  "cargaHoraria": 60,
  "valor": 980.5,
  "modulos": [
    {
      "titulo": "Fundamentos",
      "conteudo": "Variáveis, tipos e operadores",
      "cargaHoraria": 30
    },
    {
      "titulo": "Estruturas de controle",
      "conteudo": "Condicionais e repetições",
      "cargaHoraria": 30
    }
  ]
}
```

## 2. Read (GET)

### Listar todos os cursos

```http
GET /cursos
```

**Resposta:** HTTP 200

```json
[
  {
    "id": "6ad6a6077893ffec9230b97b",
    "nome": "Banco de Dados Não Relacional",
    "cargaHoraria": 80,
    "valor": 1500.0,
    "modulos": [
      {
        "titulo": "Introdução ao MongoDB",
        "conteudo": "Conceitos de NoSQL, documentos e coleções",
        "cargaHoraria": 20
      },
      {
        "titulo": "Modelagem NoAM",
        "conteudo": "Agregados, entidades e relacionamentos",
        "cargaHoraria": 30
      },
      {
        "titulo": "API REST com Spring Boot",
        "conteudo": "CRUD com Spring Data MongoDB",
        "cargaHoraria": 30
      }
    ]
  },
  {
    "id": "6ad6a6087893ffec9230b97c",
    "nome": "Algoritmos e Lógica de Programação",
    "cargaHoraria": 60,
    "valor": 980.5,
    "modulos": [
      {
        "titulo": "Fundamentos",
        "conteudo": "Variáveis, tipos e operadores",
        "cargaHoraria": 30
      },
      {
        "titulo": "Estruturas de controle",
        "conteudo": "Condicionais e repetições",
        "cargaHoraria": 30
      }
    ]
  }
]
```

### Buscar curso 1 por id

```http
GET /cursos/6ad6a6077893ffec9230b97b
```

**Resposta:** HTTP 200

```json
{
  "id": "6ad6a6077893ffec9230b97b",
  "nome": "Banco de Dados Não Relacional",
  "cargaHoraria": 80,
  "valor": 1500.0,
  "modulos": [
    {
      "titulo": "Introdução ao MongoDB",
      "conteudo": "Conceitos de NoSQL, documentos e coleções",
      "cargaHoraria": 20
    },
    {
      "titulo": "Modelagem NoAM",
      "conteudo": "Agregados, entidades e relacionamentos",
      "cargaHoraria": 30
    },
    {
      "titulo": "API REST com Spring Boot",
      "conteudo": "CRUD com Spring Data MongoDB",
      "cargaHoraria": 30
    }
  ]
}
```

### Buscar id inexistente (esperado 404)

```http
GET /cursos/000000000000000000000000
```

**Resposta:** HTTP 404

## 3. Update (PUT)

### Atualizar curso 1 (carga horária, valor e novo módulo)

```http
PUT /cursos/6ad6a6077893ffec9230b97b
Content-Type: application/json

{"nome":"Banco de Dados Não Relacional","cargaHoraria":90,"valor":1750.00,"modulos":[{"titulo":"Introdução ao MongoDB","conteudo":"Conceitos de NoSQL, documentos e coleções","cargaHoraria":20},{"titulo":"Modelagem NoAM","conteudo":"Agregados, entidades e relacionamentos","cargaHoraria":30},{"titulo":"API REST com Spring Boot","conteudo":"CRUD com Spring Data MongoDB","cargaHoraria":30},{"titulo":"Agregações e índices","conteudo":"Aggregation pipeline e índices","cargaHoraria":10}]}
```

**Resposta:** HTTP 200

```json
{
  "id": "6ad6a6077893ffec9230b97b",
  "nome": "Banco de Dados Não Relacional",
  "cargaHoraria": 90,
  "valor": 1750.0,
  "modulos": [
    {
      "titulo": "Introdução ao MongoDB",
      "conteudo": "Conceitos de NoSQL, documentos e coleções",
      "cargaHoraria": 20
    },
    {
      "titulo": "Modelagem NoAM",
      "conteudo": "Agregados, entidades e relacionamentos",
      "cargaHoraria": 30
    },
    {
      "titulo": "API REST com Spring Boot",
      "conteudo": "CRUD com Spring Data MongoDB",
      "cargaHoraria": 30
    },
    {
      "titulo": "Agregações e índices",
      "conteudo": "Aggregation pipeline e índices",
      "cargaHoraria": 10
    }
  ]
}
```

### Conferir curso 1 após atualização

```http
GET /cursos/6ad6a6077893ffec9230b97b
```

**Resposta:** HTTP 200

```json
{
  "id": "6ad6a6077893ffec9230b97b",
  "nome": "Banco de Dados Não Relacional",
  "cargaHoraria": 90,
  "valor": 1750.0,
  "modulos": [
    {
      "titulo": "Introdução ao MongoDB",
      "conteudo": "Conceitos de NoSQL, documentos e coleções",
      "cargaHoraria": 20
    },
    {
      "titulo": "Modelagem NoAM",
      "conteudo": "Agregados, entidades e relacionamentos",
      "cargaHoraria": 30
    },
    {
      "titulo": "API REST com Spring Boot",
      "conteudo": "CRUD com Spring Data MongoDB",
      "cargaHoraria": 30
    },
    {
      "titulo": "Agregações e índices",
      "conteudo": "Aggregation pipeline e índices",
      "cargaHoraria": 10
    }
  ]
}
```

### Atualizar id inexistente (esperado 404)

```http
PUT /cursos/000000000000000000000000
Content-Type: application/json

{"nome":"Algoritmos e Lógica de Programação","cargaHoraria":60,"valor":980.50,"modulos":[{"titulo":"Fundamentos","conteudo":"Variáveis, tipos e operadores","cargaHoraria":30},{"titulo":"Estruturas de controle","conteudo":"Condicionais e repetições","cargaHoraria":30}]}
```

**Resposta:** HTTP 404

## 4. Delete (DELETE)

### Excluir curso 2

```http
DELETE /cursos/6ad6a6087893ffec9230b97c
```

**Resposta:** HTTP 204

### Buscar curso 2 após exclusão (esperado 404)

```http
GET /cursos/6ad6a6087893ffec9230b97c
```

**Resposta:** HTTP 404

### Listar cursos ao final

```http
GET /cursos
```

**Resposta:** HTTP 200

```json
[
  {
    "id": "6ad6a6077893ffec9230b97b",
    "nome": "Banco de Dados Não Relacional",
    "cargaHoraria": 90,
    "valor": 1750.0,
    "modulos": [
      {
        "titulo": "Introdução ao MongoDB",
        "conteudo": "Conceitos de NoSQL, documentos e coleções",
        "cargaHoraria": 20
      },
      {
        "titulo": "Modelagem NoAM",
        "conteudo": "Agregados, entidades e relacionamentos",
        "cargaHoraria": 30
      },
      {
        "titulo": "API REST com Spring Boot",
        "conteudo": "CRUD com Spring Data MongoDB",
        "cargaHoraria": 30
      },
      {
        "titulo": "Agregações e índices",
        "conteudo": "Aggregation pipeline e índices",
        "cargaHoraria": 10
      }
    ]
  }
]
```

