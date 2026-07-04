package br.com.proposal.e2e.hooks;

import br.com.proposal.e2e.support.PlaywrightContext;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Page;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Hooks globais do Cucumber responsáveis pelo ciclo de vida do navegador a
 * cada cenário: abre um {@code BrowserContext}/{@code Page} isolados antes de
 * cada cenário e fecha depois, garantindo que um cenário não vaze estado
 * (cookies, storage, abas) para o próximo. Em caso de falha, anexa um
 * screenshot da página ao relatório do Cucumber para facilitar o diagnóstico.
 */
public class PlaywrightHooks {

	@Autowired
	private Browser browser;

	@Autowired
	private PlaywrightContext playwrightContext;

	@Before
	public void abrirNavegador() {
		var browserContext = browser.newContext();
		playwrightContext.setBrowserContext(browserContext);
		playwrightContext.setPage(browserContext.newPage());
	}

	@After
	public void fecharNavegador(Scenario scenario) {
		Page page = playwrightContext.getPage();
		if (scenario.isFailed() && page != null) {
			scenario.attach(page.screenshot(), "image/png", scenario.getName());
		}
		if (playwrightContext.getBrowserContext() != null) {
			playwrightContext.getBrowserContext().close();
		}
	}
}
