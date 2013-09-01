package de.frosner.datagenerator.distributions;

import java.util.Random;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

import net.sf.qualitycheck.Check;

import org.apache.commons.math3.analysis.function.Gaussian;

import de.frosner.datagenerator.features.ContinuousFeatureValue;
import de.frosner.datagenerator.features.FeatureValue;

/**
 * Representation of a Gaussian distribution. It samples {@link ContinuousFeatureValue}s around the given mean with the
 * specified standard deviation.
 */
@Immutable
public final class GaussianDistribution implements Distribution {

	private final Random _generator;
	private final Gaussian _function;
	private final String _type = GaussianDistribution.class.getSimpleName();
	private final double _mean;
	private final double _sigma;

	/**
	 * Construct a {@link GaussianDistribution} with the given mean and standard deviation.
	 * 
	 * @param mean
	 * @param sigma
	 */
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
	public String getType() {
		return _type;
	}

	@Override
	public String toString() {
		return _type + " (mean = " + _mean + ", sigma = " + _sigma + ")";
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof GaussianDistribution) {
			GaussianDistribution gaussianDistribution = (GaussianDistribution) o;
			return (gaussianDistribution._mean == _mean) && (gaussianDistribution._sigma == _sigma);
		} else {
			return false;
		}
	}
}
