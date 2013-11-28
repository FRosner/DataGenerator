package de.frosner.datagenerator.distributions;

public abstract class Parameter {

	public abstract double getParameter();

	public abstract void setParameter(double parameter);

	@Override
	public boolean equals(Object o) {
		if (o instanceof Parameter) {
			return Double.compare(((Parameter) o).getParameter(), getParameter()) == 0;
		}
		return false;
	}

}
