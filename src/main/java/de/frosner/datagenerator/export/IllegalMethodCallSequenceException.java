package de.frosner.datagenerator.export;

import de.frosner.datagenerator.util.ApplicationMetaData;

/**
 * Exception indicating that a required method call sequence has not been used or a method was called that requires
 * another method to be called first.
 */
public final class IllegalMethodCallSequenceException extends RuntimeException {

	private static final long serialVersionUID = ApplicationMetaData.SERIAL_VERSION_UID;

}
