package de.frosner.datagenerator.export;

public enum ExportInstanceIds {

	YES, NO;

	public static ExportInstanceIds when(boolean exportInstanceIds) {
		return (exportInstanceIds) ? YES : NO;
	}

	public boolean toBoolean() {
		return this == YES;
	}

}
