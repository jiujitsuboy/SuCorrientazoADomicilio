package co.com.domicilio.corrientazo.factories;

import co.com.domicilio.corrientazo.models.Dron;
import co.com.domicilio.corrientazo.models.DronOrdersConfiguration;
import co.com.domicilio.corrientazo.processors.InstructionProcessor;

/**
 * A factory for the creating of {@link Dron} with their schedules
 * 
 * @author JoseAlejandro
 *
 */
public class DronFactory {

	private InstructionProcessor ordersProcessor;

	public DronFactory(InstructionProcessor ordersProcessor) {
		this.ordersProcessor = ordersProcessor;
	}

	/**
	 * Create a {@link Dron}
	 * 
	 * @param dronSchedule delivery schedule
	 * @return {@link Dron}
	 */
	public Dron createDron(DronOrdersConfiguration dronOrdersConfiguration) {
		return new Dron(dronOrdersConfiguration, ordersProcessor);
	}

}
