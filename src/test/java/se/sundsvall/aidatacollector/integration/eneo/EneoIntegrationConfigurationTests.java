package se.sundsvall.aidatacollector.integration.eneo;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static se.sundsvall.aidatacollector.integration.eneo.EneoIntegrationConfiguration.INTEGRATION_NAME;

class EneoIntegrationConfigurationTests {

	@Test
	void integrationName() {
		assertThat(INTEGRATION_NAME).isEqualTo("eneo");
	}
}
