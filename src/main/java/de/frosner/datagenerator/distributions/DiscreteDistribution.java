package de.frosner.datagenerator.distributions;

import java.util.Set;

import de.frosner.datagenerator.features.FeatureValue;

public interface DiscreteDistribution extends Distribution {

	/**
	 * Returns the domain of the distribution. That is a set of all {@linkplain FeatureValue}s that could be sampled,
	 * regardless of the concrete parameters.
	 * 
	 * @return set of possible sampled {@linkplain FeatureValue}s
	 */
	Set<FeatureValue> getPossibleValues();

}
