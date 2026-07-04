package br.com.automacao.pages;

import br.com.automacao.config.AppProperties;
import br.com.automacao.support.PlaywrightContext;
import com.microsoft.playwright.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LoginPage {

	@Autowired
	private PlaywrightContext playwrightContext;

	@Autowired
	private AppProperties appProperties;

	private Page page() {
		return playwrightContext.getPage();
	}

	public void acessar() {
		page().navigate(appProperties.getBaseUrl());
	}

	public void preencherUsuario(String usuario) {
		page().fill("#username", usuario);
	}

	public void preencherSenha(String senha) {
		page().fill("#password", senha);
	}

	public void confirmarLogin() {
		page().click("button[type=submit]");
	}

	public boolean estaNoDashboard() {
		page().waitForURL("**/dashboard/**");
		return page().url().contains("/dashboard");
	}
}
