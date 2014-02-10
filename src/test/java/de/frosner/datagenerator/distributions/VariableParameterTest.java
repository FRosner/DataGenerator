package de.frosner.datagenerator.distributions;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;

import de.frosner.datagenerator.exceptions.VariableParameterNotSetException;
import de.frosner.datagenerator.features.DummyFeatureValue;

public class VariableParameterTest {

	@Test
	public void testGetParameter() {
		VariableDummyParameter parameter = new VariableDummyParameter();
		parameter.updateParameter(new DummyFeatureValue("Test"));
		assertThat(parameter.getParameter()).isEqualTo("Test");
	}

	@Test(expected = VariableParameterNotSetException.class)
	public void testGetParameter_notUpdated() {
		VariableDummyParameter parameter = new VariableDummyParameter();
		parameter.getParameter();
	}

	@Test(expected = VariableParameterNotSetException.class)
	public void testGetParameter_updatedAndRetrievedButNotUpdatedAgain() {
		VariableDummyParameter parameter = new VariableDummyParameter();
		parameter.updateParameter(new DummyFeatureValue("Test"));
		parameter.getParameter();
		parameter.getParameter();
	}

}
