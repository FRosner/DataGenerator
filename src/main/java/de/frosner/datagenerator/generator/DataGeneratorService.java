package de.frosner.datagenerator.generator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.collect.Lists;

import de.frosner.datagenerator.export.CsvExportConnection;
import de.frosner.datagenerator.export.ExportConnection;
import de.frosner.datagenerator.features.FeatureDefinition;

public final class DataGeneratorService {

	private static final Logger LOGGER = LogManager.getLogger();

	public static final DataGeneratorService INSTANCE = new DataGeneratorService();

	private final List<FeatureDefinition> _featureDefinitions = Lists.newArrayList();
	private DataGenerator _dataGenerator;

	// visible for testing
	DataGeneratorService() {
	}

	public void addFeatureDefinition(FeatureDefinition featureDefinition) {
		_featureDefinitions.add(featureDefinition);
		LOGGER.info("Added Feature: " + featureDefinition.getName());
	}

	public void removeFeatureDefinition(int index) {
		LOGGER.info("Removed Feature: " + _featureDefinitions.remove(index).getName());
	}

	public void generateData(int numberOfInstances, File exportFile) {
		try {
			ExportConnection exportConnection;
			exportConnection = new CsvExportConnection(new FileOutputStream(exportFile));
			DataGenerator generator = new DataGenerator(numberOfInstances, exportConnection, _featureDefinitions);
			LOGGER.info("Generating " + numberOfInstances + " instances");
			generator.generate();
			LOGGER.info("Exported instances to " + exportFile);
		} catch (FileNotFoundException e) {
			LOGGER.error("File not found: " + exportFile);
		} catch (IOException e) {
			LOGGER.error("Writing to file failed: " + e.getMessage());
		}
	}

	// visible for testing
	List<FeatureDefinition> getFeatureDefinitions() {
		return _featureDefinitions;
	}

}
