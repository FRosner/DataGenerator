package de.frosner.datagenerator.generator;

import static org.fest.assertions.Assertions.assertThat;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import de.frosner.datagenerator.distributions.GaussianDistribution;
import de.frosner.datagenerator.export.CsvExportConnection;
import de.frosner.datagenerator.export.ExportConnection;
import de.frosner.datagenerator.features.FeatureDefinition;
import de.frosner.datagenerator.generator.DataGenerator;

public class DataGeneratorIntegrationTest {

	ByteArrayOutputStream _out;
	FeatureDefinition _fd1;
	FeatureDefinition _fd2;
	FeatureDefinition _fd3;
	FeatureDefinition _fd4;
	FeatureDefinition _fd5;
	int _countFeatureDefinitions = 5;
	DataGenerator _dataGenerator;
	ExportConnection _exportConnection;
	int _numberOfInstances;

	@Before
	public void createOutputStream() {
		_out = new ByteArrayOutputStream();
	}

	@Test
	public void testDataGenerator_csvExport_continuousFeatures() throws IOException {
		_numberOfInstances = 100;
		_exportConnection = new CsvExportConnection(_out);
		_fd1 = new FeatureDefinition("fd1_hightUsMen", new GaussianDistribution(177.8, 7.62));
		_fd2 = new FeatureDefinition("fd2_hightDogs", new GaussianDistribution(394, 147.32));
		_fd3 = new FeatureDefinition("fd3_pirateGold", new GaussianDistribution(5, 2.24));
		_fd4 = new FeatureDefinition("fd4_random1", new GaussianDistribution(-9, 1));
		_fd5 = new FeatureDefinition("fd5_temperatureMallorca", new GaussianDistribution(300, 5));
		_dataGenerator = new DataGenerator(_numberOfInstances, _exportConnection, _fd1, _fd2, _fd3, _fd4, _fd5);

		_dataGenerator.generate();

		String regex = "^\\-?[0-9]+\\.[0-9]+$";
		String[] lines = _out.toString().split("\\n");

		assertThat(lines).hasSize(_numberOfInstances);
		for (String linesString : lines) {
			String[] cells = linesString.split(",");
			assertThat(cells).hasSize(_countFeatureDefinitions);

			for (String cellsString : cells) {
				assertThat(cellsString).matches(regex);
			}

		}
	}
}
