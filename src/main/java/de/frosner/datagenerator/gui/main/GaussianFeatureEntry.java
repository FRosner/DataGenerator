package de.frosner.datagenerator.gui.main;

import javax.annotation.Nonnull;

import net.sf.qualitycheck.Check;
import de.frosner.datagenerator.features.FeatureDefinition;

public class GaussianFeatureEntry extends FeatureDefinitionEntry {

	public enum MeanIsDependent {
		TRUE, FALSE;
	}

	public static String KEY = "Gaussian";

	private final boolean _meanIsDependent;

	private final Object _mean;
	private final String _sigma;

	// TODO make builder and make meanIsDependent obsolete
	public GaussianFeatureEntry(@Nonnull FeatureDefinition featureDefinition, @Nonnull Object mean,
			@Nonnull MeanIsDependent meanIsDependent, @Nonnull String sigma) {
		super(featureDefinition);
		Check.notNull(meanIsDependent);
		_mean = Check.notNull(mean);
		_sigma = Check.notNull(sigma);
		_meanIsDependent = meanIsDependent.equals(MeanIsDependent.TRUE);
	}

	public String getMeanAsString() {
		return _mean.toString();
	}

	public Object getMean() {
		return _mean;
	}

	public String getSigma() {
		return _sigma;
	}

	public boolean meanIsDependent() {
		return _meanIsDependent;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof GaussianFeatureEntry) {
			GaussianFeatureEntry gaussian = (GaussianFeatureEntry) o;
			return (gaussian._featureDefinition.equals(_featureDefinition) && gaussian._mean.equals(_mean)
					&& gaussian._sigma.equals(_sigma) && (_meanIsDependent == gaussian._meanIsDependent));
		} else {
			return false;
		}
	}

}
