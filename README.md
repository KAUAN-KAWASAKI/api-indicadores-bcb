# API de Indicadores do Banco Central

[![CI](https://github.com/KAUAN-KAWASAKI/api-indicadores-bcb/actions/workflows/ci.yml/badge.svg)](https://github.com/KAUAN-KAWASAKI/api-indicadores-bcb/actions/workflows/ci.yml)

API REST em Java e Spring Boot que expõe os indicadores econômicos do Banco Central (Selic, IPCA e dólar). Os dados são coletados todos os dias pelo [pipeline-indicadores-bcb](https://github.com/KAUAN-KAWASAKI/pipeline-indicadores-bcb) e gravados em MySQL. Esta API apenas lê essa tabela.

```
API do Banco Central → pipeline (Python) → MySQL → esta API (Java) → site / clientes
```

## Endpoints

| Método | Rota | Descrição |
|---|---|---|
| GET | `/indicadores` | Lista as séries disponíveis |
| GET | `/indicadores/{serie}` | Todos os valores de uma série, em ordem de data |
| GET | `/indicadores/{serie}?inicio=AAAA-MM-DD&fim=AAAA-MM-DD` | Valores de uma série dentro de um período |
| GET | `/indicadores/{serie}/ultimo` | Valor mais recente de uma série |

Séries: `selic`, `ipca` e `dolar`.

Exemplo de `GET /indicadores/selic/ultimo`:

```json
{
  "serie": "selic",
  "data": "2026-10-28",
  "valor": 15.0000
}
```

Erros seguem o formato padrão [Problem Details (RFC 9457)](https://www.rfc-editor.org/rfc/rfc9457):

| Situação | Status |
|---|---|
| Série inexistente | 404 Not Found |
| Só uma das datas informada, ou início depois do fim | 400 Bad Request |

A documentação interativa (Swagger) fica em `http://localhost:8080/swagger-ui.html` com a aplicação rodando.

## Como rodar

Requer Java 25 e um MySQL com a tabela `indicadores` criada e preenchida pelo pipeline.

Configure as variáveis de ambiente:

| Variável | Padrão | Descrição |
|---|---|---|
| `DB_URL` | `jdbc:mysql://localhost:3307/indicadores_bcb` | Endereço JDBC do banco |
| `DB_USERNAME` | `root` | Usuário do banco |
| `DB_PASSWORD` | (vazio) | Senha do banco |

```bash
./mvnw spring-boot:run        # Linux/macOS
.\mvnw.cmd spring-boot:run    # Windows
```

## Testes

```bash
./mvnw test
```

Os testes não precisam de banco: o service é testado com Mockito e o controller com `@WebMvcTest`. O GitHub Actions roda a suíte a cada push.

## Estrutura

```
src/main/java/.../indicador/   entidade, repository, service, controller e DTO
src/main/java/.../erro/        tratamento de erros (ProblemDetail)
src/test/java/.../indicador/   testes do service e do controller
```

## Tecnologias

Java 25 · Spring Boot 4 · Spring Data JPA · MySQL · JUnit 5 · Mockito · springdoc-openapi · GitHub Actions

## Licença

[MIT](LICENSE)
