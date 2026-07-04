package br.com.automacao.support;

import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import io.cucumber.spring.ScenarioScope;
import org.springframework.stereotype.Component;

/**
 * Guarda o BrowserContext/Page do cenário Cucumber em execução, recriado a
 * cada cenário pelo cucumber-spring.
 */
@Component
@ScenarioScope
public class PlaywrightContext {

	private BrowserContext browserContext;
	private Page page;

	public BrowserContext getBrowserContext() {
		return browserContext;
	}

	public void setBrowserContext(BrowserContext browserContext) {
		this.browserContext = browserContext;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}
}
