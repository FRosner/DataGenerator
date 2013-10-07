package de.frosner.datagenerator.features;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

import net.sf.qualitycheck.Check;
import de.frosner.datagenerator.distributions.Distribution;
import de.frosner.datagenerator.generator.DataGenerator;

/**
 * This class contains all information about a feature, including the feature name and the underlying probability
 * distribution. The {@linkplain DataGenerator} uses feature definitions to sample individual {@linkplain FeatureValue}s
 * for each {@linkplain Instance} generated.
 */
@Immutable
public final class FeatureDefinition {

	private final String _name;
	private final Distribution _distribution;

	/**
	 * Creates a new {@linkplain FeatureDefinition} with the specified name and distribution.
	 * 
	 * @param name
	 *            of the feature
	 * @param distribution
	 *            of the underlying feature values
	 */
	public FeatureDefinition(@Nonnull String name, @Nonnull Distribution distribution) {
		_name = Check.notNull(name);
		_distribution = Check.notNull(distribution);
	}

	/**
	 * Returns the underlying probability distribution.
	 * 
	 * @return probability distribution for the feature values to be generated
	 */
	public Distribution getDistribution() {
		return _distribution;
	}

	/**
	 * Returns the name of the feature.
	 * 
	 * @return feature name
	 */
	public String getName() {
		return _name;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof FeatureDefinition) {
			FeatureDefinition featureDefinition = (FeatureDefinition) o;
			return (featureDefinition._name.equals(_name) && featureDefinition._distribution.equals(_distribution));
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return _name.hashCode();
	}

	@Override
	public String toString() {
		return "[" + _name + ", " + _distribution + "]";
	}

}
