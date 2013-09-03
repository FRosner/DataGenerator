package de.frosner.datagenerator.features;

public class DummyFeatureValue implements FeatureValue {

	public static DummyFeatureValue INSTANCE = new DummyFeatureValue();

	private DummyFeatureValue() {

	}

	@Override
	public Object getValue() {
		return DummyFeatureValue.class.getSimpleName();
	}

	@Override
	public boolean equals(Object o) {
		return (o == this);
	}

	@Override
	public String toString() {
		return getValue().toString();
	}

	@Override
	public String getValueAsString() {
		return getValue().toString();
	}

}
