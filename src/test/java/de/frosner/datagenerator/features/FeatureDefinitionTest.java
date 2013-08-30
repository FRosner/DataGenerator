package de.frosner.datagenerator.features;

import org.junit.Before;

import de.frosner.datagenerator.distributions.Distribution;
import de.frosner.datagenerator.distributions.DummyDistribution;

public class FeatureDefinitionTest {

	private FeatureDefinition _featureDefinition;
	private static final String FEATURE_NAME = "feature";
	private static final Distribution FEATURE_DISTRIBUTION = new DummyDistribution();

	@Before
	public void createFeatureDefinition() {
		_featureDefinition = new FeatureDefinition(FEATURE_NAME, FEATURE_DISTRIBUTION);
	}

}
