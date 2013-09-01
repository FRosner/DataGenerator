package de.frosner.datagenerator.main;

import java.util.ArrayList;

import net.sf.qualitycheck.exception.IllegalNullElementsException;
import net.sf.qualitycheck.exception.IllegalStateOfArgumentException;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;

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

	@Test(expected = IllegalStateOfArgumentException.class)
	public void testCreate_noFeatures() {
		new DataGenerator(1, _mockedOut, new ArrayList<FeatureDefinition>(0));
	}

	@Test(expected = IllegalStateOfArgumentException.class)
	public void testCreate_nonPositiveNumberOfInstances() {
		new DataGenerator(0, _mockedOut, _x);
	}

	@Test(expected = IllegalStateOfArgumentException.class)
	public void testCreateWithBuilder_noFeatures() {
		DataGenerator.builder(1, _mockedOut).build();
	}

	@Test(expected = IllegalStateOfArgumentException.class)
	public void testCreateWithBuilder_nonPositiveNumberOfInstances() {
		DataGenerator.builder(0, _mockedOut).addFeatureDefinition(_x).build();
	}

	@Test(expected = IllegalNullElementsException.class)
	public void testCreateWithBuilder_nullElementsInFeatureDefinition() {
		new DataGenerator(1, _mockedOut, Lists.newArrayList(_x, _y, null, _z));
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
