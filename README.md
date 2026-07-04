# proposal-frontend-tests

Automação de testes E2E (ponta a ponta) para o [proposal-frontend](../projetos/proposal-frontend)
(app Angular de gestão de propostas de financiamento), usando **Java + Spring Boot + Maven +
Playwright + Cucumber/Gherkin (BDD)**.

## Stack

- Java 17
- Spring Boot 3.5.x (só para dar contexto de injeção de dependência aos testes via `cucumber-spring`)
- Maven, via Maven Wrapper (`mvnw` / `mvnw.cmd`) — não precisa ter o Maven instalado
- Cucumber 7 (JUnit 5 Platform Engine) — cenários em Gherkin, escritos em português
- Playwright (Java) — automação do navegador (Chromium por padrão)

## Pré-requisitos

- JDK 17 instalado (ex.: [Eclipse Temurin](https://adoptium.net/)).
- O [proposal-frontend](../projetos/proposal-frontend) rodando localmente antes de executar os
  testes:
  ```
  cd C:\projetos\proposal-frontend
  ng serve
  ```
  A aplicação precisa estar acessível em `http://localhost:4200`. Este projeto **não** sobe o
  Angular sozinho.
- Browsers do Playwright instalados (só na primeira vez, ou quando a versão do Playwright mudar
  no `pom.xml`):
  ```
  mvnw exec:java@install-playwright-browsers
  ```

## Como rodar os testes

Com o `proposal-frontend` rodando, em outro terminal:

No PowerShell (terminal padrão do VS Code no Windows), lembre do `.\` antes do comando:
`.\mvnw test`.

Roda em modo **headless** por padrão (sem janela do navegador) — igual rodaria em CI.

### Rodar com o navegador visível

Para acompanhar visualmente as ações no navegador (como no Selenium sem `--headless`):

```
.\mvnw test "-Dapp.headless=false" "-Dapp.slow-mo-millis=600"
```

- `-Dapp.headless=false` abre a janela real do Chromium.
- `-Dapp.slow-mo-millis=600` desacelera cada ação (em milissegundos) para dar tempo de ver o
  preenchimento dos campos e cliques.
- **No PowerShell, as aspas em volta de cada `-D...` são obrigatórias** (`.\mvnw test "-Dapp.headless=false" "-Dapp.slow-mo-millis=600"`).
  Sem aspas, o PowerShell repassa o argumento quebrado para o `mvnw.cmd` (ele separa no `.`) e o
  Maven acusa erro de `Unknown lifecycle phase`.

### Rodar pelo IntelliJ

1. Abra a pasta `C:\automacao` como projeto Maven.
2. Configure o SDK do projeto para o JDK 17 instalado.
3. (Opcional) instale o plugin **Cucumber for Java** para rodar cenários direto do `.feature`.
4. Rode a classe `src/test/java/br/com/automacao/runner/RunCucumberTest.java` (clique no ▶️).
5. Para modo headed, edite a Run Configuration e adicione em **VM options**:
   `-Dapp.headless=false -Dapp.slow-mo-millis=600`
   (aqui não precisa de aspas — o problema de quebra de argumento é só no PowerShell, não no
   campo VM options do IntelliJ)

## Relatórios

Após cada execução, o relatório HTML fica em `target/cucumber-report.html`.

## Estrutura do projeto

```
src/test/java/br/com/automacao/
  runner/    -> RunCucumberTest (suíte JUnit 5 que roda o Cucumber)
  support/   -> integração Cucumber + Spring, contexto do Playwright por cenário
  config/    -> beans do Playwright e propriedades configuráveis (app.base-url, app.headless, ...)
  hooks/     -> abre/fecha navegador e página a cada cenário
  pages/     -> Page Objects (um por tela/fluxo da aplicação)
  steps/     -> step definitions que ligam os .feature aos Page Objects
src/test/resources/
  features/  -> cenários em Gherkin (português)
  application.yml            -> configuração (base-url, headless, slow-mo)
  junit-platform.properties  -> configuração do JUnit Platform/Cucumber
```

## Configuração

| Propriedade | Padrão | Descrição |
|---|---|---|
| `app.base-url` | `http://localhost:4200` | URL do `proposal-frontend` |
| `app.headless` | `true` | Se `false`, abre o navegador visível |
| `app.slow-mo-millis` | `0` | Atraso (ms) entre ações do Playwright, útil com `headless=false` |

Podem ser sobrescritas em `src/test/resources/application.yml` ou via `-D<propriedade>=<valor>`
na linha de comando.

## Mais detalhes

Para o fluxo de trabalho de como adicionar novos testes e o racional das decisões de
arquitetura, veja o [CLAUDE.md](CLAUDE.md).
