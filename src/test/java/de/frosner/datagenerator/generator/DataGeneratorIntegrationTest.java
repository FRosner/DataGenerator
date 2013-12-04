package de.frosner.datagenerator.generator;

import static org.fest.assertions.Assertions.assertThat;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import de.frosner.datagenerator.distributions.BernoulliDistribution;
import de.frosner.datagenerator.distributions.CategorialDistribution;
import de.frosner.datagenerator.distributions.ContinuousVariableParameter;
import de.frosner.datagenerator.distributions.DiscreteVariableParameter;
import de.frosner.datagenerator.distributions.FixedParameter;
import de.frosner.datagenerator.distributions.GaussianDistribution;
import de.frosner.datagenerator.export.CsvExportConnection;
import de.frosner.datagenerator.export.ExportConnection;
import de.frosner.datagenerator.features.DiscreteFeatureValue;
import de.frosner.datagenerator.features.FeatureDefinition;
import de.frosner.datagenerator.util.ExportFormatReaderUtil;

public class DataGeneratorIntegrationTest {

	private ByteArrayOutputStream _out;
	private DataGenerator _dataGenerator;
	private FeatureDefinitionGraph _featureDefinitions;
	private ExportConnection _exportConnection;
	private int _numberOfInstances;

	@Before
	public void setup() {
		_out = new ByteArrayOutputStream();
		_featureDefinitions = new FeatureDefinitionGraph();
	}

	@Test
	public void testDataGenerator_csvExport_continuousFeatures_noDependencies() {
		_numberOfInstances = 100;
		_exportConnection = new CsvExportConnection(_out, true, false);
		FeatureDefinition featureA = new FeatureDefinition("A", new GaussianDistribution(new FixedParameter<Double>(
				177.8), new FixedParameter<Double>(7.62)));
		FeatureDefinition featureB = new FeatureDefinition("B", new GaussianDistribution(new FixedParameter<Double>(
				394D), new FixedParameter<Double>(147.32)));
		FeatureDefinition featureC = new FeatureDefinition("C", new GaussianDistribution(new FixedParameter<Double>(
				5.4234324235235345E-14), new FixedParameter<Double>(2.243847294729784E-16)));
		FeatureDefinition featureD = new FeatureDefinition("D", new GaussianDistribution(
				new FixedParameter<Double>(-9d), new FixedParameter<Double>(1d)));
		FeatureDefinition featureE = new FeatureDefinition("E", new GaussianDistribution(new FixedParameter<Double>(
				5.4234324235235345E+14), new FixedParameter<Double>(2.243847294729784E+16)));
		_featureDefinitions.addFeatureDefinition(featureA);
		_featureDefinitions.addFeatureDefinition(featureB);
		_featureDefinitions.addFeatureDefinition(featureC);
		_featureDefinitions.addFeatureDefinition(featureD);
		_featureDefinitions.addFeatureDefinition(featureE);

		_dataGenerator = new DataGenerator(_numberOfInstances, _exportConnection, _featureDefinitions);
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

	@Test
	public void testDataGenerator_csvExport_discreteFeatures_noDependencies() {
		_numberOfInstances = 100;
		_exportConnection = new CsvExportConnection(_out, true, false);
		FeatureDefinition featureA = new FeatureDefinition("A", new BernoulliDistribution(new FixedParameter<Double>(
				0.4)));
		FeatureDefinition featureB = new FeatureDefinition("B", new CategorialDistribution(
				new FixedParameter<List<Double>>(Lists.newArrayList(0.10, 0.4, 0.45, 0.05))));
		_featureDefinitions.addFeatureDefinition(featureA);
		_featureDefinitions.addFeatureDefinition(featureB);

		_dataGenerator = new DataGenerator(_numberOfInstances, _exportConnection, _featureDefinitions);
		_dataGenerator.generate();
		_exportConnection.close();

		Map<String, List<String>> csv = ExportFormatReaderUtil.readCsvWithHeader(_out.toString(), ",");
		assertThat(csv.keySet()).containsOnly("A", "B");
		String bernoulliFormat = "^(0|1)$";
		String categorialFormat = "^(0|1|2|3)$";
		for (List<String> column : csv.values()) {
			assertThat(column).hasSize(_numberOfInstances);
		}
		for (String bernoulliEntry : csv.get("A")) {
			assertThat(bernoulliEntry).matches(bernoulliFormat);
		}
		for (String bernoulliEntry : csv.get("B")) {
			assertThat(bernoulliEntry).matches(categorialFormat);
		}
	}

	@Test
	public void testDataGenerator_csvExport_gaussianFeatureWithGaussianPriors() {
		_numberOfInstances = 100;
		_exportConnection = new CsvExportConnection(_out, true, false);
		FeatureDefinition meanFeature = new FeatureDefinition("Mean", new GaussianDistribution(
				new FixedParameter<Double>(-1000d), new FixedParameter<Double>(1d)));
		FeatureDefinition sigmaFeature = new FeatureDefinition("Sigma", new GaussianDistribution(
				new FixedParameter<Double>(10000d), new FixedParameter<Double>(1d)));
		ContinuousVariableParameter meanParameter = new ContinuousVariableParameter();
		ContinuousVariableParameter sigmaParameter = new ContinuousVariableParameter();
		FeatureDefinition dependentFeature = new FeatureDefinition("D", new GaussianDistribution(meanParameter,
				sigmaParameter));

		_featureDefinitions.addFeatureDefinition(meanFeature);
		_featureDefinitions.addFeatureDefinition(sigmaFeature);
		_featureDefinitions.addFeatureDefinitionParameterDependency(meanFeature, dependentFeature, meanParameter);
		_featureDefinitions.addFeatureDefinitionParameterDependency(sigmaFeature, dependentFeature, sigmaParameter);

		_dataGenerator = new DataGenerator(_numberOfInstances, _exportConnection, _featureDefinitions);
		_dataGenerator.generate();
		_exportConnection.close();

		Map<String, List<String>> csv = ExportFormatReaderUtil.readCsvWithHeader(_out.toString(), ",");
		assertThat(csv.keySet()).containsOnly("Mean", "Sigma", "D");
		String arbitraryDoubleFormat = "^\\-?[0-9]+\\.[0-9]+(E\\-?[0-9]+)?$";
		String negativeDoubleFormat = "^\\-[0-9]+\\.[0-9]+(E\\-?[0-9]+)?$";
		String positiveDoubleFormat = "^[0-9]+\\.[0-9]+(E\\-?[0-9]+)?$";

		for (List<String> column : csv.values()) {
			assertThat(column).hasSize(_numberOfInstances);
		}

		List<String> meanValues = csv.get("Mean");
		for (String means : meanValues) {
			assertThat(means).matches(negativeDoubleFormat);
		}

		List<String> sigmaValues = csv.get("Sigma");
		for (String sigma : sigmaValues) {
			assertThat(sigma).matches(positiveDoubleFormat);
		}

		List<String> gaussianValues = csv.get("D");
		for (String gaussian : gaussianValues) {
			assertThat(gaussian).matches(arbitraryDoubleFormat);
		}
	}

	@Test
	public void testDataGenerator_csvExport_bernoulliFeatureWithBernoulliPrior() {
		_numberOfInstances = 100;
		_exportConnection = new CsvExportConnection(_out, true, false);

		FeatureDefinition coinA = new FeatureDefinition("A", new BernoulliDistribution(new FixedParameter<Double>(0.5)));

		Map<DiscreteFeatureValue, Double> bCoins = Maps.newHashMap();
		bCoins.put(new DiscreteFeatureValue(0), 0d);
		bCoins.put(new DiscreteFeatureValue(1), 1d);
		DiscreteVariableParameter<Double> bParameter = new DiscreteVariableParameter<Double>(bCoins);
		FeatureDefinition coinB = new FeatureDefinition("B", new BernoulliDistribution(bParameter));

		_featureDefinitions.addFeatureDefinition(coinA);
		_featureDefinitions.addFeatureDefinitionParameterDependency(coinA, coinB, bParameter);

		_dataGenerator = new DataGenerator(_numberOfInstances, _exportConnection, _featureDefinitions);
		_dataGenerator.generate();
		_exportConnection.close();

		Map<String, List<String>> csv = ExportFormatReaderUtil.readCsvWithHeader(_out.toString(), ",");
		assertThat(csv.keySet()).containsOnly("A", "B");

		for (List<String> column : csv.values()) {
			assertThat(column).hasSize(_numberOfInstances);
		}

		List<String> aValues = csv.get("A");
		List<String> bValues = csv.get("B");
		for (int row = 0; row < _numberOfInstances; row++) {
			if (aValues.get(row).equals("0")) {
				assertThat(bValues.get(row)).isEqualTo("0");
			} else {
				assertThat(bValues.get(row)).isEqualTo("1");
			}
		}

	}

}
