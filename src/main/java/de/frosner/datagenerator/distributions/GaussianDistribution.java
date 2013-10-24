package de.frosner.datagenerator.distributions;

import java.util.Random;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

import net.sf.qualitycheck.Check;

import org.apache.commons.math3.analysis.function.Gaussian;

import de.frosner.datagenerator.features.ContinuousFeatureValue;
import de.frosner.datagenerator.features.FeatureValue;
import de.frosner.datagenerator.util.VisibleForTesting;

/**
 * Representation of a Gaussian distribution. It samples {@linkplain ContinuousFeatureValue}s around the given mean with
 * the specified standard deviation.
 */
@Immutable
public final class GaussianDistribution implements Distribution {

	private static final String TYPE = "Gaussian";

	private final Random _generator;
	private final Gaussian _function;
	private final double _mean;
	private final double _sigma;

	/**
	 * Construct a {@linkplain GaussianDistribution} with the given mean and standard deviation.
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

	@VisibleForTesting
	void setSeed(long seed) {
		_generator.setSeed(seed);
	}

	@Override
	public double getProbabilityOf(@Nonnull FeatureValue value) {
		Check.notNull(value);
		Check.instanceOf(ContinuousFeatureValue.class, value);
		return _function.value((Double) value.getValue());
	}

	@Override
	public FeatureValue sample() {
		return new ContinuousFeatureValue(_generator.nextGaussian() * _sigma + _mean);
	}

	@Override
	public String getType() {
		return TYPE;
	}

	@Override
	public String getParameterDescription() {
		return "Mean = " + _mean + ", Sigma = " + _sigma;
	}

	@Override
	public String toString() {
		return GaussianDistribution.class.getSimpleName() + " (" + getParameterDescription() + ")";
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
