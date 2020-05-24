package co.com.domicilio.corrientazo.processors;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import co.com.domicilio.corrientazo.enums.InstructionSet;
import co.com.domicilio.corrientazo.models.DronOrdersConfiguration;

@ExtendWith(MockitoExtension.class)
public class TextFileDeliveryProcessorTest extends DeliveryProcessorTest {

	@Mock
	private TextFileDeliveryProcessor textFileDeliveryProcessor;
	private List<DronOrdersConfiguration> dronOrdersConfigurations;

	@BeforeEach
	public void init() {

		DronOrdersConfiguration dronOrdersConfiguration = new DronOrdersConfiguration("01",
				List.of(new InstructionSet[1], new InstructionSet[1]));
		dronOrdersConfigurations = new ArrayList<DronOrdersConfiguration>();
		dronOrdersConfigurations.add(dronOrdersConfiguration);
		when(textFileDeliveryProcessor.readDeliveries()).thenReturn(dronOrdersConfigurations);
	}

	@Override
	protected DeliveryProcessor createDeliveryProcessor() {
		return textFileDeliveryProcessor;
	}

	@Test
	public void readMoreThan20DeliveriesRoots() {
		when(textFileDeliveryProcessor.isMaxNumberRouteExceed()).thenReturn(true);
		textFileDeliveryProcessor.readDeliveries();
		assertTrue(textFileDeliveryProcessor.isMaxNumberRouteExceed());
	}

	@Test
	public void readMoreThan3InstructionsSetPerDelivery() {
		when(textFileDeliveryProcessor.isMaxNumberDeliveryExceed()).thenReturn(true);
		textFileDeliveryProcessor.readDeliveries();
		assertTrue(textFileDeliveryProcessor.isMaxNumberDeliveryExceed());
	}
}