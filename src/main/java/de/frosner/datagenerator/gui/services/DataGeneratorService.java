package de.frosner.datagenerator.gui.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;

import net.sf.qualitycheck.Check;
import de.frosner.datagenerator.exceptions.UncheckedFileNotFoundException;
import de.frosner.datagenerator.exceptions.UncheckedIOException;
import de.frosner.datagenerator.export.ExportConfiguration;
import de.frosner.datagenerator.export.ExportConnection;
import de.frosner.datagenerator.features.FeatureDefinition;
import de.frosner.datagenerator.generator.DataGenerator;
import de.frosner.datagenerator.generator.FeatureDefinitionGraph;
import de.frosner.datagenerator.gui.main.FeatureDefinitionEntry;
import de.frosner.datagenerator.util.VisibleForTesting;

/**
 * Service maintaining a list of {@linkplain FeatureDefinition}s. It can be used to generate data to an
 * {@linkplain ExportConnection}.
 * <p>
 * The feature definition list should be kept synchronized with the list in the UI.
 */
public final class DataGeneratorService {

	public static final DataGeneratorService INSTANCE = new DataGeneratorService();

	private final List<FeatureDefinition> _featureDefinitions = Collections
			.synchronizedList(new ArrayList<FeatureDefinition>());
	private boolean _generating = false;

	@VisibleForTesting
	DataGeneratorService() {
	}

	/**
	 * Adds a {@linkplain FeatureDefinitionEntry} to the graph.
	 * 
	 * @param featureDefinitionEntry
	 *            to add
	 */
	public void addFeatureDefinition(@Nonnull FeatureDefinitionEntry featureDefinitionEntry) {
		Check.notNull(featureDefinitionEntry, "featureDefinition");
		_featureDefinitions.add(featureDefinitionEntry.getFeatureDefinition());

		FeatureDefinitionGraphVisualizationManager.addVertex(featureDefinitionEntry);
		TextAreaLogManager.info("Added Feature: " + featureDefinitionEntry.getFeatureName());
		PreviewTableManager.generatePreview(FeatureDefinitionGraph.createFromList(_featureDefinitions));
	}

	/**
	 * Replaces a {@linkplain FeatureDefinition} from the list at the specified index by the specified
	 * {@linkplain FeatureDefinition}. Make sure that it is replaced in the list in the UI as well.
	 * 
	 * @param index
	 *            to replace at
	 * @param featureDefinition
	 *            to replace with
	 */
	public void replaceFeatureDefinitionAt(int index, @Nonnull FeatureDefinition featureDefinition) {
		Check.notNull(featureDefinition, "featureDefinition");
		TextAreaLogManager.info("Edited Feature: " + _featureDefinitions.set(index, featureDefinition).getName());
		PreviewTableManager.generatePreview(FeatureDefinitionGraph.createFromList(_featureDefinitions));
	}

	/**
	 * Removes a {@linkplain FeatureDefinitionEntry} from the graph.
	 * 
	 * @param index
	 *            to remove the feature definition at
	 */
	public void removeFeatureDefinition(FeatureDefinitionEntry featureDefinitionEntry) {
		_featureDefinitions.remove(featureDefinitionEntry.getFeatureDefinition());
		TextAreaLogManager.info("Removed Feature: " + featureDefinitionEntry.getFeatureName());
		FeatureDefinitionGraphVisualizationManager.removeVertex(featureDefinitionEntry);
		PreviewTableManager.generatePreview(FeatureDefinitionGraph.createFromList(_featureDefinitions));
	}

	/**
	 * Generate and export a number of instances to an {@linkplain ExportConnection} as configured by the specified
	 * {@linkplain ExportConfiguration}.
	 * 
	 * @param numberOfInstances
	 *            to be generated
	 * @param exportConfig
	 *            containing everything needed to create an {@linkplain ExportConnection}.
	 */
	public void generateData(int numberOfInstances, @Nonnull ExportConfiguration exportConfig) {
		Check.notNull(exportConfig, "exportConfig");
		if (!_generating) {
			try {
				_generating = true;
				boolean aborted = false;
				ExportConnection exportConnection = exportConfig.createExportConnection();
				DataGenerator generator = new DataGenerator(numberOfInstances, exportConnection, FeatureDefinitionGraph
						.createFromList(_featureDefinitions));
				TextAreaLogManager.info("Generating " + numberOfInstances + " instances");
				int range = 1000;
				ProgressBarManager.resetProgress();
				ProgressBarManager.setProgressBarMaximumValue(Math.max(numberOfInstances / range, 1));
				for (int offset = 0; offset < numberOfInstances; offset += range) {
					generator.generate(offset, range);
					ProgressBarManager.increaseProgress();
					if (Thread.interrupted()) {
						aborted = true;
						break;
					}
				}
				if (!aborted) {
					TextAreaLogManager.info("Exported instances to " + exportConnection.getExportLocation());
				} else {
					TextAreaLogManager.warn("Generation aborted. Partial results written to "
							+ exportConnection.getExportLocation());
				}
				exportConnection.close();
			} catch (UncheckedFileNotFoundException e) {
				TextAreaLogManager.error("File not found: " + e.getMessage());
			} catch (UncheckedIOException e) {
				TextAreaLogManager.error("Writing to file failed: " + e.getMessage());
			} finally {
				_generating = false;
			}
		} else {
			TextAreaLogManager.error("Generation already in progress");
		}
	}

	@VisibleForTesting
	List<FeatureDefinition> getFeatureDefinitions() {
		return _featureDefinitions;
	}

	@VisibleForTesting
	public void reset() {
		_featureDefinitions.clear();
		_generating = false;
	}

}
