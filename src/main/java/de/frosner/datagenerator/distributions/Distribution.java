package de.frosner.datagenerator.distributions;

import javax.annotation.concurrent.Immutable;

import de.frosner.datagenerator.features.FeatureValue;
import de.frosner.datagenerator.util.GsonSerializable;

@Immutable
@GsonSerializable
public interface Distribution {

	public double getProbabilityOf(FeatureValue value);

	public FeatureValue sample();

	public String getType();

}
