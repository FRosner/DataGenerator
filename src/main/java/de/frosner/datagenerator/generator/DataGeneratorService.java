package de.frosner.datagenerator.generator;

import java.util.List;

import com.google.common.collect.Lists;

import de.frosner.datagenerator.features.FeatureDefinition;

public final class DataGeneratorService {

	public static final DataGeneratorService INSTANCE = new DataGeneratorService();

	private final List<FeatureDefinition> _featureDefinitions = Lists.newArrayList();
	private DataGenerator _dataGenerator;

	private DataGeneratorService() {
	}

}
