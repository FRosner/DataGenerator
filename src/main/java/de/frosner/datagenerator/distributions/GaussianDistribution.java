package de.frosner.datagenerator.distributions;

import java.util.Random;

import javax.annotation.concurrent.Immutable;

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
	private final Parameter<Double> _mean;
	private final Parameter<Double> _sigma;

	/**
	 * Construct a {@linkplain GaussianDistribution} with the given mean and standard deviation.
	 * 
	 * @param mean
	 * @param sigma
	 */
	public GaussianDistribution(Parameter<Double> mean, Parameter<Double> sigma) {
		_generator = new Random();
		_mean = mean;
		_sigma = sigma;
	}

	@VisibleForTesting
	void setSeed(long seed) {
		_generator.setSeed(seed);
	}

	@Override
	public FeatureValue sample() {
		return new ContinuousFeatureValue(_generator.nextGaussian() * _sigma.getParameter() + _mean.getParameter());
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
			return (gaussianDistribution._mean.equals(_mean)) && (gaussianDistribution._sigma.equals(_sigma));
		} else {
			return false;
		}
	}

}
