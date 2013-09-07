package de.frosner.datagenerator.generator;

import static org.fest.assertions.Assertions.assertThat;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import de.frosner.datagenerator.distributions.DummyDistribution;
import de.frosner.datagenerator.features.FeatureDefinition;

public class DataGeneratorServiceTest {

	private DataGeneratorService _service;

	private FeatureDefinition _feature1 = new FeatureDefinition("1", new DummyDistribution());
	private FeatureDefinition _feature2 = new FeatureDefinition("2", new DummyDistribution());

	@Before
	public void createDataGeneratorService() {
		_service = new DataGeneratorService();
	}

	@Test
	public void testAddFeatureDefinition() {
		assertThat(_service.getFeatureDefinitions()).isEmpty();
		_service.addFeatureDefinition(_feature1);
		assertThat(_service.getFeatureDefinitions()).containsExactly(_feature1);
		_service.addFeatureDefinition(_feature2);
		assertThat(_service.getFeatureDefinitions()).containsExactly(_feature1, _feature2);
	}

	@Test
	public void testRemoveFeatureDefinition() {
		_service.getFeatureDefinitions().add(_feature1);
		_service.getFeatureDefinitions().add(_feature2);

		_service.removeFeatureDefinition(0);
		assertThat(_service.getFeatureDefinitions()).containsExactly(_feature2);

		_service.removeFeatureDefinition(0);
		assertThat(_service.getFeatureDefinitions()).isEmpty();
	}

	@Test
	public void testGenerateData() {
		_service.getFeatureDefinitions().add(_feature1);
		_service.getFeatureDefinitions().add(_feature2);

		File exportFile = new File("src/test/resources/" + DataGeneratorServiceTest.class.getSimpleName() + ".tmp");
		if (exportFile.exists()) {
			exportFile.delete();
		}
		assertThat(exportFile).doesNotExist();
		_service.generateData(1000, exportFile);
		assertThat(exportFile).exists();

		exportFile.delete();
	}
}
