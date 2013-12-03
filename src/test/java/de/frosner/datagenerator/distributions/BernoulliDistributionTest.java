package de.frosner.datagenerator.distributions;

import static org.fest.assertions.Assertions.assertThat;

import java.util.List;

import org.fest.assertions.Delta;
import org.junit.Test;

import com.google.common.collect.Lists;

import de.frosner.datagenerator.features.DiscreteFeatureValue;
import de.frosner.datagenerator.util.StatisticsTestUtil;

public class BernoulliDistributionTest {

	private BernoulliDistribution _distribution;

	@Test
	public void testSample() {
		_distribution = new BernoulliDistribution(new FixedParameter<Double>(0.4));
		_distribution.setSeed(43253);
		assertThat(_distribution.sample()).isInstanceOf(DiscreteFeatureValue.class);
		List<Integer> samples = Lists.newArrayList();
		for (int i = 0; i < 100000; i++) {
			samples.add((Integer) _distribution.sample().getValue());
		}
		double sampleMean = StatisticsTestUtil.sampleMeanFromIntegerList(samples);
		assertThat(sampleMean).isEqualTo(0.4, Delta.delta(0.01));
	}

}
