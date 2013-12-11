package de.frosner.datagenerator.export;

public enum ExportFeatureNames {

	YES, NO;

	public static ExportFeatureNames when(boolean exportFeatureNames) {
		return (exportFeatureNames) ? YES : NO;
	}

	public boolean toBoolean() {
		return this == YES;
	}

}
