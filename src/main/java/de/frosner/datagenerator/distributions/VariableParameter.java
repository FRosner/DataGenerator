package de.frosner.datagenerator.distributions;

import de.frosner.datagenerator.exceptions.VariableParameterNotSetException;

public abstract class VariableParameter<T> extends Parameter<T> {

	protected T _parameter;

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
