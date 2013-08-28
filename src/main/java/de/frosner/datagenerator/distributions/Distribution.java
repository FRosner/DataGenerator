package de.frosner.datagenerator.distributions;

import javax.annotation.concurrent.Immutable;

import de.frosner.datagenerator.features.FeatureValue;

@Immutable
public interface Distribution {

	public double getProbabilityOf(FeatureValue value);

	public FeatureValue sample();

}
