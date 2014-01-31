package de.frosner.datagenerator.distributions;

import java.util.Random;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

import net.sf.qualitycheck.Check;

import com.google.common.collect.Sets;

import de.frosner.datagenerator.exceptions.IllegalProbabilityArgumentException;
import de.frosner.datagenerator.features.DiscreteFeatureValue;
import de.frosner.datagenerator.features.FeatureValue;
import de.frosner.datagenerator.util.VisibleForTesting;

/**
 * Representation of a Bernoulli distribution. It samples {@linkplain DiscreteFeatureValue}s of 0 or 1 depending on the
 * specified probability of success (value = 1).
 */
@Immutable
public class BernoulliDistribution implements DiscreteDistribution {

	public static final FeatureValue TAILS = new DiscreteFeatureValue(0);
	public static final FeatureValue HEADS = new DiscreteFeatureValue(1);

	private static final String TYPE = "Bernoulli";

	private final Parameter<Double> _p;
	private final Random _random;

	/**
	 * Construct a {@linkplain BernoulliDistribution} with the given success probability.
	 * 
	 * @param p
	 *            of success
	 */
	public BernoulliDistribution(@Nonnull Parameter<Double> p) {
		Check.notNull(p);

		_p = p;
		_random = new Random();
	}

	@Override
	public FeatureValue sample() {
		double pValue = _p.getParameter();
		Check.stateIsTrue(pValue >= 0 && pValue <= 1, IllegalProbabilityArgumentException.class);

		double randomNumber = _random.nextDouble();
		return (Double.compare(randomNumber, pValue) < 0) ? HEADS : TAILS;
	}

	@Override
	public String getType() {
		return TYPE;
	}

	@Override
	public String getParameterDescription() {
		return "p = " + _p.getParameter();
	}

	@Override
	public Set<FeatureValue> getPossibleValues() {
		return Sets.newHashSet(TAILS, HEADS);
	}

	@Override
	public String toString() {
		return BernoulliDistribution.class.getSimpleName() + " (" + getParameterDescription() + ")";
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof BernoulliDistribution) {
			return ((BernoulliDistribution) o)._p.equals(_p);
		} else {
			return false;
		}

	}

	@VisibleForTesting
	void setSeed(long seed) {
		_random.setSeed(seed);
	}

}
