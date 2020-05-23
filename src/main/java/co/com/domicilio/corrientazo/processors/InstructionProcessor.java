package co.com.domicilio.corrientazo.processors;

import java.util.List;

import co.com.domicilio.corrientazo.enums.InstructionSet;
import co.com.domicilio.corrientazo.models.Location;

/**
 * Specifies the way to follow a set of delivery orders to calculate the final deliver location
 * @author JoseAlejandro
 *
 */
public interface InstructionProcessor {
	/**
	 * process a list of instructions to deliver a order
	 * @param ordersInstructions a list of a set of delivery instructions
	 * @return a list of {@link Location}
	 */
	public List<Location> processOrdersIntructions(List<InstructionSet[]> ordersInstructions);
}
