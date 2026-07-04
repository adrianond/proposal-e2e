package br.com.automacao.support;

import br.com.automacao.ProposalFrontendTestsApplication;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

@CucumberContextConfiguration
@SpringBootTest(classes = ProposalFrontendTestsApplication.class)
public class CucumberSpringConfiguration {
}
