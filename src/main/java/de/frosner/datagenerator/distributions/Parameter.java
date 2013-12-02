package de.frosner.datagenerator.distributions;


public abstract class Parameter<T> {

	public abstract T getParameter();

	@Override
	public boolean equals(Object o) {
		if (o instanceof Parameter) {
			return ((Parameter<?>) o).getParameter().equals(getParameter());
		}
		return false;
	}

}
