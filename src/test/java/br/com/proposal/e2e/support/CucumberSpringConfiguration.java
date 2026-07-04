package br.com.proposal.e2e.support;

import br.com.proposal.e2e.ProposalFrontendTestsApplication;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

@CucumberContextConfiguration
@SpringBootTest(classes = ProposalFrontendTestsApplication.class)
public class CucumberSpringConfiguration {
}
