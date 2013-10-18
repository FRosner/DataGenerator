package de.frosner.datagenerator.features;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;

public class DiscreteFeatureValueTest {

	private DiscreteFeatureValue _value;

	@Before
	public void createFeatureValue() {
		_value = new DiscreteFeatureValue(0);
	}

	@Test
	public void testEquals() {
		assertThat(_value).isEqualTo(new DiscreteFeatureValue(0));
		assertThat(_value).isNotEqualTo(new DiscreteFeatureValue(1));
	}

	@Test
	public void testGetValue() {
		assertThat(_value.getValue()).isEqualTo(0);
	}

	@Test
	public void testGetValueAsString() {
		assertThat(_value.getValueAsString()).isEqualTo("0");
	}

}
