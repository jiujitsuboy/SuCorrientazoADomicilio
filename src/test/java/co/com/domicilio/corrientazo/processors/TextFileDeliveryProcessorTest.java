package co.com.domicilio.corrientazo.processors;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import co.com.domicilio.corrientazo.enums.CardinalPoint;
import co.com.domicilio.corrientazo.models.DeliveryStatus;
import co.com.domicilio.corrientazo.models.Location;
import co.com.domicilio.corrientazo.parsers.ParseTextInstruction;
import co.com.domicilio.corrientazo.processors.textfile.util.HandleFilesFromDirectory;

@ExtendWith(MockitoExtension.class)
public class TextFileDeliveryProcessorTest extends DeliveryProcessorTest {

	private static final Logger LOGGER = Logger.getLogger(TextFileDeliveryProcessorTest.class.getName());
	@Mock
	private HandleFilesFromDirectory handleFilesFromDirectory;
	private ParseTextInstruction parseTextInstruction;
	private TextFileDeliveryProcessor textFileDeliveryProcessor;

	@BeforeEach
	public void init() {

		parseTextInstruction = new ParseTextInstruction();
		textFileDeliveryProcessor = new TextFileDeliveryProcessor(handleFilesFromDirectory, parseTextInstruction);

	}

	@Override
	protected DeliveryProcessor createDeliveryProcessor() {
		int NUMBER_OF_FILES = 2;
		String[] lines = new String[] { "AIAAAA", "ADAAAAA", "IAAAAA" };
		when(handleFilesFromDirectory.readFiles()).thenReturn(createFileList(NUMBER_OF_FILES, lines));
		when(handleFilesFromDirectory.readFileContent(anyString())).thenReturn(lines);
		
		return textFileDeliveryProcessor;
	}

	@Test
	public void readMoreThan20DeliveriesRoots() {

		int NUMBER_OF_FILES = 21;
		String[] lines = new String[] { "AIAAAA", "ADAAAAA", "IAAAAA" };

		when(handleFilesFromDirectory.readFiles()).thenReturn(createFileList(NUMBER_OF_FILES, lines));
		when(handleFilesFromDirectory.readFileContent(anyString())).thenReturn(lines);

		textFileDeliveryProcessor.readDeliveries();

		assertTrue(textFileDeliveryProcessor.isMaxNumberRouteExceed());

	}

	@Test
	public void readLessThan20DeliveriesRoots() {

		int NUMBER_OF_FILES = 19;
		String[] lines = new String[] { "AIAAAA", "ADAAAAA", "IAAAAA" };

		when(handleFilesFromDirectory.readFiles()).thenReturn(createFileList(NUMBER_OF_FILES, lines));
		when(handleFilesFromDirectory.readFileContent(anyString())).thenReturn(lines);
		
		textFileDeliveryProcessor.readDeliveries();

		assertFalse(textFileDeliveryProcessor.isMaxNumberRouteExceed());

	}

	@Test
	public void readMoreThan3InstructionsSetPerDelivery() {

		int NUMBER_OF_FILES = 1;
		String[] lines = new String[] { "AIAAAA", "ADAAAAA", "IAAAAA", "DAAAAIA" };

		when(handleFilesFromDirectory.readFiles()).thenReturn(createFileList(NUMBER_OF_FILES, lines));
		when(handleFilesFromDirectory.readFileContent(anyString())).thenReturn(lines);

		textFileDeliveryProcessor.readDeliveries();

		assertTrue(textFileDeliveryProcessor.isMaxNumberDeliveryExceed());

	}

	@Test
	public void readLessThan3InstructionsSetPerDelivery() {

		int NUMBER_OF_FILES = 1;
		String[] lines = new String[] { "AIAAAA", "ADAAAAA", "IAAAAA" };

		when(handleFilesFromDirectory.readFiles()).thenReturn(createFileList(NUMBER_OF_FILES, lines));
		when(handleFilesFromDirectory.readFileContent(anyString())).thenReturn(lines);

		textFileDeliveryProcessor.readDeliveries();

		assertFalse(textFileDeliveryProcessor.isMaxNumberDeliveryExceed());

	}

	@Test
	public void writeDeliveryStatus() {

		List<DeliveryStatus> deliveriesStatus = List
				.of(new DeliveryStatus("01", List.of(new Location(0, 1, CardinalPoint.NORTE))));

		doNothing().when(handleFilesFromDirectory).writeFile(anyString(), any());

		textFileDeliveryProcessor.generateDeliveriesReport(deliveriesStatus);

		verify(handleFilesFromDirectory, times(1)).writeFile(anyString(), any());

	}

	private File[] createFileList(int numberOfFiles, String[] lines) {

		File[] files = new File[numberOfFiles];

		for (int index = 0; index < numberOfFiles; index++) {
			String suffix = (index < 10) ? "0" + (index + 1) : String.valueOf(index + 1);

			try {
				files[index] = createTempFile(String.format("in%s", suffix), lines);
			} catch (IOException e) {
				LOGGER.severe(e.getMessage());
			}
		}

		return files;
	}
}