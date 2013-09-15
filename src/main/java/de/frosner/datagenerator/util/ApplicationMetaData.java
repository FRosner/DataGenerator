package de.frosner.datagenerator.util;

import net.sf.qualitycheck.Check;

public final class ApplicationMetaData {

	public static final int SERIAL_VERSION_UID = 1;
	private static final String MESSAGE_PREFIX = "Jar manifest is missing ";
	private static String _version = "NA";
	private static String _name = "Data Generator";
	private static String _revision = "NA";
	private static String _timestamp = "NA";

	private ApplicationMetaData() {
		throw new UnsupportedOperationException();
	}

	public static String getVersion() {
		return _version;
	}

	public static void setVersion(String version) {
		_version = Check.notNull(version, MESSAGE_PREFIX + "version");
	}

	public static String getName() {
		return _name;
	}

	public static void setName(String name) {
		_name = Check.notNull(name, MESSAGE_PREFIX + "name");
	}

	public static String getRevision() {
		return _revision;
	}

	public static void setRevision(String revision) {
		_revision = Check.notNull(revision, MESSAGE_PREFIX + "revision");
	}

	public static String getTimestamp() {
		return _timestamp;
	}

	public static void setTimestamp(String timestamp) {
		_timestamp = Check.notNull(timestamp, MESSAGE_PREFIX + "timestamp");
	}

}
