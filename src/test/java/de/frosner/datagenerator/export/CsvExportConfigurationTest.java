package de.frosner.datagenerator.export;

import static org.fest.assertions.Assertions.assertThat;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CsvExportConfigurationTest {

	private File _exportFile;

	@Before
	public void createFile() {
		_exportFile = new File("test.txt");
	}

	@After
	public void deleteFile() {
		_exportFile.delete();
	}

	@Test
	public void testCreateExportConnection() {
		CsvFileExportConfiguration configuration = new CsvFileExportConfiguration(_exportFile, ExportInstanceIds.NO,
				ExportFeatureNames.YES);

		ExportConnection connection = configuration.createExportConnection();
		connection.close();

		assertThat(connection).isInstanceOf(CsvExportConnection.class);
		assertThat(connection.getExportLocation()).endsWith("test.txt");
		assertThat(_exportFile).exists();
	}

}
