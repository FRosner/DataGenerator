package de.frosner.datagenerator.distributions;

import javax.annotation.Nonnull;

import net.sf.qualitycheck.Check;
import de.frosner.datagenerator.exceptions.VariableParameterNotSetException;
import de.frosner.datagenerator.features.FeatureDefinition;
import de.frosner.datagenerator.features.FeatureValue;

public abstract class VariableParameter<T> extends Parameter<T> {

	public static final String KEY = "Conditioned";

	protected T _parameter;
	private FeatureDefinition _featureDefinitionConditionedOn;

	/**
	 * Constructs a {@linkplain VariableParameter} conditioned on the specified {@linkplain FeatureDefinition}.
	 * 
	 * @param featureDefinition
	 *            the parameter is conditioned on
	 */
	protected VariableParameter(@Nonnull FeatureDefinition featureDefinition) {
		_featureDefinitionConditionedOn = Check.notNull(featureDefinition);
	}

	public abstract void updateParameter(FeatureValue value);

	/**
	 * @return the {@linkplain FeatureDefinition} the parameter belongs to.
	 */
	public FeatureDefinition getFeatureDefinitionConditionedOn() {
		return _featureDefinitionConditionedOn;
	}

	@Override
	public T getParameter() {
		if (_parameter == null) {
			throw new VariableParameterNotSetException();
		}
		T parameter = _parameter;
		_parameter = null;
		return parameter;
	}

}
