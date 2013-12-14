package de.frosner.datagenerator.distributions;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import net.sf.qualitycheck.exception.IllegalNullArgumentException;

import org.fest.assertions.Delta;
import org.junit.Test;

import com.google.common.collect.Lists;

import de.frosner.datagenerator.exceptions.IllegalProbabilityArgumentException;
import de.frosner.datagenerator.features.DiscreteFeatureValue;
import de.frosner.datagenerator.testutils.StatisticsTestUtil;

public class CategorialDistributionTest {

	private CategorialDistribution _distribution;

	@Test
	public void testSample() {
		_distribution = new CategorialDistribution(new FixedParameter<List<Double>>(Lists.newArrayList(0.6, 0.3, 0.1)));
		_distribution.setSeed(43253);
		assertThat(_distribution.sample()).isInstanceOf(DiscreteFeatureValue.class);
		List<Integer> samples = Lists.newArrayList();
		for (int i = 0; i < 100000; i++) {
			samples.add((Integer) _distribution.sample().getValue());
		}
		double sampleMean = StatisticsTestUtil.sampleMeanFromIntegerList(samples);
		assertThat(sampleMean).isEqualTo(0.5, Delta.delta(0.01));
	}

	@Test
	public void testSample_noRandom() {
		_distribution = new CategorialDistribution(new FixedParameter<List<Double>>(Lists.newArrayList(0d, 0d, 1d)));
		_distribution.setSeed(43253);
		for (int i = 0; i < 100000; i++) {
			assertThat(_distribution.sample().getValue()).isEqualTo(2);
		}
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void testCreate_nullArgument() {
		new CategorialDistribution(null);
	}

	@Test(expected = IllegalProbabilityArgumentException.class)
	public void testSample_illegalFixedParameter() {
		_distribution = new CategorialDistribution(new FixedParameter<List<Double>>(Lists.newArrayList(0.4)));
		_distribution.sample();
	}

	@Test(expected = IllegalProbabilityArgumentException.class)
	public void testSample_illegalVariableParameter() {
		@SuppressWarnings("unchecked")
		VariableParameter<List<Double>> parameter = mock(VariableParameter.class);
		when(parameter.getParameter()).thenReturn(Lists.newArrayList(1.2));
		_distribution = new CategorialDistribution(parameter);
		_distribution.sample();
	}

}
