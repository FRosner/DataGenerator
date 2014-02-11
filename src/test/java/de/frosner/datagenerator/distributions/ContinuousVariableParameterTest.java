package de.frosner.datagenerator.distributions;

import static org.fest.assertions.Assertions.assertThat;
import net.sf.qualitycheck.exception.IllegalInstanceOfArgumentException;

import org.junit.Before;
import org.junit.Test;

import de.frosner.datagenerator.features.ContinuousFeatureValue;
import de.frosner.datagenerator.features.DiscreteFeatureValue;
import de.frosner.datagenerator.features.FeatureDefinition;

public class ContinuousVariableParameterTest {

	private FeatureDefinition _featureDefinition;
	private ContinuousVariableParameter _parameter;

	@Before
	public void setupParameter() {
		_featureDefinition = new FeatureDefinition("F", new DummyDistribution());
		_parameter = new ContinuousVariableParameter(_featureDefinition);
	}

	@Test
	public void testUpdateParameter() {
		_parameter.updateParameter(new ContinuousFeatureValue(0.5));
		assertThat(_parameter.getParameter()).isEqualTo(0.5);
	}

	@Test
	public void testGetFeatureDefinition() {
		assertThat(_parameter.getFeatureDefinitionConditionedOn()).isEqualTo(_featureDefinition);
	}

	@Test(expected = IllegalInstanceOfArgumentException.class)
	public void testUpdateParameter_featureValueNotContinuous() {
		_parameter.updateParameter(new DiscreteFeatureValue(5));
	}

}
