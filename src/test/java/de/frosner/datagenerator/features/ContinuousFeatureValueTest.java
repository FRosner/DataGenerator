package de.frosner.datagenerator.features;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;

public class ContinuousFeatureValueTest {

	@Test
	public void testEquals() {
		assertThat(new ContinuousFeatureValue(0)).isEqualTo(new ContinuousFeatureValue(0));
		assertThat(new ContinuousFeatureValue(0)).isNotEqualTo(new ContinuousFeatureValue(1));
	}
}
