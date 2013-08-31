package de.frosner.datagenerator.features;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

import net.sf.qualitycheck.Check;
import de.frosner.datagenerator.distributions.Distribution;

@Immutable
public class FeatureDefinition {

	private final String _name;
	private final Distribution _distribution;

	public FeatureDefinition(@Nonnull String name, @Nonnull Distribution distribution) {
		_name = Check.notNull(name);
		_distribution = Check.notNull(distribution);
	}

	public Distribution getDistribution() {
		return _distribution;
	}

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
