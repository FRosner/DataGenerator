package de.frosner.datagenerator.distributions;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.Delta.delta;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import net.sf.qualitycheck.exception.IllegalNullArgumentException;

import org.junit.Test;

import com.google.common.collect.Lists;

import de.frosner.datagenerator.exceptions.IllegalSigmaParameterArgumentException;
import de.frosner.datagenerator.features.ContinuousFeatureValue;
import de.frosner.datagenerator.testutils.StatisticsTestUtil;

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

	@Test
	public void testGetPossibleValueInterval() {
		_distribution = new GaussianDistribution(new FixedParameter<Double>(0d), new FixedParameter<Double>(1d));
		assertThat(_distribution.getPossibleValueInterval()).isEqualTo(Interval.UNBOUNDED);
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void testCreate_firstArgumentNull() {
		new GaussianDistribution(null, new FixedParameter<Double>(1d));
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void testCreate_secondArgumentNull() {
		new GaussianDistribution(new FixedParameter<Double>(1d), null);
	}

	@Test(expected = IllegalSigmaParameterArgumentException.class)
	public void testSample_zeroSigma_fixedParameter() {
		_distribution = new GaussianDistribution(new FixedParameter<Double>(1d), new FixedParameter<Double>(0d));
		_distribution.sample();
	}

	@Test(expected = IllegalSigmaParameterArgumentException.class)
	public void testSample_negativeSigma_fixedParameter() {
		_distribution = new GaussianDistribution(new FixedParameter<Double>(1d), new FixedParameter<Double>(-1d));
		_distribution.sample();
	}

	@Test(expected = IllegalSigmaParameterArgumentException.class)
	public void testSample_zeroSigma_variableParameter() {
		@SuppressWarnings("unchecked")
		VariableParameter<Double> sigmaParameter = mock(VariableParameter.class);
		when(sigmaParameter.getParameter()).thenReturn(0d);

		_distribution = new GaussianDistribution(new FixedParameter<Double>(1d), sigmaParameter);
		_distribution.sample();
	}

	@Test(expected = IllegalSigmaParameterArgumentException.class)
	public void testSample_negativeSigma_variableParameter() {
		@SuppressWarnings("unchecked")
		VariableParameter<Double> sigmaParameter = mock(VariableParameter.class);
		when(sigmaParameter.getParameter()).thenReturn(-1d);

		_distribution = new GaussianDistribution(new FixedParameter<Double>(1d), sigmaParameter);
		_distribution.sample();
	}

}
