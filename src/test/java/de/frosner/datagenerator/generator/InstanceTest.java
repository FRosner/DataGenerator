package de.frosner.datagenerator.generator;

import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.fail;
import net.sf.qualitycheck.exception.IllegalNullElementsException;

import org.junit.Test;

import de.frosner.datagenerator.features.ContinuousFeatureValue;
import de.frosner.datagenerator.features.FeatureValue;

public class InstanceTest {

	private Instance _instance;

	@Test(expected = IllegalNullElementsException.class)
	public void testCreationWithNullArguments() {
		new Instance(0, new ContinuousFeatureValue(0), null);
	}

	@Test
	public void testIterator() {
		_instance = new Instance(0, new ContinuousFeatureValue(1), new ContinuousFeatureValue(2),
				new ContinuousFeatureValue(3));
		int i = 1;
		for (FeatureValue value : _instance) {
			assertThat(value).isEqualTo(new ContinuousFeatureValue(i++));
		}
	}

	@Test
	public void testEquals() {
		_instance = new Instance(0, new ContinuousFeatureValue(1));
		Instance instance1 = new Instance(0, new ContinuousFeatureValue(1));
		Instance instance2 = new Instance(1, new ContinuousFeatureValue(1));
		Instance instance3 = new Instance(0, new ContinuousFeatureValue(2));
		assertThat(_instance.equals(instance1)).isTrue();
		assertThat(_instance.equals(instance2)).isFalse();
		assertThat(_instance.equals(instance3)).isFalse();
	}

	@Test
	public void testCreateInstanceWithoutFeatures() {
		_instance = Instance.builder(1).build();
		for (@SuppressWarnings("unused")
		FeatureValue values : _instance) {
			fail("Instance should not contain any values.");
		}
	}
}
