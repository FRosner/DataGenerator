package de.frosner.datagenerator.export;

import java.io.File;

import javax.annotation.Nonnull;

import net.sf.qualitycheck.Check;

/**
 * Configuration parameters a CSV export connection needs.
 */
public final class CsvExportConfiguration {

	private final File _file;
	private final boolean _exportInstanceIds;
	private final boolean _exportFeatureNames;

	public CsvExportConfiguration(@Nonnull File exportFile, boolean exportInstanceIds, boolean exportFeatureNames) {
		_file = Check.notNull(exportFile);
		_exportInstanceIds = exportInstanceIds;
		_exportFeatureNames = exportFeatureNames;
	}

	public File getFile() {
		return _file;
	}

	public boolean exportInstanceIds() {
		return _exportInstanceIds;
	}

	public boolean exportFeatureNames() {
		return _exportFeatureNames;
	}

}
