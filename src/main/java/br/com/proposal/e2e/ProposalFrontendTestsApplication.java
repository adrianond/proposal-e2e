package br.com.proposal.e2e;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Ponto de entrada Spring Boot do projeto. Não é usada para subir uma aplicação
 * de verdade: existe apenas para fornecer o {@code ApplicationContext} que o
 * cucumber-spring precisa para injetar dependências (@Autowired) nos hooks,
 * page objects e step definitions dos testes E2E.
 */
@SpringBootApplication
public class ProposalFrontendTestsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProposalFrontendTestsApplication.class, args);
	}

}
