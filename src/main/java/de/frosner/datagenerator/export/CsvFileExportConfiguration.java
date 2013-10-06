package de.frosner.datagenerator.export;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import javax.annotation.Nonnull;

import net.sf.qualitycheck.Check;

/**
 * Configuration parameters a CSV export connection needs.
 */
public final class CsvFileExportConfiguration implements ExportConfiguration {

	private final File _file;
	private final boolean _isExportingInstanceIds;
	private final boolean _isExportingFeatureNames;

	public CsvFileExportConfiguration(@Nonnull File exportFile, boolean isExportingInstanceIds, boolean isExportingFeatureNames) {
		_file = Check.notNull(exportFile);
		_isExportingInstanceIds = isExportingInstanceIds;
		_isExportingFeatureNames = isExportingFeatureNames;
	}

	public File getFile() {
		return _file;
	}

	public boolean isExportingInstanceIds() {
		return _isExportingInstanceIds;
	}

	public boolean isExportingFeatureNames() {
		return _isExportingFeatureNames;
	}

	@Override
	public ExportConnection createExportConnection() {
		try {
			return new CsvExportConnection(new FileOutputStream(_file), _isExportingFeatureNames, _isExportingInstanceIds,
					_file.getAbsolutePath());
		} catch (FileNotFoundException e) {
			throw new UncheckedFileNotFoundException(e);
		}
	}

}
