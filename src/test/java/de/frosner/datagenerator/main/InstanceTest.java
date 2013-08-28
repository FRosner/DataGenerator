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

	@Test
	public void testEquals() {
		Instance instanceUnderTest = new Instance(0, new ContinuousFeatureValue(1));
		Instance instance1 = new Instance(0, new ContinuousFeatureValue(1));
		Instance instance2 = new Instance(1, new ContinuousFeatureValue(1));
		Instance instance3 = new Instance(0, new ContinuousFeatureValue(2));
		assertThat(instanceUnderTest.equals(instance1)).isTrue();
		assertThat(instanceUnderTest.equals(instance2)).isFalse();
		assertThat(instanceUnderTest.equals(instance3)).isFalse();
	}
}
