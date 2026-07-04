package br.com.proposal.e2e.steps;

import br.com.proposal.e2e.pages.LoginPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Step definitions que traduzem os passos em português de {@code login.feature}
 * para chamadas ao {@link LoginPage}. Os textos dos anotações (@Given/@When/@Then)
 * são casados pelo Cucumber com o conteúdo do .feature, independente da palavra-chave
 * Gherkin usada no arquivo — por isso todos os steps aqui usam anotações em inglês.
 */
public class LoginSteps {

	@Autowired
	private LoginPage loginPage;

	@Given("que o usuário está na tela de login da aplicação de propostas")
	public void queOUsuarioEstaNaTelaDeLoginDaAplicacaoDePropostas() {
		loginPage.acessar();
	}

	@When("ele informa o usuário {string} e a senha {string}")
	public void eleInformaOUsuarioEASenha(String usuario, String senha) {
		loginPage.preencherUsuario(usuario);
		loginPage.preencherSenha(senha);
	}

	@When("confirma o login")
	public void confirmaOLogin() {
		loginPage.confirmarLogin();
	}

	@Then("ele deve ser redirecionado para o dashboard de propostas")
	public void deveSerRedirecionadoParaODashboardDePropostas() {
		assertThat(loginPage.estaNoDashboard()).isTrue();
	}
}
