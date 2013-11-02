package de.frosner.datagenerator.distributions;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.Delta.delta;

import java.util.List;

import net.sf.qualitycheck.exception.IllegalInstanceOfArgumentException;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;

import de.frosner.datagenerator.features.ContinuousFeatureValue;
import de.frosner.datagenerator.features.FeatureValue;
import de.frosner.datagenerator.util.StatisticsTestUtil;

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
	public void testSampleMeanAndSigma_nonStandard() {
		double expectedMean = 5;
		double expectedSigma = 10;
		_distribution = new GaussianDistribution(expectedMean, expectedSigma);
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

	@Test(expected = IllegalInstanceOfArgumentException.class)
	public void testGetProbabilityOf_wrongFeatureValueType() {
		FeatureValue wrongFeatureValueType = new FeatureValue() {
			@Override
			public Object getValue() {
				return null;
			}

			@Override
			public String getValueAsString() {
				return null;
			}
		};
		_distribution.getProbabilityOf(wrongFeatureValueType);
	}
}
