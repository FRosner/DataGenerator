package de.frosner.datagenerator.generator;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nonnull;

import net.sf.qualitycheck.Check;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import de.frosner.datagenerator.distributions.Parameter;
import de.frosner.datagenerator.exceptions.CircularDependencyException;
import de.frosner.datagenerator.features.FeatureDefinition;
import de.frosner.datagenerator.util.VisibleForTesting;

public class FeatureDefinitionGraph implements Iterable<FeatureDefinition> {

	@VisibleForTesting
	final Map<FeatureDefinition, Set<FeatureDefinitionParameterPair>> _adjacentNodes = Maps.newHashMap();
	@VisibleForTesting
	final List<FeatureDefinition> _insertionOrder = Lists.newArrayList();

	public boolean addFeatureDefinition(FeatureDefinition featureDefinition) {
		Check.notNull(featureDefinition, "featureDefinition");
		if (_adjacentNodes.containsKey(featureDefinition)) {
			return false;
		}
		_adjacentNodes.put(featureDefinition, new HashSet<FeatureDefinitionParameterPair>());
		_insertionOrder.add(featureDefinition);
		return true;
	}

	private void removeFeatureDefinition(FeatureDefinition featureDefinition) {
		_adjacentNodes.remove(featureDefinition);
		_insertionOrder.remove(featureDefinition);
	}

	public boolean addDependency(@Nonnull FeatureDefinition parentFeature, @Nonnull FeatureDefinition childFeature,
			@Nonnull Parameter childParameter) {
		Check.notNull(parentFeature, "parentFeature");
		Check.notNull(childFeature, "childFeature");
		Check.notNull(childParameter, "childParameter");
		Check.stateIsTrue(_adjacentNodes.containsKey(parentFeature), "parent feature must be part of the graph");
		Set<FeatureDefinitionParameterPair> children = _adjacentNodes.get(parentFeature);
		FeatureDefinitionParameterPair pair = new FeatureDefinitionParameterPair(childFeature, childParameter);
		if (children.add(pair)) {
			addFeatureDefinition(childFeature);
			if (!containsPath(childFeature, parentFeature)) {
				return true;
			} else {
				children.remove(pair);
				removeFeatureDefinition(childFeature);
				throw new CircularDependencyException();
			}
		} else {
			return false;
		}
	}

	public boolean isEmpty() {
		return _adjacentNodes.keySet().isEmpty();
	}

	public boolean isLeaf(@Nonnull FeatureDefinition definition) {
		Check.notNull(definition);
		if (!_adjacentNodes.containsKey(definition)) {
			return false;
		}
		return _adjacentNodes.get(definition).isEmpty();
	}

	public List<Parameter> getDependentParameters(@Nonnull FeatureDefinition definition) {
		Check.notNull(definition);
		List<Parameter> dependentParameters = Lists.newArrayList();
		for (FeatureDefinitionParameterPair pair : _adjacentNodes.get(definition)) {
			dependentParameters.add(pair.getParameter());
		}
		return dependentParameters;
	}

	@Override
	public Iterator<FeatureDefinition> iterator() {
		return _insertionOrder.iterator();
	}

	private boolean containsPath(FeatureDefinition start, FeatureDefinition goal) {
		if (start.equals(goal)) {
			return true;
		}
		for (FeatureDefinitionParameterPair pair : _adjacentNodes.get(start)) {
			if (containsPath(pair.getFeatureDefinition(), goal)) {
				return true;
			}
		}
		return false;
	}

}
