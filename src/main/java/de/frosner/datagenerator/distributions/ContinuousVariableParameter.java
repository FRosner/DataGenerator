package de.frosner.datagenerator.distributions;

import javax.annotation.Nonnull;

import net.sf.qualitycheck.Check;
import de.frosner.datagenerator.features.ContinuousFeatureValue;
import de.frosner.datagenerator.features.FeatureDefinition;
import de.frosner.datagenerator.features.FeatureValue;

public class ContinuousVariableParameter extends VariableParameter<Double> {

	/**
	 * Constructs a new {@linkplain ContinuousVariableParameter} conditioned on the specified
	 * {@linkplain FeatureDefinition}.
	 * 
	 * @param featureDefinition
	 *            this parameter is conditioned on
	 */
	public ContinuousVariableParameter(@Nonnull FeatureDefinition featureDefinition) {
		super(featureDefinition);
	}

	@Override
	public void updateParameter(FeatureValue value) {
		Check.instanceOf(ContinuousFeatureValue.class, value, "value");
		_parameter = ((ContinuousFeatureValue) value).getDoubleValue();
	}

}
