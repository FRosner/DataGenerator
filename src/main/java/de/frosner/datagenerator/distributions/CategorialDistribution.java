package de.frosner.datagenerator.distributions;

import java.util.List;
import java.util.Random;

import javax.annotation.concurrent.Immutable;

import net.sf.qualitycheck.Check;

import org.apache.commons.math3.util.Precision;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import de.frosner.datagenerator.exceptions.IllegalProbabilityArgumentException;
import de.frosner.datagenerator.features.DiscreteFeatureValue;
import de.frosner.datagenerator.features.FeatureValue;
import de.frosner.datagenerator.util.StatisticsUtil;
import de.frosner.datagenerator.util.VisibleForTesting;

@Immutable
public final class CategorialDistribution implements Distribution {

	private static final String TYPE = "Categorial";

	private final List<Double> _probabilities;
	private final List<Double> _cumulativeProbabilities;
	private final Random _random;

	public CategorialDistribution(List<Double> probabilities) {
		Check.stateIsTrue(compare(StatisticsUtil.sum(probabilities), 1.0D) == 0,
				IllegalProbabilityArgumentException.class);
		_probabilities = ImmutableList.copyOf(probabilities);
		_cumulativeProbabilities = Lists.newArrayList();
		for (double probability : _probabilities) {
			if (_cumulativeProbabilities.isEmpty()) {
				_cumulativeProbabilities.add(probability);
			} else {
				_cumulativeProbabilities.add(_cumulativeProbabilities.get(_cumulativeProbabilities.size() - 1)
						+ probability);
			}
		}
		_random = new Random();
	}

	@Override
	public double getProbabilityOf(FeatureValue value) {
		Check.notNull(value);
		Check.instanceOf(DiscreteFeatureValue.class, value);
		int integerValue = (Integer) value.getValue();
		if (integerValue < 0 || integerValue >= _probabilities.size()) {
			return 0.0D;
		}
		return _probabilities.get(integerValue);
	}

	@Override
	public FeatureValue sample() {
		double randomValue = _random.nextDouble();
		int featureValue = 0;
		for (double threshold : _cumulativeProbabilities) {
			if (compare(randomValue, threshold) <= 0) {
				return new DiscreteFeatureValue(featureValue);
			}
			featureValue++;
		}
		return null;
	}

	@Override
	public String getType() {
		return TYPE;
	}

	@Override
	public String getParameterDescription() {
		return "p = " + _probabilities;
	}

	@Override
	public String toString() {
		return CategorialDistribution.class.getSimpleName() + " (" + getParameterDescription() + ")";
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof CategorialDistribution) {
			return ((CategorialDistribution) o)._probabilities.equals(_probabilities);
		} else {
			return false;
		}
	}

	@VisibleForTesting
	void setSeed(long seed) {
		_random.setSeed(seed);
	}

	private static int compare(double a, double b) {
		return Precision.compareTo(a, b, 0.0001);
	}

}
