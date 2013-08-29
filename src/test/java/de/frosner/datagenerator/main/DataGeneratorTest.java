package de.frosner.datagenerator.main;

import org.junit.Before;
import org.junit.Test;

import de.frosner.datagenerator.distributions.DummyDistribution;
import de.frosner.datagenerator.export.MockedExportConnection;
import de.frosner.datagenerator.features.FeatureDefinition;

public class DataGeneratorTest {

	private MockedExportConnection _mockedOut;
	private FeatureDefinition _x;
	private FeatureDefinition _y;
	private FeatureDefinition _z;
	private DataGenerator _generator;

	@Before
	public void initMocks() {
		_mockedOut = new MockedExportConnection();
	}

	@Before
	public void createFeatureDefinitions() {
		_x = new FeatureDefinition("x", new DummyDistribution());
		_y = new FeatureDefinition("y", new DummyDistribution());
		_z = new FeatureDefinition("z", new DummyDistribution());
	}

	@Test
	public void testGenerateInstance() {
		_generator = new DataGenerator(1, _mockedOut, _x, _y);
		_generator.generate();
		_mockedOut.addExpectedExport(new Instance(0, DummyDistribution.ANY_SAMPLE, DummyDistribution.ANY_SAMPLE));
		_mockedOut.verify();
	}

	@Test
	public void testGenerateInstances() {
		int numberOfInstances = 5;
		_generator = new DataGenerator(numberOfInstances, _mockedOut, _x, _y, _z);
		_generator.generate();
		for (int i = 0; i < numberOfInstances; i++) {
			_mockedOut.addExpectedExport(new Instance(i, DummyDistribution.ANY_SAMPLE, DummyDistribution.ANY_SAMPLE,
					DummyDistribution.ANY_SAMPLE));
		}
		_mockedOut.verify();
	}

}
