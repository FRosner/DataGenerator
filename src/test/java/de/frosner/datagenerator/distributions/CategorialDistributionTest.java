package de.frosner.datagenerator.distributions;

import static org.fest.assertions.Assertions.assertThat;

import java.util.List;

import net.sf.qualitycheck.exception.IllegalNullArgumentException;

import org.fest.assertions.Delta;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;

import de.frosner.datagenerator.features.DiscreteFeatureValue;
import de.frosner.datagenerator.util.StatisticsTestUtil;

public class CategorialDistributionTest {

	private CategorialDistribution _distribution;

	@Before
	public void createDistribution() {
		_distribution = new CategorialDistribution(new FixedParameter<List<Double>>(Lists.newArrayList(0.6, 0.3, 0.1)));
	}

	@Test
	public void testSample() {
		_distribution.setSeed(43253);
		assertThat(_distribution.sample()).isInstanceOf(DiscreteFeatureValue.class);
		List<Integer> samples = Lists.newArrayList();
		for (int i = 0; i < 100000; i++) {
			samples.add((Integer) _distribution.sample().getValue());
		}
		double sampleMean = StatisticsTestUtil.sampleMeanFromIntegerList(samples);
		assertThat(sampleMean).isEqualTo(0.5, Delta.delta(0.01));
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void testCreate_nullArgument() {
		new CategorialDistribution(null);
	}

}
