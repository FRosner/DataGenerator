package de.frosner.datagenerator.distributions;

import de.frosner.datagenerator.features.FeatureValue;

public abstract class Parameter<T> {

	public abstract T getParameter();

	public abstract void updateParameter(FeatureValue value);

	@Override
	public boolean equals(Object o) {
		if (o instanceof Parameter) {
			return ((Parameter<?>) o).getParameter().equals(getParameter());
		}
		return false;
	}

}
