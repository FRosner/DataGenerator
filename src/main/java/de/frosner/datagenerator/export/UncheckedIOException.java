package de.frosner.datagenerator.export;

import de.frosner.datagenerator.util.ApplicationMetaData;

public class UncheckedIOException extends RuntimeException {

	private static final long serialVersionUID = ApplicationMetaData.SERIAL_VERSION_UID;

	public UncheckedIOException(java.io.IOException e) {
		super(e);
	}

}
