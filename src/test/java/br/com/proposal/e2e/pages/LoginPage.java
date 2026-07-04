package br.com.proposal.e2e.pages;

import br.com.proposal.e2e.config.AppProperties;
import br.com.proposal.e2e.support.PlaywrightContext;
import com.microsoft.playwright.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Page Object da tela de login do proposal-frontend. Encapsula os seletores
 * Playwright (#username, #password, button[type=submit]) e as ações possíveis
 * na tela, para que as step definitions não dependam de seletores diretamente.
 */
@Component
public class LoginPage {

	@Autowired
	private PlaywrightContext playwrightContext;

	@Autowired
	private AppProperties appProperties;

	// Sempre resolve a Page do cenário atual (via PlaywrightContext), nunca guarda referência própria.
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

	// Espera a navegação pós-login antes de checar a URL, evitando falso negativo por corrida.
	public boolean estaNoDashboard() {
		page().waitForURL("**/dashboard/**");
		return page().url().contains("/dashboard");
	}
}
