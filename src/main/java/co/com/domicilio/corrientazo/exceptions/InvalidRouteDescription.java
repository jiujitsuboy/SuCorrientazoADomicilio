package co.com.domicilio.corrientazo.exceptions;

/**
 * Represent a invalid route description
 * @author JoseAlejandro
 *
 */
public class InvalidRouteDescription extends Exception {

	private static final long serialVersionUID = 1L;
	
	public InvalidRouteDescription(String errorMessage) {
		super(errorMessage);
	}

}
