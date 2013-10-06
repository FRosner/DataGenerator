package de.frosner.datagenerator.generator;

import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.ArrayList;

import net.sf.qualitycheck.exception.IllegalEmptyArgumentException;
import net.sf.qualitycheck.exception.IllegalNullElementsException;
import net.sf.qualitycheck.exception.IllegalStateOfArgumentException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.google.common.collect.Lists;

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

	@Test(expected = IllegalEmptyArgumentException.class)
	public void testCreate_noFeatures() {
		new DataGenerator(1, _mockedOut, new ArrayList<FeatureDefinition>(0));
	}

	@Test(expected = IllegalStateOfArgumentException.class)
	public void testCreate_nonPositiveNumberOfInstances() {
		new DataGenerator(0, _mockedOut, _x);
	}

	@Test(expected = IllegalNullElementsException.class)
	public void testCreateWithBuilder_nullElementsInFeatureDefinition() {
		new DataGenerator(1, _mockedOut, Lists.newArrayList(_x, _y, null, _z));
	}

	@Test
	public void testGenerateInstance() {
		_generator = new DataGenerator(1, _mockedOut, _x, _y);
		_generator.generate();
		verify(_mockedOut).exportMetaData(Lists.newArrayList(_x, _y));
		verify(_mockedOut).exportInstance(new Instance(0, DummyDistribution.ANY_SAMPLE, DummyDistribution.ANY_SAMPLE));
	}

	@Test
	public void testGenerateInstances() {
		int numberOfInstances = 5;
		_generator = new DataGenerator(numberOfInstances, _mockedOut, _x, _y, _z);
		_generator.generate();
		verify(_mockedOut).exportMetaData(Lists.newArrayList(_x, _y, _z));
		for (int i = 0; i < numberOfInstances; i++) {
			verify(_mockedOut).exportInstance(
					new Instance(i, DummyDistribution.ANY_SAMPLE, DummyDistribution.ANY_SAMPLE,
							DummyDistribution.ANY_SAMPLE));
		}
	}

}
