package de.frosner.datagenerator.distributions;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;

import de.frosner.datagenerator.exceptions.VariableParameterNotSetException;
import de.frosner.datagenerator.features.DummyFeatureValue;
import de.frosner.datagenerator.features.FeatureValue;

public class VariableParameterTest {

	private static class DummyVariableParameter extends VariableParameter<Object> {
		@Override
		public void updateParameter(FeatureValue value) {
			_parameter = value.getValue();
		}
	}

	@Test
	public void testGetParameter() {
		DummyVariableParameter parameter = new DummyVariableParameter();
		parameter.updateParameter(new DummyFeatureValue("Test"));
		assertThat(parameter.getParameter()).isEqualTo("Test");
	}

	@Test(expected = VariableParameterNotSetException.class)
	public void testGetParameter_notUpdated() {
		DummyVariableParameter parameter = new DummyVariableParameter();
		assertThat(parameter.getParameter()).isEqualTo("Test");
	}

	@Test(expected = VariableParameterNotSetException.class)
	public void testGetParameter_updatedAndRetrievedButNotUpdatedAgain() {
		DummyVariableParameter parameter = new DummyVariableParameter();
		parameter.updateParameter(new DummyFeatureValue("Test"));
		parameter.getParameter();
		parameter.getParameter();
	}

}
