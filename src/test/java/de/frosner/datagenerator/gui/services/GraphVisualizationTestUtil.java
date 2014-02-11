package de.frosner.datagenerator.gui.services;

import static org.fest.assertions.Fail.fail;

import org.jgraph.graph.DefaultGraphCell;

import de.frosner.datagenerator.gui.main.FeatureDefinitionEntry;

public class GraphVisualizationTestUtil {

	public static void assertEdgeExists(String sourceFeatureName, String targetFeatureName) {
		assertNodeExists(sourceFeatureName);
		assertNodeExists(targetFeatureName);
		FeatureDefinitionEntry sourceVertex = getFeatureDefinitionEntryByName(sourceFeatureName);
		FeatureDefinitionEntry targetVertex = getFeatureDefinitionEntryByName(targetFeatureName);
		if (FeatureDefinitionGraphVisualizationManager._featureGraphModel.containsEdge(sourceVertex, targetVertex)) {
			return;
		}
		fail("GUI does not contain an edge from " + sourceFeatureName + " to " + targetFeatureName + ".");
	}

	public static void assertNodeExists(String featureName) {
		if (getCellByFeatureName(featureName) == null) {
			fail("GUI does not contain an vertex for " + featureName + ".");
		}
	}

	public static void assertGraphIsEmpty() {
		if (!FeatureDefinitionGraphVisualizationManager._featureGraphModel.vertexSet().isEmpty()) {
			fail("Graph is not empty!");
		}
	}

	public static int getNodeCount() {
		return FeatureDefinitionGraphVisualizationManager._featureGraphModel.vertexSet().size();
	}

	public static DefaultGraphCell getCellByFeatureName(String featureName) {
		return FeatureDefinitionGraphVisualizationManager._featureGraphModelAdapter
				.getVertexCell(getFeatureDefinitionEntryByName(featureName));
	}

	private static FeatureDefinitionEntry getFeatureDefinitionEntryByName(String name) {
		for (FeatureDefinitionEntry featureDefinitionEntry : FeatureDefinitionGraphVisualizationManager._featureGraphModel
				.vertexSet()) {
			if (featureDefinitionEntry.getFeatureName().equals(name)) {
				return featureDefinitionEntry;
			}
		}
		return null;
	}

}
