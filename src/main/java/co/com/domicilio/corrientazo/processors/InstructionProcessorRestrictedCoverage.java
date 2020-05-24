package co.com.domicilio.corrientazo.processors;

import java.util.ArrayList;
import java.util.List;

import co.com.domicilio.corrientazo.enums.InstructionSet;
import co.com.domicilio.corrientazo.models.Location;

/**
 * Specifies the way to follow a set of delivery orders to calculate the final
 * deliver location, using an Spanish instruction set.
 * 
 * @author JoseAlejandro
 *
 */
public final class InstructionProcessorRestrictedCoverage implements InstructionProcessor {

	private int deliverMaxNumberBlocks = 10;

	public InstructionProcessorRestrictedCoverage() {
		
	}

	/**
	 * Calculate the locations of all the set of delivery orders using an Spanish
	 * instruction set
	 * 
	 * @param deliveryOrders a list of of set of delivery orders
	 * @return a list of {@link Location}
	 */
	public List<Location> processOrdersIntructions(List<InstructionSet[]> ordersInstructions) {

		List<Location> locationSummary = new ArrayList<Location>();

		for (InstructionSet[] instructionSet : ordersInstructions) {
			Location location = new Location();
			instrucSet: for (int instruccion = 0; instruccion < instructionSet.length; instruccion++) {
				switch (instructionSet[instruccion]) {
				case A:
					location.advanceOneBlock();
					if (!isInsideDeliveryCoverage(location)) {
						location.setOutOfCoverage(true);
						break instrucSet;
					}
					break;
				case I:
					location.turnDirection(InstructionSet.I);
					break;
				case D:
					location.turnDirection(InstructionSet.D);
					break;
				}
			}
			locationSummary.add(location);
		}

		return locationSummary;
	}

	/**
	 * Validate if a location is inside the coverage area
	 * 
	 * @param location {@link Location}
	 * @return <code>true</code> if is inside the coverage area, <code>false</code>
	 *         otherwise
	 */
	private boolean isInsideDeliveryCoverage(Location location) {
		return (Math.abs(location.getCoordenateX()) <= deliverMaxNumberBlocks
				&& Math.abs(location.getCoordenateY()) <= deliverMaxNumberBlocks);

	}
}
