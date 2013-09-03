package de.frosner.datagenerator.features;

public class DummyFeatureValue implements FeatureValue {

	private final Object _value;

	public DummyFeatureValue(Object value) {
		_value = value;
	}

	@Override
	public Object getValue() {
		return _value;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof DummyFeatureValue) {
			return ((DummyFeatureValue) o).getValue().equals(_value);
		} else {
			return false;
		}
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
