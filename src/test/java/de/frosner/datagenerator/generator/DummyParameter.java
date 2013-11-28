package de.frosner.datagenerator.generator;

import de.frosner.datagenerator.distributions.Parameter;

public class DummyParameter extends Parameter {

	private double _parameterValue;

	public DummyParameter(double parameterValue) {
		_parameterValue = parameterValue;
	}

	@Override
	public double getParameter() {
		return _parameterValue;
	}

	@Override
	public void setParameter(double parameter) {
		_parameterValue = parameter;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof DummyParameter) {
			return ((DummyParameter) o)._parameterValue == _parameterValue;
		}
		return false;
	}

	@Override
	public String toString() {
		return Double.toString(_parameterValue);
	}

}
