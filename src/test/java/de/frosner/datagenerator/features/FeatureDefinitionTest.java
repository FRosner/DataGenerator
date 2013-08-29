package de.frosner.datagenerator.features;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;

import de.frosner.datagenerator.distributions.Distribution;
import de.frosner.datagenerator.distributions.DummyDistribution;
import de.frosner.datagenerator.util.GsonUtil;

public class FeatureDefinitionTest {

	private FeatureDefinition _featureDefinition;
	private static final String FEATURE_NAME = "feature";
	private static final Distribution FEATURE_DISTRIBUTION = new DummyDistribution();

	@Before
	public void createFeatureDefinition() {
		_featureDefinition = new FeatureDefinition(FEATURE_NAME, FEATURE_DISTRIBUTION);
	}

	@Test
	public void testSerializeDeserialize() {
		assertThat(GsonUtil.createFeatureDefinitionFromJson(GsonUtil.featureDefinitionToJson(_featureDefinition)))
				.isEqualTo(_featureDefinition);
	}

}
