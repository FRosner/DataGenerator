package de.frosner.datagenerator.generator;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.collect.Lists;

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

	// visible for testing
	List<FeatureDefinition> getFeatureDefinitions() {
		return Lists.newArrayList(_featureDefinitions);
	}

}
