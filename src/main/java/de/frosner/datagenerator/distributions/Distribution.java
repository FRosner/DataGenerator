package de.frosner.datagenerator.distributions;

import java.util.Collection;

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
	FeatureValue sample();

	/**
	 * Returns a string representation of the distribution type.
	 * 
	 * @return distribution type
	 */
	String getType();

	/**
	 * Returns the parameters necessary for the construction of the given {@linkplain Distribution}.
	 * <p>
	 * The parameters are only for displaying and must not be parsed. They do not reveal the implementation of the given
	 * {@linkplain Distribution}.
	 * 
	 * @return parameters
	 */
	String getParameterDescription();

	/**
	 * Returns all parameters that are dependent ({@linkplain VariableParameter}s) and need to be updated before being
	 * used. The result may not only depend on the concrete implementation but also on the concrete
	 * {@linkplain Distribution} instance.
	 * 
	 * @return dependent parameters
	 */
	Collection<VariableParameter<?>> getDependentParameters();

}
