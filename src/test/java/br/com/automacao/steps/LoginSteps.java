package br.com.automacao.steps;

import br.com.automacao.pages.LoginPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class LoginSteps {

	@Autowired
	private LoginPage loginPage;

	@Given("que o usuário está na tela de login da aplicação de propostas")
	public void que_o_usuario_esta_na_tela_de_login() {
		loginPage.acessar();
	}

	@When("ele informa o usuário {string} e a senha {string}")
	public void ele_informa_o_usuario_e_a_senha(String usuario, String senha) {
		loginPage.preencherUsuario(usuario);
		loginPage.preencherSenha(senha);
	}

	@When("confirma o login")
	public void confirma_o_login() {
		loginPage.confirmarLogin();
	}

	@Then("ele deve ser redirecionado para o dashboard de propostas")
	public void deve_ser_redirecionado_para_o_dashboard() {
		assertThat(loginPage.estaNoDashboard()).isTrue();
	}
}
