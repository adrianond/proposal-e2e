package br.com.proposal.e2e.runner;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;
import static io.cucumber.junit.platform.engine.Constants.PLUGIN_PROPERTY_NAME;

/**
 * Suíte JUnit 5 que dispara a execução do Cucumber: descobre os arquivos
 * .feature em {@code src/test/resources/features} e aponta o "glue" (pacote
 * onde procurar hooks/steps/configuração do Spring) para {@code br.com.proposal.e2e}.
 * É esta classe que o Maven Surefire executa como teste; o glue precisa
 * cobrir o pacote de {@code CucumberSpringConfiguration}, senão o Cucumber
 * falha ao subir o contexto Spring para os cenários.
 */
@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "br.com.proposal.e2e")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "pretty, html:target/cucumber-report.html")
public class RunCucumberTest {
}
