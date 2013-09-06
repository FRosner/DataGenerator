package de.frosner.datagenerator.generator;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;

import de.frosner.datagenerator.distributions.DummyDistribution;
import de.frosner.datagenerator.features.FeatureDefinition;

public class DataGeneratorServiceTest {

	private DataGeneratorService _service;

	@Before
	public void createDataGeneratorService() {
		_service = new DataGeneratorService();
	}

	@Test
	public void testAddFeatureDefinition() {
		FeatureDefinition feature1 = new FeatureDefinition("1", new DummyDistribution());
		FeatureDefinition feature2 = new FeatureDefinition("2", new DummyDistribution());

		assertThat(_service.getFeatureDefinitions()).isEmpty();
		_service.addFeatureDefinition(feature1);
		assertThat(_service.getFeatureDefinitions()).containsExactly(feature1);
		_service.addFeatureDefinition(feature2);
		assertThat(_service.getFeatureDefinitions()).containsExactly(feature1, feature2);
	}

	public void testRemoveFeatureDefinition(int index) {
		FeatureDefinition feature1 = new FeatureDefinition("1", new DummyDistribution());
		FeatureDefinition feature2 = new FeatureDefinition("2", new DummyDistribution());
		_service.getFeatureDefinitions().add(feature1);
		_service.getFeatureDefinitions().add(feature2);

		_service.removeFeatureDefinition(0);
		assertThat(_service.getFeatureDefinitions()).containsExactly(feature2);

		_service.removeFeatureDefinition(0);
		assertThat(_service.getFeatureDefinitions()).isEmpty();
	}
}
