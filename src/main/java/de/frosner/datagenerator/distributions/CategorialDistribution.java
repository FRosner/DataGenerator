package de.frosner.datagenerator.distributions;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

import net.sf.qualitycheck.Check;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import de.frosner.datagenerator.features.DiscreteFeatureValue;
import de.frosner.datagenerator.features.FeatureValue;
import de.frosner.datagenerator.util.StatisticsUtil;
import de.frosner.datagenerator.util.VisibleForTesting;

@Immutable
public final class CategorialDistribution implements DiscreteDistribution {

	private static final String TYPE = "Categorial";

	private final Parameter<List<Double>> _probabilities;
	private final Random _random;

	public CategorialDistribution(@Nonnull Parameter<List<Double>> probabilities) {
		Check.notNull(probabilities, "probabilities");

		_probabilities = probabilities;
		_random = new Random();
	}

	@Override
	public FeatureValue sample() {
		double randomValue = _random.nextDouble();
		int featureValue = 0;
		List<Double> cumulativeProbabilities = StatisticsUtil.cumulateProbabilities(_probabilities.getParameter());
		for (double threshold : cumulativeProbabilities) {
			if (randomValue <= threshold) {
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
	public Set<FeatureValue> getPossibleValues() {
		Set<FeatureValue> possibleValues = Sets.newHashSet();
		for (int i = 0; i < _probabilities.getParameter().size(); i++) {
			possibleValues.add(new DiscreteFeatureValue(i));
		}
		return possibleValues;
	}

	@Override
	public Collection<VariableParameter<?>> getDependentParameters() {
		if (_probabilities instanceof VariableParameter<?>) {
			Collection<VariableParameter<?>> result = Lists.newArrayList();
			result.add((VariableParameter<?>) _probabilities);
			return result;
		}
		return Collections.emptyList();
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

}
