package de.frosner.datagenerator.distributions;

import de.frosner.datagenerator.features.FeatureDefinition;
import de.frosner.datagenerator.features.FeatureValue;

public class VariableDummyParameter extends VariableParameter<Object> {

	private static final FeatureDefinition DUMMY_FEATURE_DEFINITION = new FeatureDefinition(
			VariableDummyParameter.class.getSimpleName(), new DummyDistribution());

	public VariableDummyParameter() {
		super(DUMMY_FEATURE_DEFINITION);
	}

	public VariableDummyParameter(FeatureDefinition featureDefinition) {
		super(featureDefinition);
	}

	@Override
	public void updateParameter(FeatureValue value) {
		_parameter = value.getValue();
	}

	@Override
	public boolean equals(Object o) {
		// Avoid comparison of unset parameters in tests
		if (o instanceof VariableDummyParameter) {
			return o == this;
		}
		return false;
	}

}
