package de.frosner.datagenerator.export;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Iterator;
import java.util.List;

import de.frosner.datagenerator.features.FeatureDefinition;
import de.frosner.datagenerator.features.FeatureValue;
import de.frosner.datagenerator.generator.Instance;

/**
 * Connection for exporting generated data in comma separated value (CSV) format to a given {@link OutputStream}.
 * Optionally a textual representation of the export location can be specified to make error messages readable.
 */
public final class CsvExportConnection extends ExportConnection {

	private final BufferedWriter _out;
	private final boolean _exportFeatureNames;
	private final boolean _exportInstanceIds;
	private final String _exportLocation;

	/**
	 * Creates a new {@link CsvExportConnection} exporting to the specified {@link OutputStream}. The specified location
	 * is used to display log and error messages.
	 * 
	 * @param outputStream
	 *            to export to
	 * @param exportFeatureNames
	 *            should feature names also be exported?
	 * @param exportInstanceIds
	 *            should instances have their ID exported as well?
	 * @param location
	 *            textual representation of the export location
	 */
	public CsvExportConnection(OutputStream outputStream, boolean exportFeatureNames, boolean exportInstanceIds,
			String location) {
		_out = new BufferedWriter(new OutputStreamWriter(outputStream));
		_exportFeatureNames = exportFeatureNames;
		_exportInstanceIds = exportInstanceIds;
		_exportLocation = location;
	}

	/**
	 * Creates a new {@link CsvExportConnection} exporting to the specified {@link OutputStream}.
	 * 
	 * @param outputStream
	 *            to export to
	 * @param exportFeatureNames
	 *            should feature names also be exported?
	 * @param exportInstanceIds
	 *            should instances have their ID exported as well?
	 **/
	public CsvExportConnection(OutputStream outputStream, boolean exportFeatureNames, boolean exportInstanceIds) {
		this(outputStream, exportFeatureNames, exportInstanceIds, outputStream.toString());
	}

	/**
	 * @throws UncheckedIOException
	 *             if the underlying export {@linkplain OutputStream} throws an {@linkplain IOException}.
	 */
	@Override
	public void close() {
		try {
			_out.close();
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	/**
	 * @throws UncheckedIOException
	 *             if the underlying export {@linkplain OutputStream} throws an {@linkplain IOException}.
	 */
	@Override
	protected void exportInstanceStrategy(Instance instance) {
		try {
			if (_exportInstanceIds) {
				_out.write(instance.getId() + ",");
			}
			Iterator<FeatureValue> values = instance.iterator();
			while (values.hasNext()) {
				_out.write(values.next().getValueAsString());
				if (values.hasNext()) {
					_out.write(",");
				}
			}
			_out.write("\n");
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	/**
	 * @throws UncheckedIOException
	 *             if the underlying export {@linkplain OutputStream} throws an {@linkplain IOException}.
	 */
	@Override
	protected void exportMetaDataStrategy(List<FeatureDefinition> featureDefinitions) {
		if (_exportFeatureNames) {
			try {
				if (_exportInstanceIds) {
					_out.write("ID,");
				}
				Iterator<FeatureDefinition> featureDefinitionsIterator = featureDefinitions.iterator();

				while (featureDefinitionsIterator.hasNext()) {
					_out.write(featureDefinitionsIterator.next().getName());
					if (featureDefinitionsIterator.hasNext()) {
						_out.write(",");
					}
				}
				_out.write("\n");
			} catch (IOException e) {
				throw new UncheckedIOException(e);
			}
		}
	}

	@Override
	public String getExportLocation() {
		return _exportLocation;
	}
}
