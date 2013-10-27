package de.frosner.datagenerator.exceptions;

import de.frosner.datagenerator.util.ApplicationMetaData;

public class UnsupportedSelectionException extends RuntimeException {

	private static final long serialVersionUID = ApplicationMetaData.SERIAL_VERSION_UID;

	public UnsupportedSelectionException(Object selection) {
		super("Unsupported selection: " + selection.toString());
	}

}
