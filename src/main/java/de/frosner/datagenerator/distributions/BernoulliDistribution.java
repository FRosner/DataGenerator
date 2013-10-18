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

	private static final String TYPE = BernoulliDistribution.class.getSimpleName();

	private final double _p;
	private final Random _random;

	/**
	 * Construct a {@linkplain BernoulliDistribution} with the given success probability.
	 * 
	 * @param p
	 *            of success
	 */
	public BernoulliDistribution(double p) {
		Check.stateIsTrue(p >= 0 && p <= 1, IllegalProbabilityArgumentException.class);
		_p = p;
		_random = new Random();
	}

	@Override
	public double getProbabilityOf(FeatureValue value) {
		Check.notNull(value);
		Check.instanceOf(DiscreteFeatureValue.class, value);
		int intValue = (Integer) value.getValue();
		if (intValue == 0) {
			return 1 - _p;
		} else if (intValue == 1) {
			return _p;
		}
		return 0;
	}

	@Override
	public FeatureValue sample() {
		double randomNumber = _random.nextDouble();
		return (Double.compare(randomNumber, _p) < 0) ? new DiscreteFeatureValue(1) : new DiscreteFeatureValue(0);
	}

	@Override
	public String getType() {
		return TYPE;
	}

	@Override
	public String toString() {
		return TYPE + " (p = " + _p + ")";
	}

	@VisibleForTesting
	void setSeed(long seed) {
		_random.setSeed(seed);
	}

}
