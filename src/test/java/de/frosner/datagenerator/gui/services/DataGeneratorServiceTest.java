package de.frosner.datagenerator.gui.services;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import de.frosner.datagenerator.distributions.DummyDistribution;
import de.frosner.datagenerator.export.ExportConfiguration;
import de.frosner.datagenerator.export.ExportConnection;
import de.frosner.datagenerator.features.FeatureDefinition;
import de.frosner.datagenerator.generator.Instance;

public class DataGeneratorServiceTest {

	@Mock
	private ExportConnection _mockedExportConnection;
	@Mock
	private ExportConfiguration _mockedExportConfiguration;
	private DataGeneratorService _service;

	private FeatureDefinition _feature1 = new FeatureDefinition("1", new DummyDistribution());
	private FeatureDefinition _feature2 = new FeatureDefinition("2", new DummyDistribution());

	@Before
	public void createObjects() {
		_service = new DataGeneratorService();
	}

	@Before
	public void initializeMocks() {
		initMocks(this);
		when(_mockedExportConfiguration.createExportConnection()).thenReturn(_mockedExportConnection);
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
	public void testGenerateData() throws InterruptedException {
		_service.getFeatureDefinitions().add(_feature1);
		Thread.sleep(100);
		_service.getFeatureDefinitions().add(_feature2);
		Thread.sleep(100);

		_service.generateData(5, _mockedExportConfiguration);
		verify(_mockedExportConnection).exportInstance(
				new Instance(0, DummyDistribution.ANY_SAMPLE, DummyDistribution.ANY_SAMPLE));
	}
}
