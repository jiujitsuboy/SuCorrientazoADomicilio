package co.com.domicilio.corrientazo.processors;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import co.com.domicilio.corrientazo.processors.textfile.util.HandleFilesFromDirectory;
import co.com.domicilio.corrientazo.enums.InstructionSet;
import co.com.domicilio.corrientazo.exceptions.InvalidRouteDescription;
import co.com.domicilio.corrientazo.factories.NumbericPropertiesFactory;
import co.com.domicilio.corrientazo.models.DeliveryStatus;
import co.com.domicilio.corrientazo.models.DronOrdersConfiguration;
import co.com.domicilio.corrientazo.models.Location;
import co.com.domicilio.corrientazo.parsers.ParseTextInstruction;

/**
 * Read delivery instructions from files in the working directory and generate
 * one report file per delivery
 * 
 * @author jose.nino
 *
 */
public class TextFileDeliveryProcessor implements DeliveryProcessor {

	private static final Logger LOGGER = Logger.getLogger(TextFileDeliveryProcessor.class.getName());
	private final int FILE_NAME_PREFIX_LENGTH = 2;

	private Integer maxNumberRoutes;
	private Integer maxNumberDeliveries;

	private boolean maxNumberRouteExceed;
	private boolean maxNumberDeliveryExceed;
	private HandleFilesFromDirectory handleFilesFromDirectory;
	private ParseTextInstruction parseTextInstruction;

	public TextFileDeliveryProcessor(HandleFilesFromDirectory handleFilesFromDirectory,
			ParseTextInstruction parseTextInstruction) {
		this.handleFilesFromDirectory = handleFilesFromDirectory;
		this.parseTextInstruction = parseTextInstruction;

		maxNumberRoutes = NumbericPropertiesFactory.getPropertieValue("dron.max");
		maxNumberDeliveries = NumbericPropertiesFactory.getPropertieValue("dron.max.delivery");
	}

	public boolean isMaxNumberRouteExceed() {
		return maxNumberRouteExceed;
	}

	public boolean isMaxNumberDeliveryExceed() {
		return maxNumberDeliveryExceed;
	}

	/**
	 * Pull from files the {@link DronOrdersConfiguration}
	 */
	@Override
	public List<DronOrdersConfiguration> readDeliveries() {

		List<DronOrdersConfiguration> routesDelivery = new ArrayList<DronOrdersConfiguration>();

		File[] routesInfo = handleFilesFromDirectory.readFiles();

		int nroRoutesFound = 0;

		for (File routeInfo : routesInfo) {

			nroRoutesFound++;

			if (isMaxNumberRoutesExceed(nroRoutesFound)) {
				break;
			}

			List<InstructionSet[]> deliveries = new ArrayList<InstructionSet[]>(maxNumberDeliveries);

			String[] lines = handleFilesFromDirectory.readFileContent(routeInfo.getAbsolutePath());

			for (int nroDeliveries = 0; nroDeliveries < lines.length; nroDeliveries++) {

				if (isMaxNumberDeliveriesExceed(nroDeliveries + 1, routeInfo.getName())) {
					break;
				}

				try {
					deliveries.add(parseTextInstruction.parse(lines[nroDeliveries]));
				} catch (InvalidRouteDescription ex) {
					LOGGER.warning(
							String.format("Error parsing route configuration [%s]:  %s", routeInfo, ex.getMessage()));
				}
			}

			if (deliveries.size() == 0) {
				LOGGER.warning(String.format("Not content in route file [%s] ", routeInfo));
			} else {
				String deliveryId = routeInfo.getName().substring(FILE_NAME_PREFIX_LENGTH,
						routeInfo.getName().indexOf("."));

				routesDelivery.add(new DronOrdersConfiguration(deliveryId, deliveries));
			}
		}

		return routesDelivery;
	}

	/**
	 * Write to working directory as many delivery status report it has in
	 * {@link List<DeliveryStatus>}
	 */
	@Override
	public void generateDeliveriesReport(List<DeliveryStatus> deliveriesStatus) {

		for (DeliveryStatus deliveryStatus : deliveriesStatus) {

			List<Location> locations = deliveryStatus.getLocation();

			String[] lines = new String[locations.size()];

			for (int index = 0; index < locations.size(); index++) {
				lines[index] = locations.get(index).toString();

			}

			handleFilesFromDirectory.writeFile(deliveryStatus.getId(), lines);

		}
	}

	/**
	 * Validate if the number of deliveries per Route is not exceed
	 * 
	 * @param nroDeliveriesFound current number of deliveries obtain from the route
	 * @param fileName           file from the deliveries was obtained
	 * @return <code>boolean</code>
	 */
	private boolean isMaxNumberDeliveriesExceed(int nroDeliveriesFound, String fileName) {

		if (nroDeliveriesFound > maxNumberDeliveries) {
			maxNumberDeliveryExceed = true;
			LOGGER.warning(
					String.format("Max number[%d] of delivery exceeded on file %s", maxNumberDeliveries, fileName));
		}

		return maxNumberDeliveryExceed;
	}

	/**
	 * Validate if the number of routes found in the folder is not exceed
	 * 
	 * @param nroRoutesFound current number of routes found in the folder
	 * @return <code>boolean</code>
	 */
	private boolean isMaxNumberRoutesExceed(int nroRoutesFound) {
		if (nroRoutesFound > maxNumberRoutes) {
			maxNumberRouteExceed = true;
			LOGGER.warning(String.format("Max number[%d] of routes exceeded", maxNumberRoutes));
		}

		return maxNumberRouteExceed;
	}
}
