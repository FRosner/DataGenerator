package de.frosner.datagenerator.gui.main;

import de.frosner.datagenerator.features.FeatureDefinition;

public class DummyFeatureDefinitionEntry extends FeatureDefinitionEntry {

	public DummyFeatureDefinitionEntry(FeatureDefinition featureDefinition) {
		super(featureDefinition);
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof DummyFeatureDefinitionEntry) {
			return ((DummyFeatureDefinitionEntry) o)._featureDefinition.equals(_featureDefinition);
		}
		return false;
	}
}
