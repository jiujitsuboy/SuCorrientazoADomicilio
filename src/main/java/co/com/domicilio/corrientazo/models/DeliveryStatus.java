package co.com.domicilio.corrientazo.models;

import java.util.List;

/**
 * Depict the result of a {@link Dron} delivery schedule
 * @author JoseAlejandro
 *
 */
public final class DeliveryStatus {
	private String id;
	private List<Location> location;
	
	public DeliveryStatus(String id, List<Location> location) {		
		this.id = id;
		this.location = location;
	}

	public String getId() {
		return id;
	}

	public List<Location> getLocation() {
		return location;
	}
	
	
}
