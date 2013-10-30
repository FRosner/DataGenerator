package de.frosner.datagenerator.features;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;

public class ContinuousFeatureValueTest {

	private ContinuousFeatureValue _value;

	@Before
	public void createFeatureValue() {
		_value = new ContinuousFeatureValue(0);
	}

	@Test
	public void testEquals() {
		assertThat(_value).isEqualTo(_value);
		assertThat(_value).isEqualTo(new ContinuousFeatureValue(0));
		assertThat(_value).isNotEqualTo(new ContinuousFeatureValue(1));
		assertThat(_value).isNotEqualTo(new DummyFeatureValue(0));
		assertThat(_value).isNotEqualTo(null);
	}

	@Test
	public void testGetValue() {
		assertThat(_value.getValue()).isEqualTo(0D);
	}

	@Test
	public void testGetValueAsString() {
		assertThat(_value.getValueAsString()).isEqualTo("0.0");
	}

}
