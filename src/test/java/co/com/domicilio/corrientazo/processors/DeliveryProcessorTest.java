package co.com.domicilio.corrientazo.processors;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import co.com.domicilio.corrientazo.config.AbstractConfigTest;
import co.com.domicilio.corrientazo.models.DronOrdersConfiguration;

public abstract class DeliveryProcessorTest extends AbstractConfigTest {

	protected abstract DeliveryProcessor createDeliveryProcessor();

	List<DronOrdersConfiguration> dronOrdersConfigurations;		
	
	@Test
	public void readASetOfDronOrdersConfiguration() {
		DeliveryProcessor deliveryProcessor = createDeliveryProcessor();
		List<DronOrdersConfiguration> dronOrdersConfig = deliveryProcessor.readDeliveries();

		assertNotNull(dronOrdersConfig);
		assertTrue(dronOrdersConfig.size() > 0);
	}
	
}
