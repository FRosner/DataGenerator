package de.frosner.datagenerator.features;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;

import de.frosner.datagenerator.distributions.Distribution;
import de.frosner.datagenerator.distributions.DummyDistribution;
import de.frosner.datagenerator.distributions.ParameterizedDummyDistribution;
import de.frosner.datagenerator.distributions.VariableDummyParameter;

public class FeatureDefinitionTest {

	@Test
	public void testEquals() {
		Distribution distribution = new DummyDistribution();
		FeatureDefinition definition = new FeatureDefinition("F", distribution);
		assertThat(definition.equals(new FeatureDefinition("F", distribution))).isTrue();
	}

	@Test
	public void testGetVariableParameters() {
		VariableDummyParameter parameter = new VariableDummyParameter();
		Distribution distribution = new ParameterizedDummyDistribution(parameter);
		FeatureDefinition definition = new FeatureDefinition("F", distribution);

		assertThat(definition.getDependentParameters()).containsOnly(parameter);
	}

}
