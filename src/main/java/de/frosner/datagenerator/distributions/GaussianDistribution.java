package de.frosner.datagenerator.distributions;

import java.util.Random;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

import net.sf.qualitycheck.Check;

import org.apache.commons.math3.analysis.function.Gaussian;

import de.frosner.datagenerator.features.ContinuousFeatureValue;
import de.frosner.datagenerator.features.FeatureValue;

@Immutable
public class GaussianDistribution implements Distribution {

	private final Random _generator;
	private final Gaussian _function;
	private final double _mean;
	private final double _sigma;

	public GaussianDistribution(double mean, double sigma) {
		_generator = new Random();
		_function = new Gaussian(mean, sigma);
		_mean = mean;
		_sigma = sigma;
	}

	@Override
	public double getProbabilityOf(@Nonnull FeatureValue value) {
		Check.notNull(value);
		Check.instanceOf(ContinuousFeatureValue.class, value);
		return _function.value((Double) value.getValue());
	}

	@Override
	public FeatureValue sample() {
		return new ContinuousFeatureValue((_generator.nextGaussian() + _mean) * _sigma);
	}

	@Override
	public String toString() {
		return GaussianDistribution.class.getSimpleName() + " (mean = " + _mean + ", sigma = " + _sigma + ")";
	}

}
