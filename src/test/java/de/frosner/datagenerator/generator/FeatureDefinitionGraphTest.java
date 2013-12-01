package de.frosner.datagenerator.generator;

import static org.fest.assertions.Assertions.assertThat;

import java.util.Iterator;

import net.sf.qualitycheck.exception.IllegalNullArgumentException;

import org.junit.Before;
import org.junit.Test;

import de.frosner.datagenerator.distributions.DummyDistribution;
import de.frosner.datagenerator.distributions.Parameter;
import de.frosner.datagenerator.exceptions.CircularDependencyException;
import de.frosner.datagenerator.features.FeatureDefinition;

public class FeatureDefinitionGraphTest {

	private FeatureDefinitionGraph _graph;

	@Before
	public void createGraph() {
		_graph = new FeatureDefinitionGraph();
	}

	@Test
	public void testAddFeatureDefinition() {
		FeatureDefinition feature1 = new FeatureDefinition("feature1", new DummyDistribution());
		assertThat(_graph.addFeatureDefinition(feature1)).isTrue();
		assertThat(_graph._adjacentNodes.keySet()).containsOnly(feature1);
		assertThat(_graph._insertionOrder).containsExactly(feature1);

		FeatureDefinition feature2 = new FeatureDefinition("feature2", new DummyDistribution());
		assertThat(_graph.addFeatureDefinition(feature2)).isTrue();
		assertThat(_graph._adjacentNodes.keySet()).containsOnly(feature1, feature2);
		assertThat(_graph._insertionOrder).containsExactly(feature1, feature2);
	}

	@Test
	public void testAddFeatureDefinition_sameFeatureTwice() {
		FeatureDefinition feature = new FeatureDefinition("feature", new DummyDistribution());
		_graph.addFeatureDefinition(feature);
		assertThat(_graph.addFeatureDefinition(feature)).isFalse();
		assertThat(_graph._adjacentNodes.keySet()).containsOnly(feature);
		assertThat(_graph._insertionOrder).containsExactly(feature);
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void testAddFeatureDefinition_null() {
		_graph.addFeatureDefinition(null);
	}

	@Test
	public void testAddDependency() {
		FeatureDefinition feature1 = new FeatureDefinition("feature1", new DummyDistribution());
		FeatureDefinition feature1_1 = new FeatureDefinition("feature1_1", new DummyDistribution());
		Parameter parameter1_1 = new DummyParameter(0);

		_graph.addFeatureDefinition(feature1);
		assertThat(_graph.addDependency(feature1, feature1_1, parameter1_1)).isTrue();

		assertThat(_graph._adjacentNodes.get(feature1)).containsOnly(
				new FeatureDefinitionParameterPair(feature1_1, parameter1_1));
		assertThat(_graph._insertionOrder).containsExactly(feature1, feature1_1);
	}

	@Test
	public void testAddDependency_toDependentFeature() {
		FeatureDefinition feature1 = new FeatureDefinition("feature1", new DummyDistribution());
		FeatureDefinition feature1_1 = new FeatureDefinition("feature1_1", new DummyDistribution());
		Parameter parameter1_1 = new DummyParameter(0);
		FeatureDefinition feature1_1_1 = new FeatureDefinition("feature1_1_1", new DummyDistribution());
		Parameter parameter1_1_1 = new DummyParameter(0);

		_graph.addFeatureDefinition(feature1);
		_graph.addDependency(feature1, feature1_1, parameter1_1);
		_graph.addDependency(feature1_1, feature1_1_1, parameter1_1_1);

		assertThat(_graph._adjacentNodes.get(feature1)).containsOnly(
				new FeatureDefinitionParameterPair(feature1_1, parameter1_1));
		assertThat(_graph._adjacentNodes.get(feature1_1)).containsOnly(
				new FeatureDefinitionParameterPair(feature1_1_1, parameter1_1_1));
		assertThat(_graph._insertionOrder).containsExactly(feature1, feature1_1, feature1_1_1);
	}

	@Test
	public void testAddDependency_sameDependencyTwice() {
		FeatureDefinition feature1 = new FeatureDefinition("feature1", new DummyDistribution());
		FeatureDefinition feature1_1 = new FeatureDefinition("feature1_1", new DummyDistribution());
		Parameter parameter1_1 = new DummyParameter(0);

		_graph.addFeatureDefinition(feature1);
		_graph.addDependency(feature1, feature1_1, parameter1_1);
		assertThat(_graph.addDependency(feature1, feature1_1, parameter1_1)).isFalse();

		assertThat(_graph._adjacentNodes.get(feature1)).containsOnly(
				new FeatureDefinitionParameterPair(feature1_1, parameter1_1));
		assertThat(_graph._insertionOrder).containsExactly(feature1, feature1_1);
	}

	@Test(expected = CircularDependencyException.class)
	public void testAddDependency_circularDependencyByEdge() {
		FeatureDefinition feature1 = new FeatureDefinition("feature1", new DummyDistribution());
		Parameter parameter1 = new DummyParameter(0);
		FeatureDefinition feature2 = new FeatureDefinition("feature2", new DummyDistribution());
		Parameter parameter2 = new DummyParameter(0);

		_graph.addFeatureDefinition(feature1);
		_graph.addDependency(feature1, feature2, parameter2);
		_graph.addDependency(feature2, feature1, parameter1);
	}

	@Test(expected = CircularDependencyException.class)
	public void testAddDependency_circularDependencyByLongerPath() {
		FeatureDefinition feature1 = new FeatureDefinition("feature1", new DummyDistribution());
		Parameter parameter1 = new DummyParameter(0);
		FeatureDefinition feature2 = new FeatureDefinition("feature2", new DummyDistribution());
		Parameter parameter2 = new DummyParameter(0);
		FeatureDefinition feature3 = new FeatureDefinition("feature3", new DummyDistribution());
		Parameter parameter3 = new DummyParameter(0);

		_graph.addFeatureDefinition(feature1);
		_graph.addDependency(feature1, feature2, parameter2);
		_graph.addDependency(feature2, feature3, parameter3);
		_graph.addDependency(feature3, feature1, parameter1);
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void testAddDependency_nullParent() {
		FeatureDefinition feature = new FeatureDefinition("feature", new DummyDistribution());
		Parameter parameter = new DummyParameter(0);
		_graph.addDependency(null, feature, parameter);
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void testAddDependency_nullChild() {
		FeatureDefinition feature = new FeatureDefinition("feature", new DummyDistribution());
		Parameter parameter = new DummyParameter(0);
		_graph.addDependency(feature, null, parameter);
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void testAddDependency_nullParameter() {
		FeatureDefinition feature = new FeatureDefinition("feature", new DummyDistribution());
		_graph.addDependency(feature, feature, null);
	}

	@Test
	public void testIsLeaf() {
		FeatureDefinition feature1 = new FeatureDefinition("feature1", new DummyDistribution());
		FeatureDefinition feature1_1 = new FeatureDefinition("feature1_1", new DummyDistribution());
		Parameter parameter1_1 = new DummyParameter(0);

		_graph.addFeatureDefinition(feature1);
		assertThat(_graph.isLeaf(feature1)).isTrue();
		_graph.addDependency(feature1, feature1_1, parameter1_1);
		assertThat(_graph.isLeaf(feature1)).isFalse();
		assertThat(_graph.isLeaf(feature1_1)).isTrue();
	}

	@Test
	public void testIsLeaf_featureNotInGraph() {
		assertThat(_graph.isLeaf(new FeatureDefinition("feature", new DummyDistribution()))).isFalse();
	}

	@Test
	public void testGetDependentParameters() {
		FeatureDefinition feature1 = new FeatureDefinition("feature1", new DummyDistribution());
		FeatureDefinition feature1_1 = new FeatureDefinition("feature1_1", new DummyDistribution());
		Parameter parameter1_1 = new DummyParameter(0);
		FeatureDefinition feature1_2 = new FeatureDefinition("feature1_2", new DummyDistribution());
		Parameter parameter1_2 = new DummyParameter(0);

		_graph.addFeatureDefinition(feature1);
		_graph.addDependency(feature1, feature1_1, parameter1_1);
		_graph.addDependency(feature1, feature1_2, parameter1_2);

		assertThat(_graph.getDependentParameters(feature1)).containsExactly(parameter1_1, parameter1_2);
		assertThat(_graph.getDependentParameters(feature1_1)).isEmpty();
		assertThat(_graph.getDependentParameters(feature1_2)).isEmpty();
	}

	@Test
	public void testIterator() {
		FeatureDefinition feature1 = new FeatureDefinition("feature1", new DummyDistribution());
		FeatureDefinition feature1_1 = new FeatureDefinition("feature1_1", new DummyDistribution());
		Parameter parameter1_1 = new DummyParameter(0);
		FeatureDefinition feature2 = new FeatureDefinition("feature2", new DummyDistribution());
		FeatureDefinition feature2_1 = new FeatureDefinition("feature2_1", new DummyDistribution());
		Parameter parameter2_1 = new DummyParameter(0);
		FeatureDefinition feature3 = new FeatureDefinition("feature3", new DummyDistribution());

		_graph.addFeatureDefinition(feature1);
		_graph.addFeatureDefinition(feature2);
		_graph.addFeatureDefinition(feature3);
		_graph.addDependency(feature1, feature1_1, parameter1_1);
		_graph.addDependency(feature2, feature2_1, parameter2_1);

		Iterator<FeatureDefinition> iterator = _graph.iterator();
		assertThat(iterator.next()).isEqualTo(feature1);
		assertThat(iterator.next()).isEqualTo(feature2);
		assertThat(iterator.next()).isEqualTo(feature3);
		assertThat(iterator.next()).isEqualTo(feature1_1);
		assertThat(iterator.next()).isEqualTo(feature2_1);
	}

}
