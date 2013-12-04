package de.frosner.datagenerator.exceptions;

import de.frosner.datagenerator.util.ApplicationMetaData;

public class IllegalSigmaParameterArgumentException extends RuntimeException {

	private static final long serialVersionUID = ApplicationMetaData.SERIAL_VERSION_UID;

	public IllegalSigmaParameterArgumentException() {
		super("Gaussian distribution sigma must be positive.");
	}

}
