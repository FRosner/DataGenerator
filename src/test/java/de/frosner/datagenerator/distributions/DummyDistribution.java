package de.frosner.datagenerator.distributions;

import de.frosner.datagenerator.features.DummyFeatureValue;
import de.frosner.datagenerator.features.FeatureValue;

public class DummyDistribution implements Distribution {

	public static final double PROBABILITY_OF_ANY_VALUE = 0;
	public static final FeatureValue ANY_SAMPLE = DummyFeatureValue.INSTANCE;

	@Override
	public double getProbabilityOf(FeatureValue value) {
		return PROBABILITY_OF_ANY_VALUE;
	}

	@Override
	public FeatureValue sample() {
		return ANY_SAMPLE;
	}

}
