package de.frosner.datagenerator.distributions;

import javax.annotation.concurrent.Immutable;

import de.frosner.datagenerator.features.FeatureValue;

/**
 * Immutable representation of a probability distribution. Distributions can sample {@link FeatureValue}s and return the
 * probability / mass function value of a given {@link FeatureValue}.
 */
@Immutable
public interface Distribution {

	/**
	 * Returns the probability (or mass function value) of the specified discrete (or continuous) {@link FeatureValue}.
	 * 
	 * @param value
	 *            the probability should be returned of
	 * @return probability (mass function value) of the value
	 */
	public double getProbabilityOf(FeatureValue value);

	/**
	 * Returns a random sample.
	 * 
	 * @return random feature value
	 */
	public FeatureValue sample();

	/**
	 * Returns a {@link String} representation of the distribution type.
	 * 
	 * @return distribution type
	 */
	public String getType();

}
