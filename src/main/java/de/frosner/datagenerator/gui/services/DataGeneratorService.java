package de.frosner.datagenerator.gui.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.frosner.datagenerator.export.CsvExportConfiguration;
import de.frosner.datagenerator.export.CsvExportConnection;
import de.frosner.datagenerator.export.ExportConnection;
import de.frosner.datagenerator.export.UncheckedIOException;
import de.frosner.datagenerator.features.FeatureDefinition;
import de.frosner.datagenerator.generator.DataGenerator;
import de.frosner.datagenerator.util.VisibleForTesting;

/**
 * Service maintaining a list of {@link FeatureDefinition} instances. It can be used to generate data to a specified
 * export connection.
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
	 * Adds a feature definition to the list. Make sure that it is added to the list in the UI as well.
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
	 * Removes a feature definition from the list at the specified index. Make sure that it is removed from the list in
	 * the UI as well.
	 * 
	 * @param index
	 */
	public void removeFeatureDefinition(int index) {
		TextAreaLogManager.info("Removed Feature: " + _featureDefinitions.remove(index).getName());
		PreviewTableManager.generatePreview(_featureDefinitions);
	}

	/**
	 * Generate and export a number of instances to a CSV file.
	 * 
	 * @param numberOfInstances
	 *            to be generated
	 * @param exportConfig
	 *            containing file and options
	 */
	public void generateData(int numberOfInstances, CsvExportConfiguration exportConfig) {
		File exportFile = exportConfig.getFile();
		if (!_generating) {
			try {
				_generating = true;
				boolean aborted = false;
				ExportConnection exportConnection;
				exportConnection = new CsvExportConnection(new FileOutputStream(exportFile), exportConfig
						.exportFeatureNames(), exportConfig.exportInstanceIds());
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
					TextAreaLogManager.info("Exported instances to " + exportFile);
				} else {
					TextAreaLogManager.warn("Generation aborted. Partial results written to " + exportFile);
				}
				exportConnection.close();
			} catch (FileNotFoundException e) {
				TextAreaLogManager.error("File not found: " + exportFile);
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
