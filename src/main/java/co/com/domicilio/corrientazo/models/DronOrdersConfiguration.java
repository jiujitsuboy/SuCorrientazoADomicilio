package co.com.domicilio.corrientazo.models;

import java.util.List;

import co.com.domicilio.corrientazo.enums.InstructionSet;

/**
 * Depict the orders that a {@link Dron} should deliver
 * @author JoseAlejandro
 *
 */
public final class DronOrdersConfiguration {

	private String id;
	private List<InstructionSet[]> ordersInstructionDetails;

	public DronOrdersConfiguration(String id, List<InstructionSet[]> ordersInstructionDetails) {
		this.id = id;
		this.ordersInstructionDetails = ordersInstructionDetails;
	}

	public String getId() {
		return id;
	}

	public List<InstructionSet[]> getOrdersInstructionDetails() {
		return ordersInstructionDetails;
	}

}
