package de.frosner.datagenerator.gui.main;

import javax.annotation.concurrent.Immutable;

import de.frosner.datagenerator.features.FeatureDefinition;

@Immutable
public abstract class FeatureDefinitionEntry {

	protected final FeatureDefinition _featureDefinition;

	protected FeatureDefinitionEntry(FeatureDefinition featureDefinition) {
		_featureDefinition = featureDefinition;
	}

	public String getFeatureName() {
		return _featureDefinition.getName();
	}

	public FeatureDefinition getFeatureDefinition() {
		return _featureDefinition;
	}

	@Override
	public String toString() {
		return _featureDefinition.getName() + " (" + _featureDefinition.getDistribution().getType() + ", "
				+ _featureDefinition.getDistribution().getParameterDescription() + ")";
	}

}
