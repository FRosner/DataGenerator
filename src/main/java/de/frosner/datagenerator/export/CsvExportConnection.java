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
 * Connection for exporting instances to a CSV file.
 */
public final class CsvExportConnection extends ExportConnection {

	private final BufferedWriter _out;
	private final boolean _exportFeatureNames;
	private final boolean _exportInstanceIds;
	private final String _exportLocation;

	public CsvExportConnection(OutputStream out, boolean exportFeatureNames, boolean exportInstanceIds, String location) {
		_out = new BufferedWriter(new OutputStreamWriter(out));
		_exportFeatureNames = exportFeatureNames;
		_exportInstanceIds = exportInstanceIds;
		_exportLocation = location;
	}

	public CsvExportConnection(OutputStream out, boolean exportFeatureNames, boolean exportInstanceIds) {
		this(out, exportFeatureNames, exportInstanceIds, out.toString());
	}

	@Override
	public void close() {
		try {
			_out.close();
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

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
