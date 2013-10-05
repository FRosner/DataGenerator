package de.frosner.datagenerator.export;

import java.util.List;

import org.junit.Test;

import com.google.common.collect.Lists;

import de.frosner.datagenerator.distributions.DummyDistribution;
import de.frosner.datagenerator.features.DummyFeatureValue;
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

	private List<FeatureDefinition> _features = Lists.newArrayList(new FeatureDefinition("", new DummyDistribution()));
	private Instance _instance = new Instance(0, new DummyFeatureValue(null));

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
