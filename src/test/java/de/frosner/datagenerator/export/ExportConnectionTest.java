package de.frosner.datagenerator.export;

import org.junit.Before;
import org.junit.Test;

import de.frosner.datagenerator.distributions.DummyDistribution;
import de.frosner.datagenerator.exceptions.IllegalMethodCallSequenceException;
import de.frosner.datagenerator.exceptions.MethodNotCallableTwiceException;
import de.frosner.datagenerator.features.DummyFeatureValue;
import de.frosner.datagenerator.features.FeatureDefinition;
import de.frosner.datagenerator.generator.FeatureDefinitionGraph;
import de.frosner.datagenerator.generator.Instance;

public class ExportConnectionTest {

	private ExportConnection _csvExportConnection = new ExportConnection() {

		@Override
		public String getExportLocation() {
			return null;
		}

		@Override
		protected void exportMetaDataStrategy(FeatureDefinitionGraph featureDefinitions) {
		}

		@Override
		protected void exportInstanceStrategy(Instance instance) {
		}

		@Override
		public void close() {
		}

	};

	private FeatureDefinitionGraph _features;
	private Instance _instance;

	@Before
	public void createInstances() {
		_features = new FeatureDefinitionGraph();
		_features.addFeatureDefinition(new FeatureDefinition("feature", new DummyDistribution()));
		_instance = new Instance(0, new DummyFeatureValue(null));
	}

	@Test(expected = MethodNotCallableTwiceException.class)
	public void testExportMetaData_mustNotBeCallableTwice() {
		_csvExportConnection.exportMetaData(_features);
		_csvExportConnection.exportMetaData(_features);
	}

	@Test(expected = IllegalMethodCallSequenceException.class)
	public void testExportMetaData_mustNotBeCallableAfterExportInstance() {
		_csvExportConnection.exportInstance(_instance);
		_csvExportConnection.exportMetaData(_features);
	}

}
