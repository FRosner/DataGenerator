package de.frosner.datagenerator.distributions;

import de.frosner.datagenerator.features.DummyFeatureValue;
import de.frosner.datagenerator.features.FeatureValue;

public class DummyDistribution implements Distribution {

	private final String _type = DummyDistribution.class.getSimpleName();
	public static final double PROBABILITY_OF_ANY_VALUE = 0;
	public static final FeatureValue ANY_SAMPLE = new DummyFeatureValue(new Object());

	@Override
	public FeatureValue sample() {
		return ANY_SAMPLE;
	}

	@Override
	public String getType() {
		return _type;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof DummyDistribution) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public String toString() {
		return _type;
	}

	@Override
	public String getParameterDescription() {
		return "";
	}

}
