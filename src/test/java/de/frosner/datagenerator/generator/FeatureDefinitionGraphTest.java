package de.frosner.datagenerator.generator;

import static org.fest.assertions.Assertions.assertThat;

import java.util.Iterator;
import java.util.List;

import net.sf.qualitycheck.exception.IllegalNullArgumentException;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;

import de.frosner.datagenerator.distributions.DummyDistribution;
import de.frosner.datagenerator.distributions.ParameterizedDummyDistribution;
import de.frosner.datagenerator.distributions.VariableDummyParameter;
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
		assertThat(_graph._topologicalOrder).containsExactly(feature1);

		FeatureDefinition feature2 = new FeatureDefinition("feature2", new DummyDistribution());
		assertThat(_graph.addFeatureDefinition(feature2)).isTrue();
		assertThat(_graph._adjacentNodes.keySet()).containsOnly(feature1, feature2);
		assertThat(_graph._topologicalOrder).containsExactly(feature1, feature2);
	}

	@Test
	public void testAddFeatureDefinition_sameFeatureTwice() {
		FeatureDefinition feature = new FeatureDefinition("feature", new DummyDistribution());
		_graph.addFeatureDefinition(feature);
		assertThat(_graph.addFeatureDefinition(feature)).isFalse();
		assertThat(_graph._adjacentNodes.keySet()).containsOnly(feature);
		assertThat(_graph._topologicalOrder).containsExactly(feature);
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void testAddFeatureDefinition_null() {
		_graph.addFeatureDefinition(null);
	}

	@Test
	public void testIsEmpty() {
		assertThat(_graph.isEmpty()).isTrue();
		FeatureDefinition feature = new FeatureDefinition("feature1", new DummyDistribution());
		_graph.addFeatureDefinition(feature);
		assertThat(_graph.isEmpty()).isFalse();
	}

	@Test
	public void testAddDependency() {
		FeatureDefinition feature1 = new FeatureDefinition("feature1", new DummyDistribution());
		FeatureDefinition feature1_1 = new FeatureDefinition("feature1_1", new DummyDistribution());
		VariableDummyParameter parameter1_1 = new VariableDummyParameter();

		_graph.addFeatureDefinition(feature1);
		assertThat(_graph.addFeatureDefinitionParameterDependency(feature1, feature1_1, parameter1_1)).isTrue();

		assertThat(_graph._adjacentNodes.get(feature1)).containsOnly(
				new FeatureDefinitionParameterPair(feature1_1, parameter1_1));
		assertThat(_graph._topologicalOrder).containsExactly(feature1, feature1_1);
	}

	@Test
	public void testAddDependency_toDependentFeature() {
		FeatureDefinition feature1 = new FeatureDefinition("feature1", new DummyDistribution());
		FeatureDefinition feature1_1 = new FeatureDefinition("feature1_1", new DummyDistribution());
		VariableDummyParameter parameter1_1 = new VariableDummyParameter();
		FeatureDefinition feature1_1_1 = new FeatureDefinition("feature1_1_1", new DummyDistribution());
		VariableDummyParameter parameter1_1_1 = new VariableDummyParameter();

		_graph.addFeatureDefinition(feature1);
		_graph.addFeatureDefinitionParameterDependency(feature1, feature1_1, parameter1_1);
		_graph.addFeatureDefinitionParameterDependency(feature1_1, feature1_1_1, parameter1_1_1);

		assertThat(_graph._adjacentNodes.get(feature1)).containsOnly(
				new FeatureDefinitionParameterPair(feature1_1, parameter1_1));
		assertThat(_graph._adjacentNodes.get(feature1_1)).containsOnly(
				new FeatureDefinitionParameterPair(feature1_1_1, parameter1_1_1));
		assertThat(_graph._topologicalOrder).containsExactly(feature1, feature1_1, feature1_1_1);
	}

	@Test
	public void testAddDependency_sameDependencyTwice() {
		FeatureDefinition feature1 = new FeatureDefinition("feature1", new DummyDistribution());
		FeatureDefinition feature1_1 = new FeatureDefinition("feature1_1", new DummyDistribution());
		VariableDummyParameter parameter1_1 = new VariableDummyParameter();

		_graph.addFeatureDefinition(feature1);
		_graph.addFeatureDefinitionParameterDependency(feature1, feature1_1, parameter1_1);
		assertThat(_graph.addFeatureDefinitionParameterDependency(feature1, feature1_1, parameter1_1)).isFalse();

		assertThat(_graph._adjacentNodes.get(feature1)).containsOnly(
				new FeatureDefinitionParameterPair(feature1_1, parameter1_1));
		assertThat(_graph._topologicalOrder).containsExactly(feature1, feature1_1);
	}

	@Test(expected = CircularDependencyException.class)
	public void testAddDependency_circularDependencyByEdge() {
		FeatureDefinition feature1 = new FeatureDefinition("feature1", new DummyDistribution());
		VariableDummyParameter parameter1 = new VariableDummyParameter();
		FeatureDefinition feature2 = new FeatureDefinition("feature2", new DummyDistribution());
		VariableDummyParameter parameter2 = new VariableDummyParameter();

		_graph.addFeatureDefinition(feature1);
		_graph.addFeatureDefinitionParameterDependency(feature1, feature2, parameter2);
		_graph.addFeatureDefinitionParameterDependency(feature2, feature1, parameter1);
	}

	@Test(expected = CircularDependencyException.class)
	public void testAddDependency_circularDependencyByLongerPath() {
		FeatureDefinition feature1 = new FeatureDefinition("feature1", new DummyDistribution());
		VariableDummyParameter parameter1 = new VariableDummyParameter();
		FeatureDefinition feature2 = new FeatureDefinition("feature2", new DummyDistribution());
		VariableDummyParameter parameter2 = new VariableDummyParameter();
		FeatureDefinition feature3 = new FeatureDefinition("feature3", new DummyDistribution());
		VariableDummyParameter parameter3 = new VariableDummyParameter();

		_graph.addFeatureDefinition(feature1);
		_graph.addFeatureDefinitionParameterDependency(feature1, feature2, parameter2);
		_graph.addFeatureDefinitionParameterDependency(feature2, feature3, parameter3);
		_graph.addFeatureDefinitionParameterDependency(feature3, feature1, parameter1);
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void testAddDependency_nullParent() {
		FeatureDefinition feature = new FeatureDefinition("feature", new DummyDistribution());
		VariableDummyParameter parameter = new VariableDummyParameter();
		_graph.addFeatureDefinitionParameterDependency(null, feature, parameter);
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void testAddDependency_nullChild() {
		FeatureDefinition feature = new FeatureDefinition("feature", new DummyDistribution());
		VariableDummyParameter parameter = new VariableDummyParameter();
		_graph.addFeatureDefinitionParameterDependency(feature, null, parameter);
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void testAddDependency_nullParameter() {
		FeatureDefinition feature = new FeatureDefinition("feature", new DummyDistribution());
		_graph.addFeatureDefinitionParameterDependency(feature, feature, null);
	}

	@Test
	public void testIsLeaf() {
		FeatureDefinition feature1 = new FeatureDefinition("feature1", new DummyDistribution());
		FeatureDefinition feature1_1 = new FeatureDefinition("feature1_1", new DummyDistribution());
		VariableDummyParameter parameter1_1 = new VariableDummyParameter();

		_graph.addFeatureDefinition(feature1);
		assertThat(_graph.isLeaf(feature1)).isTrue();
		_graph.addFeatureDefinitionParameterDependency(feature1, feature1_1, parameter1_1);
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
		VariableDummyParameter parameter1_1 = new VariableDummyParameter();
		FeatureDefinition feature1_2 = new FeatureDefinition("feature1_2", new DummyDistribution());
		VariableDummyParameter parameter1_2 = new VariableDummyParameter();

		_graph.addFeatureDefinition(feature1);
		_graph.addFeatureDefinitionParameterDependency(feature1, feature1_1, parameter1_1);
		_graph.addFeatureDefinitionParameterDependency(feature1, feature1_2, parameter1_2);

		assertThat(_graph.getDependentParameters(feature1)).containsExactly(parameter1_1, parameter1_2);
		assertThat(_graph.getDependentParameters(feature1_1)).isEmpty();
		assertThat(_graph.getDependentParameters(feature1_2)).isEmpty();
	}

	@Test
	public void testIterator() {
		FeatureDefinition feature1 = new FeatureDefinition("feature1", new DummyDistribution());
		FeatureDefinition feature1_1 = new FeatureDefinition("feature1_1", new DummyDistribution());
		VariableDummyParameter parameter1_1 = new VariableDummyParameter();
		FeatureDefinition feature2 = new FeatureDefinition("feature2", new DummyDistribution());
		FeatureDefinition feature2_1 = new FeatureDefinition("feature2_1", new DummyDistribution());
		VariableDummyParameter parameter2_1 = new VariableDummyParameter();
		FeatureDefinition feature3 = new FeatureDefinition("feature3", new DummyDistribution());

		_graph.addFeatureDefinition(feature1);
		_graph.addFeatureDefinition(feature2);
		_graph.addFeatureDefinition(feature3);
		_graph.addFeatureDefinitionParameterDependency(feature1, feature1_1, parameter1_1);
		_graph.addFeatureDefinitionParameterDependency(feature2, feature2_1, parameter2_1);

		Iterator<FeatureDefinition> iterator = _graph.iterator();
		assertThat(iterator.next()).isEqualTo(feature1);
		assertThat(iterator.next()).isEqualTo(feature2);
		assertThat(iterator.next()).isEqualTo(feature3);
		assertThat(iterator.next()).isEqualTo(feature1_1);
		assertThat(iterator.next()).isEqualTo(feature2_1);
	}

	@Test
	public void testIterator_backwardEdges() {
		FeatureDefinition feature1 = new FeatureDefinition("feature1", new DummyDistribution());
		FeatureDefinition feature1_1 = new FeatureDefinition("feature1_1", new DummyDistribution());
		VariableDummyParameter parameter1_1 = new VariableDummyParameter();
		FeatureDefinition feature2 = new FeatureDefinition("feature2", new DummyDistribution());
		VariableDummyParameter parameter1 = new VariableDummyParameter();

		_graph.addFeatureDefinition(feature1);
		_graph.addFeatureDefinitionParameterDependency(feature1, feature1_1, parameter1_1);
		_graph.addFeatureDefinition(feature2);
		_graph.addFeatureDefinitionParameterDependency(feature2, feature1, parameter1);

		Iterator<FeatureDefinition> iterator = _graph.iterator();
		assertThat(iterator.next()).isEqualTo(feature2);
		assertThat(iterator.next()).isEqualTo(feature1);
		assertThat(iterator.next()).isEqualTo(feature1_1);
	}

	@Test
	public void testEquals() {
		FeatureDefinition feature1 = new FeatureDefinition("feature1", new DummyDistribution());
		FeatureDefinition feature1_1 = new FeatureDefinition("feature1_1", new DummyDistribution());
		VariableDummyParameter parameter1_1 = new VariableDummyParameter();
		FeatureDefinition feature2 = new FeatureDefinition("feature2", new DummyDistribution());
		FeatureDefinition feature2_1 = new FeatureDefinition("feature2_1", new DummyDistribution());
		VariableDummyParameter parameter2_1 = new VariableDummyParameter();

		_graph.addFeatureDefinition(feature1);
		_graph.addFeatureDefinition(feature2);
		_graph.addFeatureDefinitionParameterDependency(feature1, feature1_1, parameter1_1);
		_graph.addFeatureDefinitionParameterDependency(feature2, feature2_1, parameter2_1);

		FeatureDefinitionGraph graph2 = new FeatureDefinitionGraph();
		graph2.addFeatureDefinition(feature1);
		graph2.addFeatureDefinition(feature2);
		graph2.addFeatureDefinitionParameterDependency(feature1, feature1_1, parameter1_1);
		graph2.addFeatureDefinitionParameterDependency(feature2, feature2_1, parameter2_1);

		assertThat(_graph.equals(graph2)).isTrue();
	}

	@Test
	public void testCreateCopyOf() {
		FeatureDefinition feature1 = new FeatureDefinition("feature1", new DummyDistribution());
		FeatureDefinition feature1_1 = new FeatureDefinition("feature1_1", new DummyDistribution());
		VariableDummyParameter parameter1_1 = new VariableDummyParameter();
		FeatureDefinition feature2 = new FeatureDefinition("feature2", new DummyDistribution());
		FeatureDefinition feature2_1 = new FeatureDefinition("feature2_1", new DummyDistribution());
		VariableDummyParameter parameter2_1 = new VariableDummyParameter();

		_graph.addFeatureDefinition(feature1);
		_graph.addFeatureDefinition(feature2);
		_graph.addFeatureDefinitionParameterDependency(feature1, feature1_1, parameter1_1);
		_graph.addFeatureDefinitionParameterDependency(feature2, feature2_1, parameter2_1);

		assertThat(_graph.equals(FeatureDefinitionGraph.createCopyOf(_graph))).isTrue();
	}

	@Test
	public void testCreateFromList_withoutDependencies() {
		FeatureDefinition feature1 = new FeatureDefinition("feature1", new DummyDistribution());
		FeatureDefinition feature2 = new FeatureDefinition("feature2", new DummyDistribution());
		FeatureDefinition feature3 = new FeatureDefinition("feature3", new DummyDistribution());

		_graph.addFeatureDefinition(feature1);
		_graph.addFeatureDefinition(feature2);
		_graph.addFeatureDefinition(feature3);

		List<FeatureDefinition> featureDefinitions = Lists.newArrayList(feature1, feature2, feature3);

		assertThat(_graph.equals(FeatureDefinitionGraph.createFromList(featureDefinitions))).isTrue();
	}

	@Test
	public void testCreateFromList_withDependencies() {
		FeatureDefinition feature1 = new FeatureDefinition("feature1", new DummyDistribution());
		VariableDummyParameter parameter1_2 = new VariableDummyParameter(feature1);
		FeatureDefinition feature1_2 = new FeatureDefinition("feature2", new ParameterizedDummyDistribution(
				parameter1_2));

		_graph.addFeatureDefinition(feature1);
		_graph.addFeatureDefinition(feature1_2);
		_graph.addFeatureDefinitionParameterDependency(feature1, feature1_2, parameter1_2);

		List<FeatureDefinition> featureDefinitions = Lists.newArrayList(feature1, feature1_2);

		assertThat(_graph.equals(FeatureDefinitionGraph.createFromList(featureDefinitions))).isTrue();
	}

}
