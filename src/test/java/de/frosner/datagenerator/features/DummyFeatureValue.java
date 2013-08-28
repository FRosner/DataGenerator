package de.frosner.datagenerator.features;

public class DummyFeatureValue implements FeatureValue {

	public static DummyFeatureValue INSTANCE = new DummyFeatureValue();

	private DummyFeatureValue() {

	}

	@Override
	public Object getValue() {
		return null;
	}

	@Override
	public boolean equals(Object o) {
		return (o == this);
	}

}
