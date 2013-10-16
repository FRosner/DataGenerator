package de.frosner.datagenerator.exceptions;

import javax.swing.text.BadLocationException;

import de.frosner.datagenerator.util.ApplicationMetaData;

/**
 * This is a {@linkplain BadLocationException} wrapped into a {@linkplain RuntimeException}.
 */
public class UncheckedBadLocationException extends RuntimeException {

	private static final long serialVersionUID = ApplicationMetaData.SERIAL_VERSION_UID;

	public UncheckedBadLocationException(BadLocationException e) {
		super(e);
	}
}
