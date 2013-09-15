package de.frosner.datagenerator.gui.main;

import javax.swing.filechooser.FileFilter;

import de.frosner.datagenerator.util.ApplicationMetaData;

public class UnsupportedFileFilterException extends RuntimeException {

	private static final long serialVersionUID = ApplicationMetaData.SERIAL_VERSION_UID;

	public UnsupportedFileFilterException(FileFilter filter) {
		super("Unsupported file filter selected: " + filter.getClass().getSimpleName());
	}

}
