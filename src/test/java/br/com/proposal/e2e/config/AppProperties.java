package br.com.proposal.e2e.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Configurações externalizáveis da suíte de testes, lidas do prefixo {@code app}
 * em {@code application.yml} (ou via {@code -Dapp.*}/variáveis de ambiente):
 * URL base da aplicação a testar, se o Chromium roda headless e o slow-mo (ms)
 * usado para desacelerar as ações do Playwright ao depurar visualmente.
 */
@Component
@ConfigurationProperties(prefix = "app")
public class AppProperties {

	private String baseUrl = "http://localhost:4200";
	private boolean headless = true;
	private double slowMoMillis = 0;

	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public boolean isHeadless() {
		return headless;
	}

	public void setHeadless(boolean headless) {
		this.headless = headless;
	}

	public double getSlowMoMillis() {
		return slowMoMillis;
	}

	public void setSlowMoMillis(double slowMoMillis) {
		this.slowMoMillis = slowMoMillis;
	}
}
