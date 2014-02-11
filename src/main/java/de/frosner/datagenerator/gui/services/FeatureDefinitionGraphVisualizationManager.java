package de.frosner.datagenerator.gui.services;

import java.awt.Color;
import java.awt.geom.Rectangle2D;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.SwingUtilities;

import net.sf.qualitycheck.Check;

import org.jgraph.JGraph;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.GraphConstants;
import org.jgraph.layout.JGraphLayoutAlgorithm;
import org.jgraph.layout.SugiyamaLayoutAlgorithm;
import org.jgrapht.ListenableGraph;
import org.jgrapht.ext.JGraphModelAdapter;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.ListenableDirectedGraph;

import de.frosner.datagenerator.gui.main.FeatureDefinitionEntry;
import de.frosner.datagenerator.util.VisibleForTesting;

public class FeatureDefinitionGraphVisualizationManager {

	private static final JGraphLayoutAlgorithm LAYOUT = new SugiyamaLayoutAlgorithm();

	@VisibleForTesting
	static ListenableGraph<FeatureDefinitionEntry, DefaultEdge> _featureGraphModel;
	@VisibleForTesting
	static JGraphModelAdapter<FeatureDefinitionEntry, DefaultEdge> _featureGraphModelAdapter;
	private static JGraph _featureGraph;

	private FeatureDefinitionGraphVisualizationManager() {
		throw new UnsupportedOperationException();
	}

	// TODO document that only default graph models are accepted / create own JGraph class that only accepts the
	// required models
	public static JGraph createNewManagedJGraph() {
		_featureGraphModel = new ListenableDirectedGraph<FeatureDefinitionEntry, DefaultEdge>(DefaultEdge.class);
		_featureGraphModelAdapter = new JGraphModelAdapter<FeatureDefinitionEntry, DefaultEdge>(_featureGraphModel);
		_featureGraph = new JGraph(_featureGraphModelAdapter);
		return _featureGraph;
	}

	public static void stopManaging() {
		_featureGraphModel = null;
		_featureGraphModelAdapter = null;
		_featureGraph = null;
	}

	public static void addVertex(@Nonnull final FeatureDefinitionEntry entry) {
		Check.notNull(entry, "entry");
		if (_featureGraph != null) {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					_featureGraphModel.addVertex(entry);
					adjustLayout(entry, Color.GRAY);
					JGraphLayoutAlgorithm.applyLayout(_featureGraph, _featureGraph.getRoots(), LAYOUT);
				}
			});
		}
	}

	public static void addEdge(@Nonnull final FeatureDefinitionEntry from, @Nonnull final FeatureDefinitionEntry to) {
		Check.notNull(from, "from");
		Check.notNull(to, "to");
		if (_featureGraph != null) {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					_featureGraphModel.addEdge(from, to);
					JGraphLayoutAlgorithm.applyLayout(_featureGraph, _featureGraph.getRoots(), LAYOUT);
				}
			});
		}
	}

	// TODO replacing needs to preserve the ordering
	public static void replaceVertex(@Nonnull final FeatureDefinitionEntry toReplace,
			@Nonnull final FeatureDefinitionEntry newEntry) {
		Check.notNull(toReplace, "toReplace");
		Check.notNull(newEntry, "newEntry");
		if (_featureGraph != null) {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					_featureGraphModel.removeVertex(toReplace);
					_featureGraphModel.addVertex(newEntry);
					adjustLayout(newEntry, Color.GRAY);
					JGraphLayoutAlgorithm.applyLayout(_featureGraph, _featureGraph.getRoots(), LAYOUT);
				}
			});
		}
	}

	public static void removeVertex(@Nonnull final FeatureDefinitionEntry entry) {
		Check.notNull(entry, "entry");
		if (_featureGraph != null) {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					_featureGraphModel.removeVertex(entry);
					if (!_featureGraphModel.vertexSet().isEmpty()) {
						JGraphLayoutAlgorithm.applyLayout(_featureGraph, _featureGraph.getRoots(), LAYOUT);
					}
				}
			});
		}
	}

	public static FeatureDefinitionEntry getFeatureDefinitionEntryByCell(Object selectedCell) {
		return (FeatureDefinitionEntry) _featureGraphModelAdapter.getValue(selectedCell);
	}

	private static void adjustLayout(FeatureDefinitionEntry feature, @Nullable Color bg) {
		DefaultGraphCell cell = _featureGraphModelAdapter.getVertexCell(feature);
		GraphConstants.setBounds(cell.getAttributes(), new Rectangle2D.Double(25, 25, 200, 20));
		GraphConstants.setAutoSize(cell.getAttributes(), true);

		if (bg != null) {
			GraphConstants.setBackground(cell.getAttributes(), bg);
			GraphConstants.setOpaque(cell.getAttributes(), true);
		}

		GraphConstants.setBorderColor(cell.getAttributes(), Color.black);
		cell.addPort();
	}

}
