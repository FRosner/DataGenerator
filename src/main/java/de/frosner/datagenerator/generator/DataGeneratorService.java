package de.frosner.datagenerator.generator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import com.google.common.collect.Lists;

import de.frosner.datagenerator.export.CsvExportConnection;
import de.frosner.datagenerator.export.ExportConnection;
import de.frosner.datagenerator.features.FeatureDefinition;
import de.frosner.datagenerator.gui.TextAreaLogger;

public final class DataGeneratorService {

	public static final DataGeneratorService INSTANCE = new DataGeneratorService();

	private final List<FeatureDefinition> _featureDefinitions = Lists.newArrayList();

	// visible for testing
	DataGeneratorService() {
	}

	public void addFeatureDefinition(FeatureDefinition featureDefinition) {
		_featureDefinitions.add(featureDefinition);
		TextAreaLogger.log("Added Feature: " + featureDefinition.getName());
	}

	public void removeFeatureDefinition(int index) {
		TextAreaLogger.log("Removed Feature: " + _featureDefinitions.remove(index).getName());
	}

	public void generateData(int numberOfInstances, File exportFile) {
		try {
			ExportConnection exportConnection;
			exportConnection = new CsvExportConnection(new FileOutputStream(exportFile));
			DataGenerator generator = new DataGenerator(numberOfInstances, exportConnection, _featureDefinitions);
			TextAreaLogger.log("Generating " + numberOfInstances + " instances");
			generator.generate();
			TextAreaLogger.log("Exported instances to " + exportFile);
		} catch (FileNotFoundException e) {
			TextAreaLogger.log("File not found: " + exportFile);
		} catch (IOException e) {
			TextAreaLogger.log("Writing to file failed: " + e.getMessage());
		}
	}

	// visible for testing
	List<FeatureDefinition> getFeatureDefinitions() {
		return _featureDefinitions;
	}

}
