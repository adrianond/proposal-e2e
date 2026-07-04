# CLAUDE.md

Este arquivo orienta o Claude Code (claude.ai/code) ao trabalhar neste repositório.

## O que é este projeto

Projeto de automação de testes E2E (ponta a ponta) para o **proposal-frontend**
(app Angular de gestão de propostas de financiamento, em `c:\projetos\proposal-frontend`;
veja o `CLAUDE.md` de lá para entender rotas, componentes e domínio da aplicação).

Stack: **Java 17 + Spring Boot 3 + Maven (com Maven Wrapper) + Playwright + Cucumber/Gherkin (BDD)**.

## Pré-requisitos

- JDK 17 instalado (não é necessário Maven — o projeto usa Maven Wrapper, `mvnw`/`mvnw.cmd`).
- O `proposal-frontend` precisa estar **rodando localmente** antes de executar os testes:
  `cd c:\projetos\proposal-frontend && ng serve` (fica em `http://localhost:4200`). Este
  projeto de automação **não** sobe o Angular sozinho — isso é responsabilidade de quem roda
  os testes.
- Browsers do Playwright instalados (uma vez, ou sempre que a versão do Playwright mudar):
  ```
  mvnw exec:java@install-playwright-browsers
  ```

## Comandos

- `mvnw test` — roda toda a suíte de testes (Cucumber via `RunCucumberTest`). Por padrão roda
  em modo headless (sem janela do navegador), igual rodaria em CI.
- `mvnw test -Dapp.headless=false` — roda com a janela do Chromium visível, pra acompanhar as
  ações (equivalente a rodar Selenium sem `--headless`). Some com `-Dapp.slow-mo-millis=600`
  (ou outro valor em ms) pra desacelerar cada ação e dar tempo de ver o preenchimento/clique.
  No PowerShell, coloque cada `-D` entre aspas (`.\mvnw test "-Dapp.headless=false"`), senão o
  PowerShell quebra o argumento no `.` e o Maven acusa `Unknown lifecycle phase`.
- `mvnw test -Dcucumber.filter.tags="@login"` — roda só os cenários marcados com uma tag
  (ainda não há tags nos `.feature` atuais; adicione-as conforme a suíte crescer).
- `mvnw test -Dcucumber.filter.name="Login com credenciais válidas"` — roda só o(s) cenário(s)
  cujo nome bate com o regex informado (útil pra rodar um único cenário sem precisar de tag).
- Relatório HTML gerado em `target/cucumber-report.html` após cada execução.
- `mvnw exec:java@install-playwright-browsers` — (re)instala os browsers do Playwright
  (Chromium/Firefox/WebKit) em `%LOCALAPPDATA%\ms-playwright`.

## Arquitetura

Tudo relevante fica em `src/test/java/br/com/automacao/` (é um projeto só de testes; não há
código de produção em `src/main`, além da classe `ProposalFrontendTestsApplication` que existe
apenas para dar um contexto Spring ao `cucumber-spring`):

- `runner/RunCucumberTest.java` — suíte JUnit 5 (`@Suite` + `cucumber-junit-platform-engine`)
  que descobre os `.feature` em `src/test/resources/features` e aponta o glue para
  `br.com.automacao`. É o que o Maven Surefire executa.
- `support/CucumberSpringConfiguration.java` — liga o Cucumber ao Spring
  (`@CucumberContextConfiguration` + `@SpringBootTest`), permitindo `@Autowired` nas classes
  de step definitions e hooks.
- `support/PlaywrightContext.java` — bean `@ScenarioScope` (cucumber-spring): guarda o
  `BrowserContext`/`Page` do Playwright do **cenário atual**, recriado a cada cenário.
- `config/PlaywrightConfig.java` — beans singletons `Playwright`/`Browser` (processo do
  navegador é criado uma vez por execução da suíte). O browser é fixo em **Chromium**
  (`playwright.chromium().launch(...)`) — não há bean/propriedade pra trocar de engine hoje;
  isso exigiria alterar essa classe diretamente.
- `config/AppProperties.java` — `@ConfigurationProperties(prefix = "app")`: `app.base-url`
  (default `http://localhost:4200`) e `app.headless` (default `true`), configuráveis em
  `src/test/resources/application.yml` ou via `-Dapp.headless=false` / variável de ambiente
  (`APP_HEADLESS=false`) para rodar com o navegador visível ao depurar.
- `hooks/PlaywrightHooks.java` — `@Before`/`@After` do Cucumber: abre um `BrowserContext` +
  `Page` novos antes de cada cenário e fecha depois, garantindo isolamento entre cenários.
- `pages/` — **Page Objects**: uma classe por tela/fluxo da aplicação, encapsulando os
  seletores Playwright (ex.: `LoginPage`). Steps não devem chamar `Page`/seletores diretamente,
  sempre através de um Page Object.
- `steps/` — step definitions em Java (`@Given`/`@When`/`@Then` de `io.cucumber.java.en`),
  ligando o texto dos `.feature` (escritos em português, com `# language: pt` no topo) às
  chamadas nos Page Objects. Cucumber casa os steps pelo texto, não pela palavra-chave do
  Gherkin — por isso é normal (e é o padrão deste projeto) usar `@Given/@When/@Then` em inglês
  mesmo com `.feature` em português. Asserções usam **AssertJ** (`assertThat(...)`, trazido
  por `spring-boot-starter-test`), não JUnit `Assertions`.
- `features/*.feature` (`src/test/resources`) — cenários em Gherkin/português.

## Fluxo de trabalho para adicionar um novo teste

Este projeto nasceu seguindo um processo de 3 passos que deve ser repetido para cada novo
fluxo a automatizar (fila, criação/edição de proposta, etc.):

1. **Analisar** o componente/fluxo correspondente no `proposal-frontend` — rotas, seletores do
   HTML, regras de navegação/validação. Usar o `CLAUDE.md` de lá como ponto de partida
   (arquitetura, módulos, serviços).
2. **Estender o projeto de automação**: criar o `.feature` novo, o Page Object da tela (se
   ainda não existir) e as step definitions, reaproveitando `PlaywrightContext`/`AppProperties`
   já existentes.
3. **Implementar e validar**: rodar `mvnw test` com o `proposal-frontend` de pé
   (`ng serve`) e confirmar que o cenário passa contra a aplicação real.

## Primeiro teste implementado: login

`features/login.feature` cobre o login com um usuário de teste (`PEDRO SANTOS` / `123`) e
valida que a aplicação redireciona para `/dashboard` após o envio do formulário — espelha
`LoginComponent`/`LoginService` do `proposal-frontend` (POST `/api/login`, navegação para
`/dashboard` em caso de sucesso). Veja `src/app/login/login.component.html` no
`proposal-frontend` para a origem dos seletores (`#username`, `#password`,
`button[type=submit]`) usados em `LoginPage`.
