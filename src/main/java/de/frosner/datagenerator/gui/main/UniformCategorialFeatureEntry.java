package de.frosner.datagenerator.gui.main;

import net.sf.qualitycheck.Check;
import de.frosner.datagenerator.features.FeatureDefinition;

public class UniformCategorialFeatureEntry extends FeatureListEntry {

	public static final String KEY = "Uniform Categorial";

	private final String _numberOfStates;

	public UniformCategorialFeatureEntry(FeatureDefinition featureDefinition, String numberOfStates) {
		super(featureDefinition);
		_numberOfStates = Check.notNull(numberOfStates);
	}

	public String getNumberOfStates() {
		return _numberOfStates;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof UniformCategorialFeatureEntry) {
			UniformCategorialFeatureEntry uniformCategorial = (UniformCategorialFeatureEntry) o;
			return (uniformCategorial._featureDefinition.equals(_featureDefinition) && uniformCategorial._numberOfStates
					.equals(_numberOfStates));
		} else {
			return false;
		}
	}

}
