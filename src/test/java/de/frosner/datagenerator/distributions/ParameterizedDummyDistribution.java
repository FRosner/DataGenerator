package de.frosner.datagenerator.distributions;

import java.util.Collection;
import java.util.Collections;

import com.google.common.collect.Lists;

import de.frosner.datagenerator.features.DummyFeatureValue;
import de.frosner.datagenerator.features.FeatureValue;

public class ParameterizedDummyDistribution implements Distribution {

	private final Parameter<?> _parameter;

	public ParameterizedDummyDistribution(Parameter<?> parameter) {
		_parameter = parameter;
	}

	@Override
	public FeatureValue sample() {
		return new DummyFeatureValue(_parameter.getParameter());
	}

	@Override
	public String getType() {
		return ParameterizedDummyDistribution.class.getSimpleName();
	}

	@Override
	public String getParameterDescription() {
		return null;
	}

	@Override
	public Collection<VariableParameter<?>> getDependentParameters() {
		if (_parameter instanceof VariableParameter<?>) {
			Collection<VariableParameter<?>> result = Lists.newArrayList();
			result.add((VariableParameter<?>) _parameter);
			return result;
		}
		return Collections.emptyList();
	}

}
