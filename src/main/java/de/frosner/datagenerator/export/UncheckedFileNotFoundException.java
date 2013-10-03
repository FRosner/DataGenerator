package de.frosner.datagenerator.export;

import java.io.FileNotFoundException;

import de.frosner.datagenerator.util.ApplicationMetaData;

public class UncheckedFileNotFoundException extends RuntimeException {

	private static final long serialVersionUID = ApplicationMetaData.SERIAL_VERSION_UID;

	public UncheckedFileNotFoundException(FileNotFoundException e) {
		super(e);
	}

}
