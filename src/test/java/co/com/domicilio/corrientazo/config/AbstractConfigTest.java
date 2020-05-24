package co.com.domicilio.corrientazo.config;

import co.com.domicilio.corrientazo.factories.NumbericPropertiesFactory;

public abstract class AbstractConfigTest {
	
	public AbstractConfigTest() {
		bootStrapConfiguration();
	}
	
	public void bootStrapConfiguration() {
		NumbericPropertiesFactory.init();
	}

}
