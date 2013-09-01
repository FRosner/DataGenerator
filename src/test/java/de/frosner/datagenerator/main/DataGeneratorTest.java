package de.frosner.datagenerator.main;

import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import de.frosner.datagenerator.distributions.DummyDistribution;
import de.frosner.datagenerator.export.ExportConnection;
import de.frosner.datagenerator.features.FeatureDefinition;

public class DataGeneratorTest {

	@Mock
	private ExportConnection _mockedOut;
	private FeatureDefinition _x;
	private FeatureDefinition _y;
	private FeatureDefinition _z;
	private DataGenerator _generator;

	@Before
	public void setUp() {
		initMocks(this);
	}

	@Before
	public void createFeatureDefinitions() {
		_x = new FeatureDefinition("x", new DummyDistribution());
		_y = new FeatureDefinition("y", new DummyDistribution());
		_z = new FeatureDefinition("z", new DummyDistribution());
	}

	@Test
	public void testGenerateInstance() throws IOException {
		_generator = new DataGenerator(1, _mockedOut, _x, _y);
		_generator.generate();
		verify(_mockedOut).export(new Instance(0, DummyDistribution.ANY_SAMPLE, DummyDistribution.ANY_SAMPLE));
		verify(_mockedOut).close();
	}

	@Test
	public void testGenerateInstances() throws IOException {
		int numberOfInstances = 5;
		_generator = new DataGenerator(numberOfInstances, _mockedOut, _x, _y, _z);
		_generator.generate();
		for (int i = 0; i < numberOfInstances; i++) {
			verify(_mockedOut).export(
					new Instance(i, DummyDistribution.ANY_SAMPLE, DummyDistribution.ANY_SAMPLE,
							DummyDistribution.ANY_SAMPLE));
		}
		verify(_mockedOut).close();
	}

}
