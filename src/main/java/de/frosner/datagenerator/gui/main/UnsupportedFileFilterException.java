package de.frosner.datagenerator.gui.main;

import javax.swing.filechooser.FileFilter;

import de.frosner.datagenerator.util.ApplicationMetaData;

/**
 * The {@linkplain FileFilter} used is not supported by the {@linkplain ExportFileChooser}.
 */
public final class UnsupportedFileFilterException extends RuntimeException {

	private static final long serialVersionUID = ApplicationMetaData.SERIAL_VERSION_UID;

	/**
	 * Creates a new {@linkplain UnsupportedFileFilterException} indicating that the specified {@linkplain FileFilter}
	 * is not supported.
	 * 
	 * @param filter
	 *            that is not supported
	 */
	public UnsupportedFileFilterException(FileFilter filter) {
		super("Unsupported file filter selected: " + filter.getClass().getSimpleName());
	}

}
