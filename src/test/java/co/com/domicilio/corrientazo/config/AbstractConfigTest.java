package co.com.domicilio.corrientazo.config;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Logger;

import co.com.domicilio.corrientazo.factories.NumbericPropertiesFactory;

public abstract class AbstractConfigTest {

	private static final Logger LOGGER = Logger.getLogger(AbstractConfigTest.class.getName());

	public AbstractConfigTest() {
		bootStrapConfiguration();
	}

	public void bootStrapConfiguration() {
		NumbericPropertiesFactory.init();
	}

	protected File createTempFile(String fileName, String[] lines) throws IOException {
		File folderPath = new File(System.getProperty("user.dir"));

		File tempFile = File.createTempFile(fileName, ".txt", folderPath);
		tempFile.deleteOnExit();

		if (lines != null) {
			try (BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile.getAbsolutePath()))) {
				for (String line : lines) {
					bw.write(line);
					bw.newLine();
				}
				bw.flush();
			} catch (IOException ex) {
				LOGGER.severe(ex.getMessage());
			}

		}

		return tempFile;
	}
}
