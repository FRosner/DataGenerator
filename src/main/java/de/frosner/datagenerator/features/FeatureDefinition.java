package de.frosner.datagenerator.features;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

import net.sf.qualitycheck.Check;
import de.frosner.datagenerator.distributions.Distribution;

@Immutable
public class FeatureDefinition {

	private final Distribution _distribution;
	private final String _name;

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

}
