package co.com.domicilio.corrientazo.factories;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import co.com.domicilio.corrientazo.exceptions.InvalidConfigurationValue;

public class NumbericPropertiesFactory {

	private final static String PROPERTIES_PATH = "application.properties";
	private static final Logger LOGGER = Logger.getLogger(NumbericPropertiesFactory.class.getName());
	private static Map<String, Integer> props;

	public static void init() {
		if (props == null) {
			String propertiesPath = Thread.currentThread().getContextClassLoader().getResource(PROPERTIES_PATH)
					.getPath();
			try (InputStream input = new FileInputStream(propertiesPath)) {

				Properties prop = new Properties();

				prop.load(input);

				props = prop.entrySet().stream().collect(Collectors.toMap(entry -> (String) entry.getKey(),
						entry -> Integer.parseInt((String) entry.getValue())));

			} catch (IOException | ClassCastException ex) {
				LOGGER.severe(ex.getMessage());
			}
		}
	}

	public static Integer getPropertieValue(String propertyKey) throws InvalidConfigurationValue {
		if (!props.containsKey(propertyKey)) {
			throw new InvalidConfigurationValue("No such key found");
		}
		return props.get(propertyKey);
	}

}
