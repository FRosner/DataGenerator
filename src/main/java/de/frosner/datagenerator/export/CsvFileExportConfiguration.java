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
	private final boolean _exportInstanceIds;
	private final boolean _exportFeatureNames;

	public CsvFileExportConfiguration(@Nonnull File exportFile, boolean exportInstanceIds, boolean exportFeatureNames) {
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

	@Override
	public ExportConnection createExportConnection() {
		try {
			return new CsvExportConnection(new FileOutputStream(_file), _exportFeatureNames, _exportInstanceIds,
					_file.getAbsolutePath());
		} catch (FileNotFoundException e) {
			throw new UncheckedFileNotFoundException(e);
		}
	}

}
