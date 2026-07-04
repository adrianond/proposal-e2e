package br.com.proposal.e2e.support;

import br.com.proposal.e2e.ProposalFrontendTestsApplication;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Ponte entre Cucumber e Spring: a anotação {@code @CucumberContextConfiguration}
 * sinaliza para o cucumber-spring qual classe define o contexto de teste a usar,
 * permitindo {@code @Autowired} nas step definitions e hooks. Precisa estar
 * dentro do pacote configurado como "glue" em {@link br.com.proposal.e2e.runner.RunCucumberTest},
 * senão o Cucumber não a encontra e falha ao iniciar o cenário.
 */
@CucumberContextConfiguration
@SpringBootTest(classes = ProposalFrontendTestsApplication.class)
public class CucumberSpringConfiguration {
}
