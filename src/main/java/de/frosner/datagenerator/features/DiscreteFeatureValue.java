package de.frosner.datagenerator.features;

import javax.annotation.concurrent.Immutable;

/**
 * Representation of a discrete feature / variable.
 */
@Immutable
public final class DiscreteFeatureValue implements FeatureValue {

	private final int _value;

	/**
	 * Constructs a {@linkplain DiscreteFeatureValue} containing the specified integer number.
	 * 
	 * @param value
	 *            of the discrete feature
	 */
	public DiscreteFeatureValue(int value) {
		_value = value;
	}

	@Override
	public Object getValue() {
		return _value;
	}

	@Override
	public String getValueAsString() {
		return Integer.toString(_value);
	}

	@Override
	public String toString() {
		return Integer.toString(_value);
	}

	@Override
	public int hashCode() {
		return new Integer(_value).hashCode();
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof DiscreteFeatureValue) {
			return ((DiscreteFeatureValue) o).getValue().equals(_value);
		} else {
			return false;
		}
	}

}
