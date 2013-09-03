package de.frosner.datagenerator.export;

import static org.fest.assertions.Assertions.assertThat;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.junit.Before;
import org.junit.Test;

import de.frosner.datagenerator.features.DummyFeatureValue;
import de.frosner.datagenerator.main.Instance;

public class CsvExportConnectionTest {

	private OutputStream _out = new ByteArrayOutputStream();
	private CsvExportConnection _csvExportConnection;
	private Instance _dummyInstanceWithOneFeature = new Instance(0, new DummyFeatureValue(0));
	private Instance _dummyInstanceWithTwoFeatures = new Instance(0, new DummyFeatureValue(0), new DummyFeatureValue(1));

	@Before
	public void createCsvExportConnection() {
		_csvExportConnection = new CsvExportConnection(_out);
	}

	@Test
	public void testExport_oneInstance_oneFeature() throws IOException {
		_csvExportConnection.export(_dummyInstanceWithOneFeature);
		_csvExportConnection.close();
		assertThat(_out.toString())
				.isEqualTo(_dummyInstanceWithOneFeature.getFeatureValue(0).getValueAsString() + "\n");
	}

	@Test
	public void testExport_twoInstances_oneFeature() throws IOException {
		_csvExportConnection.export(_dummyInstanceWithOneFeature);
		_csvExportConnection.export(_dummyInstanceWithOneFeature);
		_csvExportConnection.close();
		assertThat(_out.toString()).isEqualTo(
				_dummyInstanceWithOneFeature.getFeatureValue(0).getValueAsString() + "\n"
						+ _dummyInstanceWithOneFeature.getFeatureValue(0).getValueAsString() + "\n");
	}

	@Test
	public void testExport_oneInstance_twoFeatures() throws IOException {
		_csvExportConnection.export(_dummyInstanceWithTwoFeatures);
		_csvExportConnection.close();
		assertThat(_out.toString()).isEqualTo(
				_dummyInstanceWithTwoFeatures.getFeatureValue(0).getValueAsString() + ","
						+ _dummyInstanceWithTwoFeatures.getFeatureValue(1).getValueAsString() + "\n");
	}

	@Test
	public void testExport_twoInstances_twoFeatures() throws IOException {
		_csvExportConnection.export(_dummyInstanceWithTwoFeatures);
		_csvExportConnection.export(_dummyInstanceWithTwoFeatures);
		_csvExportConnection.close();
		assertThat(_out.toString()).isEqualTo(
				_dummyInstanceWithTwoFeatures.getFeatureValue(0).getValueAsString() + ","
						+ _dummyInstanceWithTwoFeatures.getFeatureValue(1).getValueAsString() + "\n"
						+ _dummyInstanceWithTwoFeatures.getFeatureValue(0).getValueAsString() + ","
						+ _dummyInstanceWithTwoFeatures.getFeatureValue(1).getValueAsString() + "\n");
	}

	@Test(expected = IOException.class)
	public void testExportAfterClose() throws IOException {
		_csvExportConnection.close();
		_csvExportConnection.export(_dummyInstanceWithOneFeature);
	}
}
