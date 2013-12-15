package de.frosner.datagenerator.gui.main;

import net.sf.qualitycheck.Check;
import de.frosner.datagenerator.features.FeatureDefinition;

public class BernoulliFeatureEntry extends FeatureDefinitionEntry {

	public static final String KEY = "Bernoulli";

	private final String _p;

	public BernoulliFeatureEntry(FeatureDefinition featureDefinition, String p) {
		super(featureDefinition);
		_p = Check.notNull(p);
	}

	public String getP() {
		return _p;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof BernoulliFeatureEntry) {
			BernoulliFeatureEntry bernoulli = (BernoulliFeatureEntry) o;
			return (bernoulli._featureDefinition.equals(_featureDefinition) && bernoulli._p.equals(_p));
		} else {
			return false;
		}
	}

}
