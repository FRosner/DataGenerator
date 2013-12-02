package de.frosner.datagenerator.distributions;

import net.sf.qualitycheck.Check;
import de.frosner.datagenerator.features.ContinuousFeatureValue;
import de.frosner.datagenerator.features.FeatureValue;

public class ContinuousVariableParameter extends VariableParameter<Double> {

	@Override
	public void updateParameter(FeatureValue value) {
		Check.instanceOf(ContinuousFeatureValue.class, value, "value");
		_parameter = ((ContinuousFeatureValue) value).getDoubleValue();
	}

}
