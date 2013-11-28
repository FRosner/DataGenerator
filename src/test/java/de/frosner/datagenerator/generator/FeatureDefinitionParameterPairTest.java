package de.frosner.datagenerator.generator;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;

import de.frosner.datagenerator.distributions.DummyDistribution;
import de.frosner.datagenerator.distributions.Parameter;
import de.frosner.datagenerator.features.FeatureDefinition;

public class FeatureDefinitionParameterPairTest {

	@Test
	public void testEquals() {
		FeatureDefinition definition = new FeatureDefinition("F", new DummyDistribution());
		Parameter parameter = new DummyParameter(0);
		FeatureDefinitionParameterPair pair = new FeatureDefinitionParameterPair(definition, parameter);
		assertThat(pair.equals(new FeatureDefinitionParameterPair(definition, parameter))).isTrue();
	}
}
