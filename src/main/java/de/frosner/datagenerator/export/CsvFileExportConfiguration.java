package de.frosner.datagenerator.export;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import javax.annotation.Nonnull;

import net.sf.qualitycheck.Check;

/**
 * {@linkplain ExportConfiguration} for a {@linkplain CsvExportConnection}.
 */
public final class CsvFileExportConfiguration implements ExportConfiguration {

	private final File _file;
	private final boolean _isExportingInstanceIds;
	private final boolean _isExportingFeatureNames;

	/**
	 * Creates a {@linkplain CsvFileExportConfiguration} with the specified file. You may also decide whether to include
	 * instance IDs and feature names into the CSV or not.
	 * 
	 * @param exportFile
	 *            to open the connection on
	 * @param isExportingInstanceIds
	 * @param isExportingFeatureNames
	 */
	public CsvFileExportConfiguration(@Nonnull File exportFile, boolean isExportingInstanceIds,
			boolean isExportingFeatureNames) {
		_file = Check.notNull(exportFile);
		_isExportingInstanceIds = isExportingInstanceIds;
		_isExportingFeatureNames = isExportingFeatureNames;
	}

	/**
	 * Returns the {@linkplain File} the {@linkplain CsvExportConnection} will write to.
	 * 
	 * @return {@linkplain File} to export to
	 */
	public File getFile() {
		return _file;
	}

	/**
	 * Returns whether the {@linkplain CsvExportConnection} will include the instance IDs.
	 * 
	 * @return whether to include the instance IDs
	 */
	public boolean isExportingInstanceIds() {
		return _isExportingInstanceIds;
	}

	/**
	 * Returns whether the {@linkplain CsvExportConnection} will include feature names as column names.
	 * 
	 * @return whether to include the feature names
	 */
	public boolean isExportingFeatureNames() {
		return _isExportingFeatureNames;
	}

	/**
	 * @throws UncheckedFileNotFoundException
	 *             if the underlying {@linkplain FileOutputStream} throws a {@linkplain FileNotFoundException}.
	 **/
	@Override
	public ExportConnection createExportConnection() {
		try {
			return new CsvExportConnection(new FileOutputStream(_file), _isExportingFeatureNames,
					_isExportingInstanceIds, _file.getAbsolutePath());
		} catch (FileNotFoundException e) {
			throw new UncheckedFileNotFoundException(e);
		}
	}

}
