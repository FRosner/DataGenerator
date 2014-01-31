package de.frosner.datagenerator.distributions;

import de.frosner.datagenerator.features.DiscreteFeatureValue;

public interface ContinuousDistribution extends Distribution {

	/**
	 * Returns the interval of possible {@linkplain DiscreteFeatureValue}s sampled by the distribution, regardless of
	 * the concrete parameter values.
	 * 
	 * @return interval of possible {@linkplain DiscreteFeatureValue}s sampled.
	 */
	Interval getPossibleValueInterval();

}
