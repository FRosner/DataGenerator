package de.frosner.datagenerator.export;

import java.util.List;

import org.junit.Test;

import de.frosner.datagenerator.features.FeatureDefinition;
import de.frosner.datagenerator.generator.Instance;

public class ExportConnectionTest {

	private ExportConnection _csvExportConnection = new ExportConnection() {

		@Override
		public String getExportLocation() {
			return null;
		}

		@Override
		protected void exportMetaDataStrategy(List<FeatureDefinition> featureDefinitions) {
		}

		@Override
		protected void exportInstanceStrategy(Instance instance) {
		}

		@Override
		public void close() {
		}

	};

	@Test(expected = MethodNotCallableTwiceException.class)
	public void testExportMetaData_mustNotBeCallableTwice() {
		_csvExportConnection.exportMetaData(null);
		_csvExportConnection.exportMetaData(null);
	}

	@Test(expected = IllegalMethodCallSequenceException.class)
	public void testExportMetaData_mustNotBeCallableAfterExportInstance() {
		_csvExportConnection.exportInstance(null);
		_csvExportConnection.exportMetaData(null);
	}

}
