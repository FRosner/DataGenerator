package de.frosner.datagenerator.gui.main;

import net.sf.qualitycheck.Check;
import de.frosner.datagenerator.features.FeatureDefinition;

public class GaussianFeatureEntry extends FeatureDefinitionEntry {

	public static String KEY = "Gaussian";

	private final String _mean;
	private final String _sigma;

	protected GaussianFeatureEntry(FeatureDefinition featureDefinition, String mean, String sigma) {
		super(featureDefinition);
		_mean = Check.notNull(mean);
		_sigma = Check.notNull(sigma);
	}

	public String getMean() {
		return _mean;
	}

	public String getSigma() {
		return _sigma;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof GaussianFeatureEntry) {
			GaussianFeatureEntry gaussian = (GaussianFeatureEntry) o;
			return (gaussian._featureDefinition.equals(_featureDefinition) && gaussian._mean.equals(_mean) && gaussian._sigma
					.equals(_sigma));
		} else {
			return false;
		}
	}

}
