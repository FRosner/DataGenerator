package de.frosner.datagenerator.features;

import javax.annotation.concurrent.Immutable;

/**
 * Wrapper for a feature value. It may contain a value of any type.
 */
@Immutable
public interface FeatureValue {

	/**
	 * Returns the wrapped value.
	 * 
	 * @return wrapped value
	 */
	public Object getValue();

	/**
	 * Returns a string representation of the wrapped value.
	 * 
	 * @return string representation of wrapped value
	 */
	public String getValueAsString();

}
