package de.frosner.datagenerator.exceptions;

import java.io.FileNotFoundException;

import de.frosner.datagenerator.util.ApplicationMetaData;

/**
 * This is a {@linkplain FileNotFoundException} wrapped into a {@linkplain RuntimeException}.
 */
public class UncheckedFileNotFoundException extends RuntimeException {

	private static final long serialVersionUID = ApplicationMetaData.SERIAL_VERSION_UID;

	public UncheckedFileNotFoundException(FileNotFoundException e) {
		super(e);
	}

}
