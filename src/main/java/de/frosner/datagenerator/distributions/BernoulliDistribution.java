package de.frosner.datagenerator.distributions;

import java.util.Random;

import javax.annotation.concurrent.Immutable;

import net.sf.qualitycheck.Check;
import de.frosner.datagenerator.exceptions.IllegalProbabilityArgumentException;
import de.frosner.datagenerator.features.DiscreteFeatureValue;
import de.frosner.datagenerator.features.FeatureValue;
import de.frosner.datagenerator.util.VisibleForTesting;

/**
 * Representation of a Bernoulli distribution. It samples {@linkplain DiscreteFeatureValue}s of 0 or 1 depending on the
 * specified probability of success (value = 1).
 */
@Immutable
public class BernoulliDistribution implements Distribution {

	private static final String TYPE = "Bernoulli";

	private final Parameter<Double> _p;
	private final Random _random;

	/**
	 * Construct a {@linkplain BernoulliDistribution} with the given success probability.
	 * 
	 * @param p
	 *            of success
	 */
	public BernoulliDistribution(Parameter<Double> p) {
		Check.stateIsTrue(p.getParameter() >= 0 && p.getParameter() <= 1, IllegalProbabilityArgumentException.class);
		_p = p;
		_random = new Random();
	}

	@Override
	public FeatureValue sample() {
		double randomNumber = _random.nextDouble();
		return (Double.compare(randomNumber, _p.getParameter()) < 0) ? new DiscreteFeatureValue(1)
				: new DiscreteFeatureValue(0);
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
