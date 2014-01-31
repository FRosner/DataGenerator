package de.frosner.datagenerator.distributions;

import net.sf.qualitycheck.exception.IllegalNullArgumentException;
import net.sf.qualitycheck.exception.IllegalStateOfArgumentException;

import org.junit.Test;

import de.frosner.datagenerator.features.ContinuousFeatureValue;

public class IntervalTest {

	@Test(expected = IllegalStateOfArgumentException.class)
	public void testCreate_lowerBoundEqualsUpperBound() {
		new Interval(new ContinuousFeatureValue(0), new ContinuousFeatureValue(-1));
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void testCreate_lowerBoundsNull() {
		new Interval(null, new ContinuousFeatureValue(-1));
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void testCreate_upperBoundsNull() {
		new Interval(new ContinuousFeatureValue(0), null);
	}

}
