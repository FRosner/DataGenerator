package de.frosner.datagenerator.distributions;

import de.frosner.datagenerator.features.FeatureValue;

public interface Distribution {

	public double getProbabilityOf(FeatureValue value);

	public FeatureValue sample();

}
