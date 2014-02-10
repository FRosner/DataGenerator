package de.frosner.datagenerator.distributions;

import java.util.Map;

import javax.annotation.Nonnull;

import net.sf.qualitycheck.Check;

import com.google.common.collect.Maps;

import de.frosner.datagenerator.exceptions.FeatureValueCannotBeMappedException;
import de.frosner.datagenerator.features.DiscreteFeatureValue;
import de.frosner.datagenerator.features.FeatureValue;

public class DiscreteVariableParameter<T> extends VariableParameter<T> {

	private final Map<DiscreteFeatureValue, T> _featureValueParameterMapping;

	/**
	 * Constructs a new {@linkplain DiscreteVariableParameter} with the specified probabilities.
	 * 
	 * @param featureValueParameterMapping
	 *            defining the resulting parameter depending on the given {@linkplain DiscreteFeatureValue}
	 */
	public DiscreteVariableParameter(@Nonnull Map<DiscreteFeatureValue, T> featureValueParameterMapping) {
		_featureValueParameterMapping = Maps.newHashMap(Check.notNull(featureValueParameterMapping));
	}

	@Override
	public void updateParameter(FeatureValue value) {
		Check.instanceOf(DiscreteFeatureValue.class, value, "value");
		if (!_featureValueParameterMapping.containsKey(value)) {
			throw new FeatureValueCannotBeMappedException(value);
		}

		_parameter = _featureValueParameterMapping.get(value);
	}

}
