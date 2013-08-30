package de.frosner.datagenerator.features;

import javax.annotation.concurrent.Immutable;

/**
 * Wrapper for a feature value. It may contain a value of any type.
 */
@Immutable
public interface FeatureValue {

	/**
	 * Returns the wrapped value. Call {@link Object#toString()} in order to get an exportable {@link String}
	 * representation of the value.
	 * 
	 * @return
	 */
	public Object getValue();

}
