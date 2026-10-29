# API de Indicadores do Banco Central

[![CI](https://github.com/KAUAN-KAWASAKI/api-indicadores-bcb/actions/workflows/ci.yml/badge.svg)](https://github.com/KAUAN-KAWASAKI/api-indicadores-bcb/actions/workflows/ci.yml)

API REST em Java que expõe os indicadores econômicos do Banco Central (Selic, IPCA e dólar PTAX) para consulta por série e por período.

Os dados vêm do [pipeline-indicadores-bcb](https://github.com/KAUAN-KAWASAKI/pipeline-indicadores-bcb), que coleta as séries da API de dados abertos do Banco Central (SGS) todo dia e grava num banco MySQL. Esta API só **lê** esse banco.

## Stack

- Java 25
- Spring Boot 4.1 (Spring Web MVC e Spring Data JPA)
- MySQL
- springdoc-openapi (Swagger UI)
- JUnit 5, Mockito e `@WebMvcTest` nos testes
- GitHub Actions para o CI

## Endpoints

| Método | Rota | Descrição |
|---|---|---|
| GET | `/indicadores` | Lista as séries disponíveis |
| GET | `/indicadores/{serie}` | Valores da série em ordem de data. Aceita `?inicio=` e `?fim=` (formato `AAAA-MM-DD`) |
| GET | `/indicadores/{serie}/ultimo` | Valor mais recente da série |

Os filtros `inicio` e `fim` devem ser informados juntos, ou nenhum dos dois.

### Exemplos

`GET /indicadores`

```json
["dolar", "ipca", "selic"]
```

`GET /indicadores/selic/ultimo`

```json
{
  "serie": "selic",
  "data": "2026-10-28",
  "valor": 15.0000
}
```

`GET /indicadores/dolar?inicio=2026-10-01&fim=2026-10-03`

```json
[
  { "serie": "dolar", "data": "2026-10-01", "valor": 5.4000 },
  { "serie": "dolar", "data": "2026-10-02", "valor": 5.4100 }
]
```

Os valores acima são ilustrativos.

### Erros

Os erros seguem o formato `ProblemDetail` (RFC 9457):

| Status | Quando |
|---|---|
| 404 | A série não existe |
| 400 | Só uma das datas foi informada, ou `inicio` é depois de `fim` |

```json
{
  "title": "Série não encontrada",
  "status": 404,
  "detail": "..."
}
```

## Documentação interativa

Com a API rodando, abra o Swagger UI em http://localhost:8080/swagger-ui.html para testar os endpoints pelo navegador.

## Como rodar

Requer Java 25 e um MySQL com a tabela `indicadores` já carregada pelo [pipeline](https://github.com/KAUAN-KAWASAKI/pipeline-indicadores-bcb).

A conexão é configurada por variáveis de ambiente:

| Variável | Padrão | Descrição |
|---|---|---|
| `DB_URL` | `jdbc:mysql://localhost:3307/indicadores_bcb?serverTimezone=America/Sao_Paulo` | URL JDBC do banco |
| `DB_USERNAME` | `root` | usuário |
| `DB_PASSWORD` | vazio | senha |

```powershell
$env:DB_PASSWORD = "sua_senha"
.\mvnw.cmd spring-boot:run
```

```bash
DB_PASSWORD=sua_senha ./mvnw spring-boot:run   # Linux/macOS
```

A API sobe em http://localhost:8080.

## Testes

```bash
./mvnw test
```

Os testes do service (Mockito) e do controller (`@WebMvcTest`) não usam banco, por isso rodam em qualquer máquina e no CI sem nenhum secret. O workflow `.github/workflows/ci.yml` roda a suíte a cada push na `main` e em todo pull request.

## Estrutura

```
src/main/java/.../indicador/   entidade, repository, service e controller
src/main/java/.../erro/        tratamento de erros (ProblemDetail)
src/test/java/                 testes do service e do controller
```

## Licença

[MIT](LICENSE)
