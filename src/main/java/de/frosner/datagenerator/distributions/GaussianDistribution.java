package de.frosner.datagenerator.distributions;

import java.util.Collection;
import java.util.Random;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

import net.sf.qualitycheck.Check;

import com.google.common.collect.Lists;

import de.frosner.datagenerator.exceptions.IllegalSigmaParameterArgumentException;
import de.frosner.datagenerator.features.ContinuousFeatureValue;
import de.frosner.datagenerator.features.FeatureValue;
import de.frosner.datagenerator.util.VisibleForTesting;

/**
 * Representation of a Gaussian distribution. It samples {@linkplain ContinuousFeatureValue}s around the given mean with
 * the specified standard deviation.
 */
@Immutable
public final class GaussianDistribution implements ContinuousDistribution {

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
	public GaussianDistribution(@Nonnull Parameter<Double> mean, @Nonnull Parameter<Double> sigma) {
		_mean = Check.notNull(mean, "mean");
		_sigma = Check.notNull(sigma, "sigma");
		_generator = new Random();
	}

	@VisibleForTesting
	void setSeed(long seed) {
		_generator.setSeed(seed);
	}

	@Override
	public FeatureValue sample() {
		double mean = _mean.getParameter();
		double sigma = _sigma.getParameter();
		Check.stateIsTrue(sigma > 0, IllegalSigmaParameterArgumentException.class);

		return new ContinuousFeatureValue(_generator.nextGaussian() * sigma + mean);
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
	public Interval getPossibleValueInterval() {
		return Interval.UNBOUNDED;
	}

	@Override
	public Collection<VariableParameter<?>> getDependentParameters() {
		Collection<VariableParameter<?>> result = Lists.newArrayList();
		if (_mean instanceof VariableParameter<?>) {
			result.add((VariableParameter<?>) _mean);
		}
		if (_sigma instanceof VariableParameter<?>) {
			result.add((VariableParameter<?>) _sigma);
		}
		return result;
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
