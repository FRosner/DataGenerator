package de.frosner.datagenerator.generator;

import static org.fest.assertions.Assertions.assertThat;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;

import de.frosner.datagenerator.distributions.GaussianDistribution;
import de.frosner.datagenerator.export.CsvExportConnection;
import de.frosner.datagenerator.export.ExportConnection;
import de.frosner.datagenerator.features.FeatureDefinition;
import de.frosner.datagenerator.util.ExportFormatReaderUtil;

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
	public void testDataGenerator_csvExport_continuousFeatures() {
		_numberOfInstances = 100;
		_exportConnection = new CsvExportConnection(_out, true, false);
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
		_exportConnection.close();

		Map<String, List<String>> csv = ExportFormatReaderUtil.readCsvWithHeader(_out.toString(), ",");
		assertThat(csv.keySet()).containsOnly("A", "B", "C", "D", "E");
		String doubleFormat = "^\\-?[0-9]+\\.[0-9]+(E\\-?[0-9]+)?$";
		for (List<String> column : csv.values()) {
			assertThat(column).hasSize(_numberOfInstances);
			for (String entry : column) {
				assertThat(entry.matches(doubleFormat));
			}
		}
	}
}
