package co.com.domicilio.corrientazo.processors;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import co.com.domicilio.corrientazo.enums.InstructionSet;
import co.com.domicilio.corrientazo.exceptions.InvalidRouteDescription;
import co.com.domicilio.corrientazo.factories.NumbericPropertiesFactory;
import co.com.domicilio.corrientazo.models.DeliveryStatus;
import co.com.domicilio.corrientazo.models.DronOrdersConfiguration;
import co.com.domicilio.corrientazo.models.Location;

public class TextFileDeliveryProcessor implements DeliveryProcessor {

	private static final Logger LOGGER = Logger.getLogger(TextFileDeliveryProcessor.class.getName());
	private final String FILE_FILTER_NAME = "^in\\d+\\.txt$";
	private final int FILE_NAME_PREFIX_LENGTH = 2;
	private final String VALID_TOKENS_SET = "^[AID]+$";
	private final String ROUTE_PARSER_ERROR_MSG = "Invalid set of intructions found. Valid set is [A,I,D]";

	private Integer maxNumberRoutes;
	private Integer maxNumberDeliveries;

	private boolean maxNumberRouteExceed;
	private boolean maxNumberDeliveryExceed;

	public TextFileDeliveryProcessor() {
		maxNumberRoutes = NumbericPropertiesFactory.getPropertieValue("dron.max");
		maxNumberDeliveries = NumbericPropertiesFactory.getPropertieValue("dron.max.delivery");
	}

	public boolean isMaxNumberRouteExceed() {
		return maxNumberRouteExceed;
	}

	public boolean isMaxNumberDeliveryExceed() {
		return maxNumberDeliveryExceed;
	}

	@Override
	public List<DronOrdersConfiguration> readDeliveries() {

		List<DronOrdersConfiguration> routesDelivery = new ArrayList<DronOrdersConfiguration>();

		File currentFolder = new File(System.getProperty("user.dir"));

		if (currentFolder.isDirectory()) {
			File[] routesInfo = currentFolder.listFiles((folder, file) -> file.matches(FILE_FILTER_NAME));

			int nroRoutesFound = 0;

			for (File routeInfo : routesInfo) {

				nroRoutesFound++;

				if (nroRoutesFound > maxNumberRoutes) {
					maxNumberRouteExceed = true;
					LOGGER.warning(String.format("Max number[%d] of routes exceeded", maxNumberRoutes));
					break;
				}
				boolean hasContent = false;
				int nroDeliveriesFound = 0;

				List<InstructionSet[]> deliveries = new ArrayList<InstructionSet[]>(maxNumberDeliveries);

				try (BufferedReader br = new BufferedReader(new FileReader(routeInfo))) {
					String line = null;

					while ((line = br.readLine()) != null) {
						nroDeliveriesFound++;
						hasContent = true;

						if (nroDeliveriesFound > maxNumberDeliveries) {
							maxNumberDeliveryExceed = true;
							LOGGER.warning(String.format("Max number[%d] of delivery exceeded on file %s", maxNumberDeliveries,routeInfo.getName()));
							break;
						}

						try {
							deliveries.add(parseRoute(line));
						} catch (InvalidRouteDescription ex) {
							LOGGER.warning(String.format("Error parsing route configuration [%s]:  %s", routeInfo,
									ex.getMessage()));
						}
					}
				} catch (IOException ex) {
					LOGGER.severe(
							String.format("Error reading route configuration [%s]: %s", routeInfo, ex.getMessage()));
				}
				if (!hasContent) {
					LOGGER.warning(String.format("Not content in route file [%s] ", routeInfo));
				} else {
					String deliveryId = routeInfo.getName().substring(FILE_NAME_PREFIX_LENGTH,
							routeInfo.getName().indexOf("."));

					routesDelivery.add(new DronOrdersConfiguration(deliveryId, deliveries));
				}
			}

		}
		return routesDelivery;
	}

	@Override
	public void generateDeliveriesReport(List<DeliveryStatus> deliveriesStatus) {

		File currentFolder = new File(System.getProperty("user.dir"));

		for (DeliveryStatus deliveryStatus : deliveriesStatus) {

			String fileName = String.format("%s%sout%s.txt", currentFolder.getAbsolutePath(), File.separator,
					deliveryStatus.getId());

			try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
				for (Location location : deliveryStatus.getLocation()) {
					bw.write(location.toString());
					bw.newLine();
				}
				bw.flush();
			} catch (IOException ex) {
				LOGGER.severe(String.format("Error writing delivery status for dealer [%d]: %s", deliveryStatus.getId(),
						ex.getMessage()));
			}
		}
	}

	/**
	 * From an String, parse it and generate a valid instruction set
	 * {@link InstructionSet}
	 * 
	 * @param route String which represent a instruction set to parse
	 * @return {@link InstructionSet[]}
	 * @throws InvalidRouteDescription
	 */
	private InstructionSet[] parseRoute(String route) throws InvalidRouteDescription {

		InstructionSet[] instructionSet = null;

		if (!route.matches(VALID_TOKENS_SET)) {
			throw new InvalidRouteDescription(ROUTE_PARSER_ERROR_MSG);
		}

		instructionSet = new InstructionSet[route.length()];
		String upperRoute = route.toUpperCase();

		for (int index = 0; index < upperRoute.length(); index++) {

			switch (upperRoute.charAt(index)) {
			case 'A':
				instructionSet[index] = InstructionSet.A;
				break;
			case 'I':
				instructionSet[index] = InstructionSet.I;
				break;
			case 'D':
				instructionSet[index] = InstructionSet.D;
				break;
			}
		}

		return instructionSet;
	}

}
