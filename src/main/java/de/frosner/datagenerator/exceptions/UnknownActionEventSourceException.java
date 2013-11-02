package de.frosner.datagenerator.exceptions;

import de.frosner.datagenerator.util.ApplicationMetaData;

public class UnknownActionEventSourceException extends RuntimeException {

	private static final long serialVersionUID = ApplicationMetaData.SERIAL_VERSION_UID;

	public UnknownActionEventSourceException(Object actionEventSource) {
		super("Unknown action event source: " + actionEventSource.toString());
	}

}
