package de.frosner.datagenerator.distributions;

public class FixedParameter<T> extends Parameter<T> {

	private final T _parameter;

	public FixedParameter(T parameter) {
		_parameter = parameter;
	}

	@Override
	public T getParameter() {
		return _parameter;
	}

	@Override
	public String toString() {
		return _parameter.toString();
	}

}
