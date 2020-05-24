package co.com.domicilio.corrientazo.exceptions;

/**
 * Represent an exception when a configuration value is missing
 * 
 * @author JoseAlejandro
 *
 */
public class InvalidConfigurationValue extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public InvalidConfigurationValue(String errorMessage) {
		super(errorMessage);
	}

}
