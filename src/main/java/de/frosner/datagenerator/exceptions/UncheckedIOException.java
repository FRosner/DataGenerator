package de.frosner.datagenerator.exceptions;

import java.io.IOException;

import de.frosner.datagenerator.util.ApplicationMetaData;

/**
 * This is a {@linkplain IOException} wrapped into a {@linkplain RuntimeException}.
 */
public final class UncheckedIOException extends RuntimeException {

	private static final long serialVersionUID = ApplicationMetaData.SERIAL_VERSION_UID;

	public UncheckedIOException(java.io.IOException e) {
		super(e);
	}

}
