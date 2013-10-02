package de.frosner.datagenerator.export;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Iterator;
import java.util.List;

import net.sf.qualitycheck.Check;
import de.frosner.datagenerator.features.FeatureDefinition;
import de.frosner.datagenerator.features.FeatureValue;
import de.frosner.datagenerator.generator.Instance;

/**
 * Connection for exporting instances to a CSV file.
 */
public final class CsvExportConnection implements ExportConnection {

	private final BufferedWriter _out;
	private boolean _metaDataAlreadyExported = false;
	private boolean _alreadyInstancesExported = false;
	private final boolean _exportFeatureNames;
	private final boolean _exportInstanceIds;

	public CsvExportConnection(OutputStream out, boolean exportFeatureNames, boolean exportInstanceIds) {
		_out = new BufferedWriter(new OutputStreamWriter(out));
		_exportFeatureNames = exportFeatureNames;
		_exportInstanceIds = exportInstanceIds;
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
	public void exportInstance(Instance instance) {
		_alreadyInstancesExported = true;

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
	public void exportMetaData(List<FeatureDefinition> featureDefinitions) {
		Check.stateIsTrue(!_alreadyInstancesExported, IllegalMethodCallSequenceException.class);
		Check.stateIsTrue(!_metaDataAlreadyExported, MethodNotCallableTwiceException.class);
		_metaDataAlreadyExported = true;

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
}
