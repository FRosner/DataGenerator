package de.frosner.datagenerator.distributions;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import de.frosner.datagenerator.features.DummyFeatureValue;
import de.frosner.datagenerator.features.FeatureValue;
import de.frosner.datagenerator.util.GsonSerializable;

@GsonSerializable
public class DummyDistribution implements Distribution {

	@Expose
	@SerializedName("type")
	private final String _type = DummyDistribution.class.getSimpleName();
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

}
