package de.frosner.datagenerator.export;

import de.frosner.datagenerator.util.ApplicationMetaData;

/**
 * Exception indicating that a method has been called twice that is only allowed to be called once.
 */
public final class MethodNotCallableTwiceException extends RuntimeException {

	private static final long serialVersionUID = ApplicationMetaData.SERIAL_VERSION_UID;

}
