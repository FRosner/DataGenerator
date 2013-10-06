package de.frosner.datagenerator.export;

import java.io.FileNotFoundException;

import de.frosner.datagenerator.util.ApplicationMetaData;

/**
 * This is a {@link FileNotFoundException} wrapped into a {@link RuntimeException}.
 */
public class UncheckedFileNotFoundException extends RuntimeException {

	private static final long serialVersionUID = ApplicationMetaData.SERIAL_VERSION_UID;

	public UncheckedFileNotFoundException(FileNotFoundException e) {
		super(e);
	}

}
