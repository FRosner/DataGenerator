package de.frosner.datagenerator.distributions;

import de.frosner.datagenerator.exceptions.VariableParameterNotSetException;
import de.frosner.datagenerator.features.FeatureValue;

public abstract class VariableParameter<T> extends Parameter<T> {

	public static final String KEY = "Conditioned";

	protected T _parameter;

	public abstract void updateParameter(FeatureValue value);

	@Override
	public T getParameter() {
		if (_parameter == null) {
			throw new VariableParameterNotSetException();
		}
		T parameter = _parameter;
		_parameter = null;
		return parameter;
	}

}
