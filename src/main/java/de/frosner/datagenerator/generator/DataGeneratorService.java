package de.frosner.datagenerator.generator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.frosner.datagenerator.export.CsvExportConfiguration;
import de.frosner.datagenerator.export.CsvExportConnection;
import de.frosner.datagenerator.export.ExportConnection;
import de.frosner.datagenerator.features.FeatureDefinition;
import de.frosner.datagenerator.gui.main.TextAreaLogger;
import de.frosner.datagenerator.util.VisibleForTesting;

public final class DataGeneratorService {

	public static final DataGeneratorService INSTANCE = new DataGeneratorService();

	private final List<FeatureDefinition> _featureDefinitions = Collections
			.synchronizedList(new ArrayList<FeatureDefinition>());
	private boolean _generating = false;

	@VisibleForTesting
	DataGeneratorService() {
	}

	public void addFeatureDefinition(FeatureDefinition featureDefinition) {
		_featureDefinitions.add(featureDefinition);
		TextAreaLogger.info("Added Feature: " + featureDefinition.getName());
	}

	public void removeFeatureDefinition(int index) {
		TextAreaLogger.info("Removed Feature: " + _featureDefinitions.remove(index).getName());
	}

	public void generateData(int numberOfInstances, CsvExportConfiguration exportConfig) {
		File exportFile = exportConfig.getFile();
		if (!_generating) {
			try {
				_generating = true;
				ExportConnection exportConnection;
				exportConnection = new CsvExportConnection(new FileOutputStream(exportFile));
				DataGenerator generator = new DataGenerator(numberOfInstances, exportConnection, _featureDefinitions);
				TextAreaLogger.info("Generating " + numberOfInstances + " instances");
				if (generator.generate()) {
					TextAreaLogger.info("Exported instances to " + exportFile);
				} else {
					TextAreaLogger.warn("Generation aborted. Partial results written to " + exportFile);
				}
			} catch (FileNotFoundException e) {
				TextAreaLogger.error("File not found: " + exportFile);
			} catch (IOException e) {
				TextAreaLogger.error("Writing to file failed: " + e.getMessage());
			} finally {
				_generating = false;
			}
		} else {
			TextAreaLogger.error("Generation already in progress");
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
