package co.com.domicilio.corrientazo.models;

import java.util.List;

import co.com.domicilio.corrientazo.enums.InstructionSet;
import co.com.domicilio.corrientazo.processors.InstructionProcessor;

/**
 * Represent a delivery entity which is capable of deliver multiple orders,
 * following a {@link InstructionSet}
 * 
 * @author JoseAlejandro
 *
 */

public class Dron {
	private String id;
	private List<InstructionSet[]> ordersInstructionDetails;
	private InstructionProcessor ordersProcessor;

	public Dron(DronOrdersConfiguration ordersConfiguration, InstructionProcessor ordersProcessor) {
		this.id = ordersConfiguration.getId();
		this.ordersInstructionDetails = ordersConfiguration.getOrdersInstructionDetails();
		this.ordersProcessor = ordersProcessor;
	}

	public String getId() {
		return id;
	}

	/**
	 * Process the dron's orders instructions set {@link InstructionSet}
	 * 
	 * @return {@link List<Location>} list of locations where the dron deliver the
	 *         order
	 */
	public DeliveryStatus deliverOrders() {
		List<Location> deliverSummary = ordersProcessor.processOrdersIntructions(ordersInstructionDetails);
		return new DeliveryStatus(id, deliverSummary);
	}

}
