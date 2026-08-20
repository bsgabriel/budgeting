# Budgeting

Aplicação de controle financeiro com suporte a lançamento de transações por texto e por áudio, usando IA para interpretar a fala do usuário e registrar o gasto/ganho automaticamente.

## O que o projeto faz

- Cadastra e lista transações financeiras (gastos e ganhos), associadas a uma categoria.
- Cria categorias automaticamente quando ainda não existem.
- Permite registrar uma transação por áudio: o usuário envia um arquivo de voz, a IA transcreve, interpreta o que foi dito ("gastei 50 reais na farmácia"), chama as ferramentas necessárias para salvar a transação, e responde de volta em áudio confirmando a ação.
- Expõe uma API REST simples (`/transactions`) para criar e consultar transações, além de um endpoint de IA (`/ai/transactions`) que recebe áudio e devolve áudio.

## Como executar a aplicação

### Pré-requisitos
- Java 25
- Maven
- Docker (para o banco de dados)
- Chaves de acesso para as APIs do Groq, Elevenlabs e Gemini

### Passos

1. Suba o banco de dados PostgreSQL:
   ```bash
   docker compose up -d
   ```
   Isso cria o banco `postgres`, o schema `budgeting` e os usuários `budgeting` (app) e `budgeting_liquibase` (migrações), conforme `init.sql`.

2. Defina as variáveis de ambiente necessárias:
   ```bash
   export GEMINI_API_KEY=chave
   export GROK_API_KEY=chave
   export ELEVENLABS_API_KEY=chave
   ```

3. Rode a aplicação:
   ```bash
   ./mvnw spring-boot:run
   ```
   O Liquibase aplica as migrações automaticamente na subida.

A aplicação sobe por padrão na porta `8080`.

## Melhorias implementadas

Originalmente, o projeto utilizava valores pré-determinados para categorizar as transações, além de não separar o que era gasto e o que era ganho.

Nesta versão:
- Foi criado uma coluna nova para indicar se a transação é um gasto ou ganho.
- Categorias criadas automaticamente sob demanda. Se a categoria já existe, ela é reaproveitada. Caso contrário, uma categoria será criada na hora.

## Tecnologias usadas

| Categoria            | Tecnologia                                                       |
|----------------------|------------------------------------------------------------------|
| Linguagem            | Java 25                                                          |
| Framework            | Spring Boot 4.1                                                  |
| Persistência         | Spring Data JPA + PostgreSQL                                     |
| Migração de banco    | Liquibase                                                        |
| IA / Chat            | Google Gemini                                                    |
| Transcrição de áudio | Groq (Whisper `whisper-large-v3`, via API compatível com OpenAI) |
| Texto para voz       | ElevenLabs                                                       |
| Infraestrutura local | Docker Compose                                                   |
| Utilitários          | Lombok, Bean Validation                                          |

## Como testar o fluxo principal

### Via texto (API REST)

Criar uma transação:
```bash
curl -X POST http://localhost:8080/transactions \
  -H "Content-Type: application/json" \
  -d '{
    "description": "Compra no mercado",
    "amount": 150.00,
    "type": "EXPENSE",
    "category": "Mercado"
  }'
```

Listar transações (opcionalmente filtrando por categoria):
```bash
curl http://localhost:8080/transactions
curl http://localhost:8080/transactions?category=Mercado
```

### Via áudio (fluxo com IA)

Envie um áudio dizendo algo como "gastei 50 reais na farmácia":
```bash
curl -X POST http://localhost:8080/ai/transactions \
  -H "Content-Type: multipart/form-data" \
  -F "file=@audio.mp3" \
  --output resposta.mp3
```

O fluxo esperado:
1. O áudio é transcrito usando Groq/Whisper.
2. O texto é enviado ao Gemini, que usa as ferramentas (`TransactionTool`, `CategoryTool`) para entender e salvar a transação no banco.
3. A resposta da IA é convertida em áudio (ElevenLabs) e devolvida como `resposta.mp3`.

Para conferir que a transação foi realmente salva, basta consultar `GET /transactions` depois.

## O que aprendi durante o desafio

- Spring AI: como configurar e usar o `ChatClient` junto com múltiplos modelos de IA (chat, transcrição de áudio e texto-para-voz), além de como expor métodos Java como tools.
- Subir o Docker automaticamente: uso do `spring-boot-docker-compose`, que sobe o banco definido no `compose.yml` junto com a aplicação.
- Script de inicialização do banco: como usar um `init.sql` montado no container do Postgres para criar usuários e schemas automaticamente na primeira subida, caso ainda não existam.


