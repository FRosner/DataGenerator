package de.frosner.datagenerator.distributions;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.Delta.delta;
import net.sf.qualitycheck.exception.IllegalInstanceOfArgumentException;

import org.junit.Before;
import org.junit.Test;

import de.frosner.datagenerator.features.ContinuousFeatureValue;
import de.frosner.datagenerator.features.FeatureValue;

public class GaussianDistributionTest {

	private GaussianDistribution _distribution;

	@Before
	public void createDistribution() {
		_distribution = new GaussianDistribution(0, 1);
	}

	@Test
	public void testGetProbabilityOf() {
		assertThat(_distribution.getProbabilityOf(new ContinuousFeatureValue(0))).isEqualTo(0.39, delta(0.01));
		assertThat(_distribution.getProbabilityOf(new ContinuousFeatureValue(1))).isEqualTo(0.24, delta(0.01));
		assertThat(_distribution.getProbabilityOf(new ContinuousFeatureValue(-1))).isEqualTo(0.24, delta(0.01));
	}

	@Test
	public void testSample() {
		assertThat(_distribution.sample()).isInstanceOf(ContinuousFeatureValue.class);
	}

	@Test(expected = IllegalInstanceOfArgumentException.class)
	public void testGetProbabilityOf_wrongFeatureValueType() {
		FeatureValue wrongFeatureValueType = new FeatureValue() {
			@Override
			public Object getValue() {
				return null;
			}
		};
		_distribution.getProbabilityOf(wrongFeatureValueType);
	}
}
