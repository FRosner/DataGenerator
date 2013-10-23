package de.frosner.datagenerator.util;

public class MalformedExportFormatException extends RuntimeException {

	private static final long serialVersionUID = ApplicationMetaData.SERIAL_VERSION_UID;

	public MalformedExportFormatException(String format, String reason) {
		super("Malformed export format: " + format + ", Reason: " + reason + ".");
	}

}
