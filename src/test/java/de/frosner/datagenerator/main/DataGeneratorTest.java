package de.frosner.datagenerator.main;

import org.junit.Test;

import de.frosner.datagenerator.distributions.DummyDistribution;
import de.frosner.datagenerator.export.MockedExportConnection;
import de.frosner.datagenerator.features.FeatureDefinition;

public class DataGeneratorTest {

	@Test
	public void testGenerateInstance() {
		FeatureDefinition x = new FeatureDefinition("x", new DummyDistribution());
		FeatureDefinition y = new FeatureDefinition("y", new DummyDistribution());
		MockedExportConnection mockedOut = new MockedExportConnection();
		DataGenerator generator = new DataGenerator(1, mockedOut, x, y);
		generator.generate();
		mockedOut.addExpectedExport(new Instance(0, DummyDistribution.ANY_SAMPLE, DummyDistribution.ANY_SAMPLE));
		mockedOut.verify();
	}

	@Test
	public void testGenerateInstances() {
		int numberOfInstances = 5;
		FeatureDefinition x = new FeatureDefinition("x", new DummyDistribution());
		FeatureDefinition y = new FeatureDefinition("y", new DummyDistribution());
		FeatureDefinition z = new FeatureDefinition("y", new DummyDistribution());
		MockedExportConnection mockedOut = new MockedExportConnection();
		DataGenerator generator = new DataGenerator(numberOfInstances, mockedOut, x, y, z);
		generator.generate();
		for (int i = 0; i < numberOfInstances; i++) {
			mockedOut.addExpectedExport(new Instance(i, DummyDistribution.ANY_SAMPLE, DummyDistribution.ANY_SAMPLE,
					DummyDistribution.ANY_SAMPLE));
		}
		mockedOut.verify();
	}

}
