package co.com.domicilio.corrientazo.factories;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import co.com.domicilio.corrientazo.config.AbstractConfigTest;
import co.com.domicilio.corrientazo.enums.InstructionSet;
import co.com.domicilio.corrientazo.models.Dron;
import co.com.domicilio.corrientazo.models.DronOrdersConfiguration;
import co.com.domicilio.corrientazo.processors.InstructionProcessor;

@ExtendWith(MockitoExtension.class)
public class DronFactoryTest extends AbstractConfigTest {
	
	private DronFactory droFactory;
	@Mock
	private InstructionProcessor ordersProcessor;
	
	@BeforeEach
	public void init() {
		droFactory = new DronFactory(ordersProcessor);
	}
	
	@Test
	public void createOneDron() {
		
		DronOrdersConfiguration dronOrdersConfiguration = new DronOrdersConfiguration("01",
				List.of(new InstructionSet[1], new InstructionSet[1]));
		
		Dron dron = droFactory.createDron(dronOrdersConfiguration);
		
		assertNotNull(dron);
	}

}
