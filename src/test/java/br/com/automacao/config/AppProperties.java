package br.com.automacao.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

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
