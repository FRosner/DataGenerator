package de.frosner.datagenerator.distributions;

import static org.fest.assertions.Assertions.assertThat;

import java.util.Map;

import net.sf.qualitycheck.exception.IllegalInstanceOfArgumentException;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Maps;

import de.frosner.datagenerator.exceptions.FeatureValueCannotBeMappedException;
import de.frosner.datagenerator.features.ContinuousFeatureValue;
import de.frosner.datagenerator.features.DiscreteFeatureValue;
import de.frosner.datagenerator.features.FeatureDefinition;

public class DiscreteVariableParameterTest {

	private FeatureDefinition _featureDefinition;
	private DiscreteVariableParameter<Double> _parameter;

	@Before
	public void setupParameter() {
		_featureDefinition = new FeatureDefinition("F", new DummyDistribution());
		Map<DiscreteFeatureValue, Double> parameterSelection = Maps.newHashMap();
		parameterSelection.put(new DiscreteFeatureValue(0), -100d);
		parameterSelection.put(new DiscreteFeatureValue(1), 100d);
		_parameter = new DiscreteVariableParameter<Double>(parameterSelection, _featureDefinition);
	}

	@Test
	public void testUpdateParameter() {
		_parameter.updateParameter(new DiscreteFeatureValue(0));
		assertThat(_parameter.getParameter()).isEqualTo(-100d);

		_parameter.updateParameter(new DiscreteFeatureValue(1));
		assertThat(_parameter.getParameter()).isEqualTo(100d);
	}

	@Test
	public void testGetFeatureDefinition() {
		assertThat(_parameter.getFeatureDefinitionConditionedOn()).isEqualTo(_featureDefinition);
	}

	@Test(expected = IllegalInstanceOfArgumentException.class)
	public void testUpdateParameter_featureValueNotDiscrete() {
		_parameter.updateParameter(new ContinuousFeatureValue(5));
	}

	@Test(expected = FeatureValueCannotBeMappedException.class)
	public void testUpdateParameter_featureValueCannotBeMapped() {
		_parameter.updateParameter(new DiscreteFeatureValue(2));
	}

}
