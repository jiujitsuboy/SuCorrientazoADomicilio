package co.com.domicilio.corrientazo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import co.com.domicilio.corrientazo.factories.DronFactory;
import co.com.domicilio.corrientazo.models.DeliveryStatus;
import co.com.domicilio.corrientazo.models.Dron;
import co.com.domicilio.corrientazo.models.DronOrdersConfiguration;
import co.com.domicilio.corrientazo.processors.DeliveryProcessor;

/**
 * Orchestrator of the delivery process. Using a {@link DeliveryProcessor}, gather the delivery orders configurations
 * and setup the set of {@link Dron} to process those orders.
 * @author jose.nino
 *
 */
public class DeliveryManager {

	private static final Logger LOGGER = Logger.getLogger(DeliveryManager.class.getName());
	private DronFactory deliveryFactory;
	private DeliveryProcessor deliveryProcessor;

	public DeliveryManager(DeliveryProcessor deliveryProcessor, DronFactory deliveryFactory) {
		this.deliveryProcessor = deliveryProcessor;
		this.deliveryFactory = deliveryFactory;
	}

	/**
	 * Acquired the configuration for the deliveries, create the necessary {@link Dron} according to the amount of {@link DronOrdersConfiguration}
	 * found, and dispatch the {@link Dron} 
	 */
	public void scheduleDeliveries() {

		List<DeliveryStatus> deliveryStatus = null;
		List<CompletableFuture<DeliveryStatus>> dronsDeliverOrders = new ArrayList<CompletableFuture<DeliveryStatus>>();

		List<DronOrdersConfiguration> deliveriesRequest = deliveryProcessor.readDeliveries();

		LOGGER.info(String.format("Read %d dron's delivery instructions ", deliveriesRequest.size()));

		if (deliveriesRequest.size() > 0) {
			for (int dronIndex = 0; dronIndex < deliveriesRequest.size(); dronIndex++) {

				Dron dron = deliveryFactory.createDron(deliveriesRequest.get(dronIndex));

				dronsDeliverOrders.add(CompletableFuture.supplyAsync(() -> dron.deliverOrders()));

			}

			LOGGER.info("Dispatching orders......");

			deliveryStatus = processDeliveries(dronsDeliverOrders);

			LOGGER.info(String.format("Generated %d dron's delivery status ", deliveryStatus.size()));

			deliveryProcessor.generateDeliveriesReport(deliveryStatus);
		}

	}

	/**
	 * Process the delivery instruction per the set of {@link Dron} in asynchronous way
	 * @param dronsDeliverOrders A list of {@link CompletableFuture<DeliveryStatus>} representing the peding work of each {@link Dron} 
	 * @return {@link List<DeliveryStatus>} result of every delivery
	 */
	private List<DeliveryStatus> processDeliveries(List<CompletableFuture<DeliveryStatus>> dronsDeliverOrders) {

		List<DeliveryStatus> deliveryStatus = null;

		CompletableFuture<Void> allDronStatus = CompletableFuture
				.allOf(dronsDeliverOrders.toArray(new CompletableFuture[dronsDeliverOrders.size()]));

		CompletableFuture<List<DeliveryStatus>> allCompletableFutures = allDronStatus.thenApply(future -> {
			return dronsDeliverOrders.stream().map(CompletableFuture::join).collect(Collectors.toList());
		});

		try {
			deliveryStatus = allCompletableFutures.get();
		} catch (InterruptedException | ExecutionException e) {
			LOGGER.severe(e.getMessage());
		}

		return deliveryStatus;
	}

}
