package de.frosner.datagenerator.features;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;

import de.frosner.datagenerator.distributions.Distribution;
import de.frosner.datagenerator.distributions.DummyDistribution;

public class FeatureDefinitionTest {

	private Distribution _distribution = new DummyDistribution();

	@Test
	public void testEquals() {
		FeatureDefinition definition = new FeatureDefinition("F", _distribution);
		assertThat(definition.equals(new FeatureDefinition("F", _distribution))).isTrue();
	}

}
