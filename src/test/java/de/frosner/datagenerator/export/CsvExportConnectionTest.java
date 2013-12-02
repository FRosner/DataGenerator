package de.frosner.datagenerator.export;

import static org.fest.assertions.Assertions.assertThat;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import org.junit.Before;
import org.junit.Test;

import de.frosner.datagenerator.distributions.DummyDistribution;
import de.frosner.datagenerator.exceptions.UncheckedIOException;
import de.frosner.datagenerator.features.DummyFeatureValue;
import de.frosner.datagenerator.features.FeatureDefinition;
import de.frosner.datagenerator.generator.FeatureDefinitionGraph;
import de.frosner.datagenerator.generator.Instance;

public class CsvExportConnectionTest {

	private OutputStream _out = new ByteArrayOutputStream();
	private CsvExportConnection _csvExportConnection;
	private Instance _dummyInstanceWithOneFeature;
	private Instance _dummyInstanceWithTwoFeatures;

	@Before
	public void createCsvExportConnection() {
		_out = new ByteArrayOutputStream();
		_csvExportConnection = new CsvExportConnection(_out, false, false);
		_dummyInstanceWithOneFeature = new Instance(0, new DummyFeatureValue(0));
		_dummyInstanceWithTwoFeatures = new Instance(0, new DummyFeatureValue(0), new DummyFeatureValue(1));
	}

	@Test
	public void testExport_oneInstance_oneFeature() {
		_csvExportConnection.exportInstanceStrategy(_dummyInstanceWithOneFeature);
		_csvExportConnection.close();

		assertThat(_out.toString())
				.isEqualTo(_dummyInstanceWithOneFeature.getFeatureValue(0).getValueAsString() + "\n");
	}

	@Test
	public void testExport_twoInstances_oneFeature() {
		_csvExportConnection.exportInstanceStrategy(_dummyInstanceWithOneFeature);
		_csvExportConnection.exportInstanceStrategy(_dummyInstanceWithOneFeature);
		_csvExportConnection.close();

		assertThat(_out.toString()).isEqualTo(
				_dummyInstanceWithOneFeature.getFeatureValue(0).getValueAsString() + "\n"
						+ _dummyInstanceWithOneFeature.getFeatureValue(0).getValueAsString() + "\n");
	}

	@Test
	public void testExport_oneInstance_twoFeatures() {
		_csvExportConnection.exportInstanceStrategy(_dummyInstanceWithTwoFeatures);
		_csvExportConnection.close();

		assertThat(_out.toString()).isEqualTo(
				_dummyInstanceWithTwoFeatures.getFeatureValue(0).getValueAsString() + ","
						+ _dummyInstanceWithTwoFeatures.getFeatureValue(1).getValueAsString() + "\n");
	}

	@Test
	public void testExport_twoInstances_twoFeatures() {
		_csvExportConnection.exportInstanceStrategy(_dummyInstanceWithTwoFeatures);
		_csvExportConnection.exportInstanceStrategy(_dummyInstanceWithTwoFeatures);
		_csvExportConnection.close();

		assertThat(_out.toString()).isEqualTo(
				_dummyInstanceWithTwoFeatures.getFeatureValue(0).getValueAsString() + ","
						+ _dummyInstanceWithTwoFeatures.getFeatureValue(1).getValueAsString() + "\n"
						+ _dummyInstanceWithTwoFeatures.getFeatureValue(0).getValueAsString() + ","
						+ _dummyInstanceWithTwoFeatures.getFeatureValue(1).getValueAsString() + "\n");
	}

	@Test(expected = UncheckedIOException.class)
	public void testExportAfterClose() {
		_csvExportConnection.close();
		_csvExportConnection.exportInstanceStrategy(_dummyInstanceWithOneFeature);
	}

	@Test
	public void testExportFeatureNames_oneFeature() {
		FeatureDefinitionGraph features = new FeatureDefinitionGraph();
		features.addFeatureDefinition(new FeatureDefinition("usheight", new DummyDistribution()));
		_csvExportConnection = new CsvExportConnection(_out, true, false);
		_csvExportConnection.exportMetaDataStrategy(features);
		_csvExportConnection.close();

		assertThat(_out.toString()).isEqualTo("usheight" + "\n");
	}

	@Test
	public void testExportFeatureNames_twoFeatures() {
		FeatureDefinitionGraph features = new FeatureDefinitionGraph();
		features.addFeatureDefinition(new FeatureDefinition("usheight", new DummyDistribution()));
		features.addFeatureDefinition(new FeatureDefinition("uswidth", new DummyDistribution()));
		_csvExportConnection = new CsvExportConnection(_out, true, false);
		_csvExportConnection.exportMetaDataStrategy(features);
		_csvExportConnection.close();

		assertThat(_out.toString()).isEqualTo("usheight" + "," + "uswidth" + "\n");
	}

	@Test
	public void testExportFeatureNames_doNotExportFeatureNames() {
		FeatureDefinitionGraph features = new FeatureDefinitionGraph();
		features.addFeatureDefinition(new FeatureDefinition("usheight", new DummyDistribution()));
		_csvExportConnection = new CsvExportConnection(_out, false, false);
		_csvExportConnection.exportMetaDataStrategy(features);
		_csvExportConnection.close();

		assertThat(_out.toString()).isEmpty();
	}

	@Test
	public void testExportMetaData_exportInstanceIds() {
		FeatureDefinitionGraph features = new FeatureDefinitionGraph();
		features.addFeatureDefinition(new FeatureDefinition("usheight", new DummyDistribution()));
		_csvExportConnection = new CsvExportConnection(_out, false, true);
		_csvExportConnection.exportMetaDataStrategy(features);
		_csvExportConnection.exportInstanceStrategy(_dummyInstanceWithTwoFeatures);
		_csvExportConnection.exportInstanceStrategy(_dummyInstanceWithTwoFeatures);
		_csvExportConnection.close();

		String[] lines = _out.toString().split("\n");
		assertThat(lines[0]).startsWith("0,");
		assertThat(lines[1]).startsWith("0,");
	}

	@Test
	public void testExportMetaData_exportInstanceIds_exportFeatureNames() {
		FeatureDefinitionGraph features = new FeatureDefinitionGraph();
		features.addFeatureDefinition(new FeatureDefinition("usheight", new DummyDistribution()));
		_csvExportConnection = new CsvExportConnection(_out, true, true);
		_csvExportConnection.exportMetaDataStrategy(features);
		_csvExportConnection.exportInstanceStrategy(_dummyInstanceWithTwoFeatures);
		_csvExportConnection.exportInstanceStrategy(_dummyInstanceWithTwoFeatures);
		_csvExportConnection.close();

		String[] lines = _out.toString().split("\n");
		assertThat(lines[0]).startsWith("ID,");
		assertThat(lines[1]).startsWith("0,");
		assertThat(lines[2]).startsWith("0,");
	}

}
