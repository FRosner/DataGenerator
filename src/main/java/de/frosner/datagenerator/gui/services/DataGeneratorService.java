package de.frosner.datagenerator.gui.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.frosner.datagenerator.exceptions.UncheckedFileNotFoundException;
import de.frosner.datagenerator.exceptions.UncheckedIOException;
import de.frosner.datagenerator.export.ExportConfiguration;
import de.frosner.datagenerator.export.ExportConnection;
import de.frosner.datagenerator.features.FeatureDefinition;
import de.frosner.datagenerator.generator.DataGenerator;
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
	 * Adds a {@linkplain FeatureDefinition} to the list. Make sure that it is added to the list in the UI as well.
	 * 
	 * @param featureDefinition
	 *            to add
	 */
	public void addFeatureDefinition(FeatureDefinition featureDefinition) {
		_featureDefinitions.add(featureDefinition);
		TextAreaLogManager.info("Added Feature: " + featureDefinition.getName());
		PreviewTableManager.generatePreview(_featureDefinitions);
	}

	/**
	 * Removes a {@linkplain FeatureDefinition} from the list at the specified index. Make sure that it is removed from
	 * the list in the UI as well.
	 * 
	 * @param index
	 */
	public void removeFeatureDefinition(int index) {
		TextAreaLogManager.info("Removed Feature: " + _featureDefinitions.remove(index).getName());
		PreviewTableManager.generatePreview(_featureDefinitions);
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
	public void generateData(int numberOfInstances, ExportConfiguration exportConfig) {
		if (!_generating) {
			try {
				_generating = true;
				boolean aborted = false;
				ExportConnection exportConnection = exportConfig.createExportConnection();
				DataGenerator generator = new DataGenerator(numberOfInstances, exportConnection, _featureDefinitions);
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
