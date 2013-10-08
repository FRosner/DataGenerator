package de.frosner.datagenerator.exceptions;

import de.frosner.datagenerator.util.ApplicationMetaData;

/**
 * {@linkplain RuntimeException} indicating that the given number violates a precondition because it is not positive.
 */
public class IllegalNonPositiveNumberArgumentException extends RuntimeException {

	private static final long serialVersionUID = ApplicationMetaData.SERIAL_VERSION_UID;

	public IllegalNonPositiveNumberArgumentException(Number argument) {
		super("Argument should be positive but was: " + argument);
	}

}
