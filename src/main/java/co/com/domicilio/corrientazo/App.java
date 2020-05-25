package co.com.domicilio.corrientazo;

import co.com.domicilio.corrientazo.factories.DronFactory;
import co.com.domicilio.corrientazo.factories.NumbericPropertiesFactory;
import co.com.domicilio.corrientazo.parsers.ParseTextInstruction;
import co.com.domicilio.corrientazo.processors.DeliveryProcessor;
import co.com.domicilio.corrientazo.processors.InstructionProcessorRestrictedCoverage;
import co.com.domicilio.corrientazo.processors.TextFileDeliveryProcessor;
import co.com.domicilio.corrientazo.processors.textfile.util.HandleFilesFromDirectory;

public class App {

	public static void main(String[] args) {

		System.out.println(
				"SU CORRIENTAZO A DOMICILIO\n\nThe Deliver Processor, will pick all the instructions files with the format in[dronId].txt from the execution folder,\nand  will generate the corresponding result file with the format out[dronId].txt\n\n");

		NumbericPropertiesFactory.init();
		
		ParseTextInstruction intructionParser = new ParseTextInstruction();
		HandleFilesFromDirectory folderReader = new HandleFilesFromDirectory();
		InstructionProcessorRestrictedCoverage instructionProcessor = new InstructionProcessorRestrictedCoverage();

		DeliveryProcessor deliveryProcessor = new TextFileDeliveryProcessor(folderReader, intructionParser);

		DronFactory deliveryFactory = new DronFactory(instructionProcessor);
		
		DeliveryManager deliveryManager = new DeliveryManager(deliveryProcessor, deliveryFactory);
		
		deliveryManager.scheduleDeliveries();
	}

}
