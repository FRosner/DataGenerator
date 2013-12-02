package de.frosner.datagenerator.generator;

import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;
import net.sf.qualitycheck.exception.IllegalEmptyArgumentException;
import net.sf.qualitycheck.exception.IllegalStateOfArgumentException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import de.frosner.datagenerator.distributions.DummyDistribution;
import de.frosner.datagenerator.distributions.ParameterizedDummyDistribution;
import de.frosner.datagenerator.distributions.VariableDummyParameter;
import de.frosner.datagenerator.export.ExportConnection;
import de.frosner.datagenerator.features.DummyFeatureValue;
import de.frosner.datagenerator.features.FeatureDefinition;

public class DataGeneratorTest {

	@Mock
	private ExportConnection _mockedOut;
	private FeatureDefinition _x;
	private FeatureDefinition _y;
	private FeatureDefinition _z;
	private FeatureDefinitionGraph _graph;
	private DataGenerator _generator;

	@Before
	public void setUp() {
		initMocks(this);
		_graph = new FeatureDefinitionGraph();
	}

	@Before
	public void createFeatureDefinitions() {
		_x = new FeatureDefinition("x", new DummyDistribution());
		_y = new FeatureDefinition("y", new DummyDistribution());
		_z = new FeatureDefinition("z", new DummyDistribution());
	}

	@Test(expected = IllegalEmptyArgumentException.class)
	public void testCreate_noFeatures() {
		new DataGenerator(1, _mockedOut, new FeatureDefinitionGraph());
	}

	@Test(expected = IllegalStateOfArgumentException.class)
	public void testCreate_nonPositiveNumberOfInstances() {
		_graph.addFeatureDefinition(_x);
		new DataGenerator(0, _mockedOut, _graph);
	}

	@Test
	public void testGenerateInstance() {
		_graph.addFeatureDefinition(_x);
		_graph.addFeatureDefinition(_y);
		_generator = new DataGenerator(1, _mockedOut, _graph);
		_generator.generate();
		verify(_mockedOut).exportMetaData(_graph);
		verify(_mockedOut).exportInstance(new Instance(0, DummyDistribution.ANY_SAMPLE, DummyDistribution.ANY_SAMPLE));
	}

	@Test
	public void testGenerateInstances() {
		int numberOfInstances = 5;
		_graph.addFeatureDefinition(_x);
		_graph.addFeatureDefinition(_y);
		_graph.addFeatureDefinition(_z);
		_generator = new DataGenerator(numberOfInstances, _mockedOut, _graph);
		_generator.generate();
		verify(_mockedOut).exportMetaData(_graph);
		for (int i = 0; i < numberOfInstances; i++) {
			verify(_mockedOut).exportInstance(
					new Instance(i, DummyDistribution.ANY_SAMPLE, DummyDistribution.ANY_SAMPLE,
							DummyDistribution.ANY_SAMPLE));
		}
	}

	@Test
	public void testGenerateInstancesWithDependencies() {
		VariableDummyParameter dependentParameter = new VariableDummyParameter();
		FeatureDefinition dependentFeature = new FeatureDefinition("D", new ParameterizedDummyDistribution(
				dependentParameter));
		_graph.addFeatureDefinition(_x);
		_graph.addDependentFeatureDefinition(_x, dependentFeature, dependentParameter);

		_generator = new DataGenerator(1, _mockedOut, _graph);
		_generator.generate();
		verify(_mockedOut).exportMetaData(_graph);
		verify(_mockedOut).exportInstance(
				new Instance(0, DummyDistribution.ANY_SAMPLE, new DummyFeatureValue(DummyDistribution.ANY_SAMPLE)));
	}

}
