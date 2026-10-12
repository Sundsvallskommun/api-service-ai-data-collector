package se.sundsvall.aidatacollector.datasource.confluence;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import se.sundsvall.aidatacollector.Application;
import se.sundsvall.aidatacollector.datasource.confluence.integration.db.DbIntegration;
import se.sundsvall.aidatacollector.integration.eneo.EneoIntegration;
import se.sundsvall.dept44.test.AbstractAppTest;
import se.sundsvall.dept44.test.annotation.wiremock.WireMockAppTestSuite;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@WireMockAppTestSuite(files = "classpath:/ConfluenceImportIT/", classes = Application.class)
class ConfluenceImportIT extends AbstractAppTest {

	@Autowired
	private ConfluenceDataSource confluenceDataSource;

	@MockitoSpyBean
	private EneoIntegration eneoIntegrationMock;

	@Autowired
	private DbIntegration dbIntegration;

	@Test
	void test1_fetchFromConfluenceAndInsertInEneo() {
		// Setup Wiremock
		setupCall();

		// Get a Confluence worker for the current municipality id
		final var worker = confluenceDataSource.getWorker("1984");

		// Run
		worker.run();

		// There should be five pages stored locally
		assertThat(dbIntegration.getAllPages("1984")).hasSize(5);
		// Five pages should also have been added to Eneo
		verify(eneoIntegrationMock, times(5)).addInfoBlob(any(), any(), any(), any(), any());
	}
}
