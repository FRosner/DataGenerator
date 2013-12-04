package de.frosner.datagenerator.distributions;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.Delta.delta;

import java.util.List;

import net.sf.qualitycheck.exception.IllegalNullArgumentException;

import org.junit.Test;

import com.google.common.collect.Lists;

import de.frosner.datagenerator.features.ContinuousFeatureValue;
import de.frosner.datagenerator.util.StatisticsTestUtil;

public class GaussianDistributionTest {

	private GaussianDistribution _distribution;

	@Test
	public void testSampleMeanAndSigma_nonStandard() {
		double expectedMean = 5;
		double expectedSigma = 10;
		_distribution = new GaussianDistribution(new FixedParameter<Double>(expectedMean), new FixedParameter<Double>(
				expectedSigma));
		_distribution.setSeed(43253);
		assertThat(_distribution.sample()).isInstanceOf(ContinuousFeatureValue.class);
		List<Double> samples = Lists.newArrayList();
		for (int i = 0; i < 100000; i++) {
			samples.add((Double) _distribution.sample().getValue());
		}
		double sampleMean = StatisticsTestUtil.sampleMeanFromDoubleList(samples);
		double sampleSigma = StatisticsTestUtil.sampleSigma(samples, sampleMean);
		assertThat(sampleMean).isEqualTo(expectedMean, delta(0.1));
		assertThat(sampleSigma).isEqualTo(expectedSigma, delta(0.1));
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void testCreate_firstArgumentNull() {
		new GaussianDistribution(null, new FixedParameter<Double>(1d));
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void testCreate_secondArgumentNull() {
		new GaussianDistribution(new FixedParameter<Double>(1d), null);
	}

}
