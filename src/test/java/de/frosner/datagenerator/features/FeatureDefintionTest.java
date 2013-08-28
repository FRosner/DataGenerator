package de.frosner.datagenerator.features;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;

import de.frosner.datagenerator.distributions.Distribution;
import de.frosner.datagenerator.distributions.GaussianDistribution;

public class FeatureDefintionTest {

	@Test
	public void testCreate() {
		String name = "feature";
		Distribution distribution = new GaussianDistribution(0, 1);
		FeatureDefinition featureDefinition = new FeatureDefinition(name, distribution);
		assertThat(featureDefinition.getName()).isEqualTo(name);
		assertThat(featureDefinition.getDistribution()).isEqualTo(distribution);
	}

}
