package de.frosner.datagenerator.distributions;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;

import de.frosner.datagenerator.exceptions.VariableParameterNotSetException;
import de.frosner.datagenerator.features.DummyFeatureValue;
import de.frosner.datagenerator.features.FeatureDefinition;

public class VariableParameterTest {

	private VariableDummyParameter _parameter;

	@Before
	public void createParameter() {
		_parameter = new VariableDummyParameter();
	}

	@Test
	public void testGetParameter() {
		_parameter.updateParameter(new DummyFeatureValue("Test"));
		assertThat(_parameter.getParameter()).isEqualTo("Test");
	}

	@Test(expected = VariableParameterNotSetException.class)
	public void testGetParameter_notUpdated() {
		_parameter.getParameter();
	}

	@Test(expected = VariableParameterNotSetException.class)
	public void testGetParameter_updatedAndRetrievedButNotUpdatedAgain() {
		_parameter.updateParameter(new DummyFeatureValue("Test"));
		_parameter.getParameter();
		_parameter.getParameter();
	}

	@Test
	public void testGetFeatureDefinitionConditionedOn() {
		FeatureDefinition featureDefinition = new FeatureDefinition("Test", new ParameterizedDummyDistribution(
				_parameter));
		_parameter = new VariableDummyParameter(featureDefinition);
		assertThat(_parameter.getFeatureDefinitionConditionedOn()).isEqualTo(featureDefinition);
	}

}
