package de.frosner.datagenerator.main;

import static org.fest.assertions.Assertions.assertThat;
import net.sf.qualitycheck.exception.IllegalNullElementsException;

import org.junit.Test;

import de.frosner.datagenerator.features.ContinuousFeatureValue;
import de.frosner.datagenerator.features.FeatureValue;

public class InstanceTest {

	@Test(expected = IllegalNullElementsException.class)
	public void testCreationWithNullArguments() {
		new Instance(0, new ContinuousFeatureValue(0), null);
	}

	@Test
	public void testIterator() {
		Instance instance = new Instance(0, new ContinuousFeatureValue(1), new ContinuousFeatureValue(2),
				new ContinuousFeatureValue(3));
		int i = 1;
		for (FeatureValue value : instance) {
			assertThat(value).isEqualTo(new ContinuousFeatureValue(i++));
		}
	}

}
