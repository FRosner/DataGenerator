package de.frosner.datagenerator.exceptions;

import de.frosner.datagenerator.features.FeatureValue;
import de.frosner.datagenerator.util.ApplicationMetaData;

public class FeatureValueCannotBeMappedException extends RuntimeException {

	private static final long serialVersionUID = ApplicationMetaData.SERIAL_VERSION_UID;

	public FeatureValueCannotBeMappedException(FeatureValue value) {
		super("Feature value " + value + " cannot be mapped to a parameter.");
	}

}
