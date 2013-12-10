package de.frosner.datagenerator.generator;

import static org.fest.assertions.Assertions.assertThat;

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
import de.frosner.datagenerator.features.ContinuousFeatureValue;
import de.frosner.datagenerator.features.DiscreteFeatureValue;
import de.frosner.datagenerator.features.FeatureDefinition;
import de.frosner.datagenerator.features.FeatureValue;

public class DataGeneratorIntegrationTest {

	private static final int NUMBER_OF_INSTANCES = 1000;

	private DataGenerator _dataGenerator;
	private FeatureDefinitionGraph _featureDefinitions;
	private DummyExportConnection _exportConnection;

	@Before
	public void setup() {
		_featureDefinitions = new FeatureDefinitionGraph();
		_exportConnection = new DummyExportConnection();
	}

	@Test
	public void testGenerate_continuousFeatures_noDependencies() {
		FeatureDefinition feature = new FeatureDefinition("A", new GaussianDistribution(new FixedParameter<Double>(0d),
				new FixedParameter<Double>(1d)));
		_featureDefinitions.addFeatureDefinition(feature);

		_dataGenerator = new DataGenerator(NUMBER_OF_INSTANCES, _exportConnection, _featureDefinitions);
		_dataGenerator.generate();

		assertThat(_exportConnection.getMetaData().equals(_featureDefinitions)).isTrue();
		assertThat(_exportConnection.getInstances()).hasSize(NUMBER_OF_INSTANCES);
		for (Instance instance : _exportConnection.getInstances()) {
			FeatureValue gaussianValue = instance.getFeatureValue(0);
			assertThat(gaussianValue).isInstanceOf(ContinuousFeatureValue.class);
		}
	}

	@Test
	public void testGenerate_discreteFeatures_noDependencies() {
		FeatureDefinition featureA = new FeatureDefinition("A", new BernoulliDistribution(new FixedParameter<Double>(
				0.4)));
		FeatureDefinition featureB = new FeatureDefinition("B", new CategorialDistribution(
				new FixedParameter<List<Double>>(Lists.newArrayList(0.10, 0.4, 0.45, 0.05))));
		_featureDefinitions.addFeatureDefinition(featureA);
		_featureDefinitions.addFeatureDefinition(featureB);

		_dataGenerator = new DataGenerator(NUMBER_OF_INSTANCES, _exportConnection, _featureDefinitions);
		_dataGenerator.generate();

		assertThat(_exportConnection.getMetaData().equals(_featureDefinitions)).isTrue();
		assertThat(_exportConnection.getInstances()).hasSize(NUMBER_OF_INSTANCES);
		for (Instance instance : _exportConnection.getInstances()) {
			FeatureValue bernoulliValue = instance.getFeatureValue(0);
			assertThat(bernoulliValue).isInstanceOf(DiscreteFeatureValue.class);
			assertThat(bernoulliValue.getValue()).isIn(Lists.newArrayList(0, 1));

			FeatureValue categorialValue = instance.getFeatureValue(1);
			assertThat(categorialValue).isInstanceOf(DiscreteFeatureValue.class);
			assertThat(categorialValue.getValue()).isIn(Lists.newArrayList(0, 1, 2, 3));
		}
	}

	@Test
	public void testGenerate_gaussianFeature_gaussianPriors() {
		FeatureDefinition meanFeature = new FeatureDefinition("Mean", new GaussianDistribution(
				new FixedParameter<Double>(-1000d), new FixedParameter<Double>(1d)));
		FeatureDefinition sigmaFeature = new FeatureDefinition("Sigma", new GaussianDistribution(
				new FixedParameter<Double>(10d), new FixedParameter<Double>(1d)));
		ContinuousVariableParameter meanParameter = new ContinuousVariableParameter();
		ContinuousVariableParameter sigmaParameter = new ContinuousVariableParameter();
		FeatureDefinition dependentFeature = new FeatureDefinition("D", new GaussianDistribution(meanParameter,
				sigmaParameter));

		_featureDefinitions.addFeatureDefinition(meanFeature);
		_featureDefinitions.addFeatureDefinition(sigmaFeature);
		_featureDefinitions.addFeatureDefinitionParameterDependency(meanFeature, dependentFeature, meanParameter);
		_featureDefinitions.addFeatureDefinitionParameterDependency(sigmaFeature, dependentFeature, sigmaParameter);

		_dataGenerator = new DataGenerator(NUMBER_OF_INSTANCES, _exportConnection, _featureDefinitions);
		_dataGenerator.generate();

		assertThat(_exportConnection.getMetaData().equals(_featureDefinitions)).isTrue();
		assertThat(_exportConnection.getInstances()).hasSize(NUMBER_OF_INSTANCES);
		for (Instance instance : _exportConnection.getInstances()) {
			FeatureValue meanValue = instance.getFeatureValue(0);
			assertThat(meanValue).isInstanceOf(ContinuousFeatureValue.class);
			assertThat((Double) meanValue.getValue()).isNegative();

			FeatureValue sigmaValue = instance.getFeatureValue(1);
			assertThat(sigmaValue).isInstanceOf(ContinuousFeatureValue.class);
			assertThat((Double) sigmaValue.getValue()).isPositive();

			FeatureValue dependentValue = instance.getFeatureValue(2);
			assertThat(dependentValue).isInstanceOf(ContinuousFeatureValue.class);
			assertThat((Double) dependentValue.getValue()).isNegative();
		}
	}

	@Test
	public void testGenerate_bernoulliFeature_bernoulliPrior() {
		FeatureDefinition coinA = new FeatureDefinition("A", new BernoulliDistribution(new FixedParameter<Double>(0.5)));

		Map<DiscreteFeatureValue, Double> bCoins = Maps.newHashMap();
		bCoins.put(new DiscreteFeatureValue(0), 0d);
		bCoins.put(new DiscreteFeatureValue(1), 1d);
		DiscreteVariableParameter<Double> bParameter = new DiscreteVariableParameter<Double>(bCoins);
		FeatureDefinition coinB = new FeatureDefinition("B", new BernoulliDistribution(bParameter));

		_featureDefinitions.addFeatureDefinition(coinA);
		_featureDefinitions.addFeatureDefinitionParameterDependency(coinA, coinB, bParameter);

		_dataGenerator = new DataGenerator(NUMBER_OF_INSTANCES, _exportConnection, _featureDefinitions);
		_dataGenerator.generate();

		assertThat(_exportConnection.getMetaData().equals(_featureDefinitions)).isTrue();
		assertThat(_exportConnection.getInstances()).hasSize(NUMBER_OF_INSTANCES);
		for (Instance instance : _exportConnection.getInstances()) {
			FeatureValue aValue = instance.getFeatureValue(0);
			assertThat(aValue).isInstanceOf(DiscreteFeatureValue.class);

			FeatureValue bValue = instance.getFeatureValue(1);
			assertThat(bValue).isInstanceOf(DiscreteFeatureValue.class);

			assertThat(aValue.getValue()).isEqualTo(bValue.getValue());
		}
	}

}
