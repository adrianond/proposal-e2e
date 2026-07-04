package br.com.proposal.e2e.config;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Playwright;
import jakarta.annotation.PreDestroy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Fornece os beans singletons do Playwright compartilhados por toda a suíte:
 * o processo {@link Playwright} e o {@link Browser} (Chromium), ambos criados
 * uma única vez na execução (não a cada cenário — isso é responsabilidade de
 * {@link br.com.proposal.e2e.hooks.PlaywrightHooks}, que abre um BrowserContext/
 * Page novo por cenário a partir deste Browser).
 */
@Configuration
public class PlaywrightConfig {

	private Playwright playwright;

	@Bean
	public Playwright playwright() {
		playwright = Playwright.create();
		return playwright;
	}

	@Bean
	public Browser browser(Playwright playwright, AppProperties appProperties) {
		return playwright.chromium().launch(new BrowserType.LaunchOptions()
				.setHeadless(appProperties.isHeadless())
				.setSlowMo(appProperties.getSlowMoMillis()));
	}

	// Encerra o processo do navegador ao final da suíte, quando o contexto Spring é fechado.
	@PreDestroy
	public void encerrar() {
		if (playwright != null) {
			playwright.close();
		}
	}
}
