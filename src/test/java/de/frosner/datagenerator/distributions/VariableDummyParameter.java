package de.frosner.datagenerator.distributions;

import de.frosner.datagenerator.features.FeatureValue;

public class VariableDummyParameter extends VariableParameter<FeatureValue> {

	@Override
	public void updateParameter(FeatureValue value) {
		_parameter = value;
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
