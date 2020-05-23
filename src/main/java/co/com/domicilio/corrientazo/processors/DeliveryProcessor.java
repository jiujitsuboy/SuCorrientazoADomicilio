package co.com.domicilio.corrientazo.processors;

import java.util.List;

import co.com.domicilio.corrientazo.models.DeliveryStatus;
import co.com.domicilio.corrientazo.models.DronOrdersConfiguration;

/**
 * Read delivery instructions and generate a delivery report
 * 
 * @author JoseAlejandro
 *
 */
public interface DeliveryProcessor {

	/**
	 * Read the delivery set and construct a List of {@link DronOrdersConfiguration}
	 * 
	 * @return List of {@link DronOrdersConfiguration}
	 */
	public List<DronOrdersConfiguration> readDeliveries();

	/**
	 * Generate a report with the {@link DeliveryStatus}
	 * 
	 * @param DeliveriesStatus status per delivery set
	 */
	public void generateDeliveriesReport(List<DeliveryStatus> DeliveriesStatus);
}
