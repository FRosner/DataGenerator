package de.frosner.datagenerator.distributions;

import java.util.Map;

import net.sf.qualitycheck.Check;

import com.google.common.collect.Maps;

import de.frosner.datagenerator.exceptions.FeatureValueCannotBeMappedException;
import de.frosner.datagenerator.features.DiscreteFeatureValue;
import de.frosner.datagenerator.features.FeatureValue;

public class DiscreteVariableParameter<T> extends VariableParameter<T> {

	private final Map<DiscreteFeatureValue, T> _parameterSelection;

	public DiscreteVariableParameter(Map<DiscreteFeatureValue, T> parameterSelection) {
		Check.notNull(parameterSelection);
		_parameterSelection = Maps.newHashMap(parameterSelection);
	}

	@Override
	public void updateParameter(FeatureValue value) {
		Check.instanceOf(DiscreteFeatureValue.class, value, "value");
		if (!_parameterSelection.containsKey(value)) {
			throw new FeatureValueCannotBeMappedException(value);
		}

		_parameter = _parameterSelection.get(value);
	}

}
