package de.frosner.datagenerator.generator;

import static org.fest.assertions.Assertions.assertThat;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;

import de.frosner.datagenerator.distributions.GaussianDistribution;
import de.frosner.datagenerator.export.CsvExportConnection;
import de.frosner.datagenerator.export.ExportConnection;
import de.frosner.datagenerator.features.FeatureDefinition;

public class DataGeneratorIntegrationTest {

	private ByteArrayOutputStream _out;
	private DataGenerator _dataGenerator;
	private ExportConnection _exportConnection;
	private int _numberOfInstances;

	@Before
	public void createOutputStream() {
		_out = new ByteArrayOutputStream();
	}

	@Test
	public void testDataGenerator_csvExport_continuousFeatures() throws IOException {
		_numberOfInstances = 100;
		_exportConnection = new CsvExportConnection(_out, true);
		FeatureDefinition featureA = new FeatureDefinition("A", new GaussianDistribution(177.8, 7.62));
		FeatureDefinition featureB = new FeatureDefinition("B", new GaussianDistribution(394, 147.32));
		FeatureDefinition featureC = new FeatureDefinition("C", new GaussianDistribution(5.4234324235235345E-14,
				2.243847294729784E-16));
		FeatureDefinition featureD = new FeatureDefinition("D", new GaussianDistribution(-9, 1));
		FeatureDefinition featureE = new FeatureDefinition("E", new GaussianDistribution(5.4234324235235345E+14,
				2.243847294729784E+16));
		List<FeatureDefinition> featureDefinitions = Lists.newArrayList(featureA, featureB, featureC, featureD,
				featureE);

		_dataGenerator = new DataGenerator(_numberOfInstances, _exportConnection, featureDefinitions);
		_dataGenerator.generate();

		String doubleRegex = "^\\-?[0-9]+\\.[0-9]+(E\\-?[0-9]+)?$";
		String[] exportedLines = _out.toString().split("\\n");

		assertThat(exportedLines).hasSize(_numberOfInstances + 1);
		int lineNumber = 0;
		for (String linesString : exportedLines) {
			String[] cells = linesString.split(",");
			assertThat(cells).hasSize(featureDefinitions.size());
			if (lineNumber++ == 0) {
				cells[0].equals("A");
				cells[1].equals("B");
				cells[2].equals("C");
				cells[3].equals("D");
				cells[4].equals("E");
			} else {
				for (String cellsString : cells) {
					assertThat(cellsString).matches(doubleRegex);
				}
			}
		}
	}
}
