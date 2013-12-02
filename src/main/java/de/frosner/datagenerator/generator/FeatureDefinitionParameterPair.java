package de.frosner.datagenerator.generator;

import net.sf.qualitycheck.Check;
import de.frosner.datagenerator.distributions.VariableParameter;
import de.frosner.datagenerator.features.FeatureDefinition;

public class FeatureDefinitionParameterPair {

	private final FeatureDefinition _featureDefinition;
	private final VariableParameter<?> _parameter;

	public FeatureDefinitionParameterPair(FeatureDefinition featureDefinition, VariableParameter<?> parameter) {
		_featureDefinition = Check.notNull(featureDefinition);
		_parameter = Check.notNull(parameter);
	}

	public FeatureDefinition getFeatureDefinition() {
		return _featureDefinition;
	}

	public VariableParameter<?> getParameter() {
		return _parameter;
	}

	@Override
	public int hashCode() {
		return _featureDefinition.hashCode();
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof FeatureDefinitionParameterPair) {
			FeatureDefinitionParameterPair pair = (FeatureDefinitionParameterPair) o;
			return pair._featureDefinition.equals(_featureDefinition) && pair._parameter.equals(_parameter);
		}
		return false;
	}

	@Override
	public String toString() {
		return "[" + _featureDefinition.toString() + ", " + _parameter.toString() + "]";
	}

}
