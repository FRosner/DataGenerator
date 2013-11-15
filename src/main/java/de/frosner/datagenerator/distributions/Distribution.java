package de.frosner.datagenerator.distributions;

import javax.annotation.concurrent.Immutable;

import de.frosner.datagenerator.features.FeatureValue;

/**
 * Immutable representation of a probability distribution. Distributions can sample {@linkplain FeatureValue}s or return
 * the probability / mass function value of a given {@linkplain FeatureValue}.
 */
@Immutable
public interface Distribution {

	/**
	 * Returns a random sample.
	 * 
	 * @return random feature value
	 */
	public FeatureValue sample();

	/**
	 * Returns a string representation of the distribution type.
	 * 
	 * @return distribution type
	 */
	public String getType();

	/**
	 * Returns the parameters neccessary for the construction of the given {@linkplain Distribution}.
	 * <p>
	 * The parameters are only for displaying and must not be parsed. They do not reveal the implementation of the given
	 * {@linkplain Distribution}.
	 * 
	 * @return parameters
	 */
	public String getParameterDescription();

}
