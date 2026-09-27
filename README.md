# 🏊 Smart Pool Management API

[![CI](https://github.com/guirodriguesxz/gestao-piscinas-api/actions/workflows/ci.yml/badge.svg)](https://github.com/guirodriguesxz/gestao-piscinas-api/actions/workflows/ci.yml)
![Java](https://img.shields.io/badge/Java-21-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4-6DB33F?logo=springboot&logoColor=white)

> Transformando um negócio tradicional de manutenção de piscinas em uma operação guiada por dados e preparada para IA.

## O problema

Empresas de manutenção de piscinas costumam operar de forma reativa e no papel: o técnico visita,
anota pH e cloro e vai embora. O cliente só liga quando a água já ficou verde.

Esta API é a **Fase 1** da modernização. Ela digitaliza clientes, piscinas e o **histórico químico
da água** de cada visita, e já classifica a condição da água no momento do registro.
Na **Fase 2**, um serviço em Python/ML vai consumir essa série histórica, cruzar com a previsão do
tempo e **prever quando a água vai desequilibrar**, sugerindo a visita antes da reclamação.

## Stack

- **Java 21 + Spring Boot 4** (Web MVC, Data JPA, Validation)
- **PostgreSQL** + **Flyway** (schema versionado; Hibernate só valida)
- **springdoc-openapi** — Swagger UI
- **JUnit 5 + MockMvc** — testes de integração e testes parametrizados da regra de negócio
- **Docker / Docker Compose** e **GitHub Actions** (build + testes a cada push/PR)

## Modelo de domínio

```
Cliente 1 ──── N Piscina 1 ──── N VisitaTecnica
(condomínio,     (volume,          (data, pH, cloro,
 residência)      revestimento,     produtos, status,
                  externa?)         condicaoAgua)
```

### Condição da água

Cada visita volta com `condicaoAgua` calculada a partir das faixas usuais de tratamento:

| Condição | Regra |
|---|---|
| `IDEAL` | pH entre 7,2 e 7,8 **e** cloro entre 1 e 3 ppm |
| `CRITICA` | pH < 6,8 ou > 8,2, **ou** cloro < 0,5 ou > 5 ppm |
| `ATENCAO` | qualquer outro caso |

A regra fica em [`CondicaoAgua`](src/main/java/com/empresa/gestao_piscinas/model/CondicaoAgua.java).
Esses rótulos também servem de *target* para o futuro modelo preditivo.

## Como rodar

```bash
git clone https://github.com/guirodriguesxz/gestao-piscinas-api.git
cd gestao-piscinas-api

# Opção 1: Postgres + API em containers
docker compose --profile app up --build

# Opção 2: só o Postgres em Docker e a API pela IDE / Maven
docker compose up -d
./mvnw spring-boot:run
```

Swagger UI: http://localhost:8080/swagger-ui.html

## Endpoints

| Método | Rota | Descrição |
|---|---|---|
| `POST` | `/api/clientes` | Cadastra cliente |
| `GET` | `/api/clientes` · `/api/clientes/{id}` | Lista / busca cliente |
| `GET` | `/api/clientes/{id}/piscinas` | Piscinas do cliente |
| `POST` | `/api/piscinas` | Cadastra piscina vinculada a um cliente |
| `GET` | `/api/piscinas` · `/api/piscinas/{id}` | Lista / busca piscina |
| `GET` | `/api/piscinas/{id}/visitas` | **Série histórica** da piscina, mais recente primeiro |
| `POST` | `/api/visitas` | Registra visita com medições |
| `GET` | `/api/visitas` | Lista todas as visitas |

Erros seguem o padrão **RFC 9457** (`application/problem+json`): 404 para recurso inexistente e
400 com os campos inválidos.

### Exemplo de fluxo

```bash
# 1. Cliente
curl -X POST localhost:8080/api/clientes -H "Content-Type: application/json" \
  -d '{"nome": "Condomínio Sol", "telefone": "11999999999", "endereco": "Rua A, 100"}'

# 2. Piscina do cliente 1
curl -X POST localhost:8080/api/piscinas -H "Content-Type: application/json" \
  -d '{"clienteId": 1, "volumeLitros": 30000, "tipoRevestimento": "Vinil", "ambienteExterno": true}'

# 3. Visita técnica
curl -X POST localhost:8080/api/visitas -H "Content-Type: application/json" \
  -d '{"piscinaId": 1, "dataVisita": "2026-09-15", "nivelPh": 8.5, "nivelCloro": 0.3,
       "produtosUsados": "200g de cloro granulado", "status": "CONCLUIDA"}'

# 4. Histórico
curl localhost:8080/api/piscinas/1/visitas
```

```json
[
  {
    "id": 1,
    "piscinaId": 1,
    "dataVisita": "2026-09-15",
    "nivelCloro": 0.3,
    "nivelPh": 8.5,
    "produtosUsados": "200g de cloro granulado",
    "status": "CONCLUIDA",
    "condicaoAgua": "CRITICA"
  }
]
```

## Testes

```bash
./mvnw test
```

- **`CondicaoAguaTest`**: 10 casos parametrizados, incluindo os limites das faixas
- **`ApiIntegrationTest`**: sobe o contexto com H2 em modo PostgreSQL e aplica as mesmas migrations do Flyway.
  Cobre cadastro, 404, validação, piscinas por cliente, histórico ordenado com condição da água e Swagger.

## Roadmap

- [x] Fase 1: cadastro, histórico químico e classificação da água
- [ ] Autenticação (técnico × administrador)
- [ ] Integração com API de previsão do tempo
- [ ] Fase 2: serviço de ML que prevê desequilíbrio e sugere visitas
