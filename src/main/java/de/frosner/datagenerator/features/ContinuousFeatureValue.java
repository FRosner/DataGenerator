package de.frosner.datagenerator.features;

import javax.annotation.concurrent.Immutable;

/**
 * Representation of a continuous feature / variable.
 */
@Immutable
public final class ContinuousFeatureValue implements FeatureValue, Comparable<ContinuousFeatureValue> {

	private final double _value;

	/**
	 * Constructs a {@linkplain ContinuousFeatureValue} containing the specified floating point number.
	 * 
	 * @param value
	 *            of the continuous feature
	 */
	public ContinuousFeatureValue(double value) {
		_value = value;
	}

	@Override
	public Object getValue() {
		return _value;
	}

	public double getDoubleValue() {
		return _value;
	}

	@Override
	public String getValueAsString() {
		return Double.toString(_value);
	}

	@Override
	public String toString() {
		return Double.toString(_value);
	}

	@Override
	public int hashCode() {
		return new Double(_value).hashCode();
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof ContinuousFeatureValue) {
			return Double.compare(((ContinuousFeatureValue) o).getDoubleValue(), getDoubleValue()) == 0;
		} else {
			return false;
		}
	}

	@Override
	public int compareTo(ContinuousFeatureValue value) {
		return Double.compare(_value, value._value);
	}

}
