package co.com.domicilio.corrientazo.processors.textfile.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


public class HandleFilesFromDirectory {

	private static final Logger LOGGER = Logger.getLogger(HandleFilesFromDirectory.class.getName());
	private final String FILE_FILTER_NAME = "^in\\d+\\.txt$";

	public File[] readFiles() {

		String folderPath = System.getProperty("user.dir");

		return readFiles(folderPath);
	}

	public File[] readFiles(String folderPath) {

		File[] routesInfo = null;

		File currentFolder = new File(folderPath);

		if (currentFolder.isDirectory()) {
			routesInfo = currentFolder.listFiles((folder, file) -> file.matches(FILE_FILTER_NAME));
		}

		return routesInfo;
	}

	public String[] readFileContent(String filePath) {

		List<String> lines = new ArrayList<String>();

		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			String line = null;

			while ((line = br.readLine()) != null) {
				lines.add(line);
			}

		} catch (IOException ex) {
			LOGGER.severe(String.format("Error reading file [%s]: %s", filePath, ex.getMessage()));
		}
		
		return lines.toArray(new String[lines.size()]);
		
	}

	public void writeFile(String id, String[] lines) {

		File currentFolder = new File(System.getProperty("user.dir"));
		writeFile(id, lines, currentFolder);

	}

	public void writeFile(String id, String[] lines, File folderPath) {

		String fileName = String.format("%s%sout%s.txt", folderPath.getAbsolutePath(), File.separator, id);

		try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
			for (String line : lines) {
				bw.write(line);
				bw.newLine();
			}
			bw.flush();
		} catch (IOException ex) {
			LOGGER.severe(ex.getMessage());
		}
	}
}
