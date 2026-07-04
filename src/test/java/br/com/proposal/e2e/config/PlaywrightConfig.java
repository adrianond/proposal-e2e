package br.com.proposal.e2e.config;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Playwright;
import jakarta.annotation.PreDestroy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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

	@PreDestroy
	public void encerrar() {
		if (playwright != null) {
			playwright.close();
		}
	}
}
