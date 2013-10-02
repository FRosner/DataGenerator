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
public class CsvExportConnection implements ExportConnection {

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
	public void close() throws IOException {
		_out.close();
	}

	@Override
	public void exportInstance(Instance instance) throws IOException {
		_alreadyInstancesExported = true;

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
	}

	@Override
	public void exportMetaData(List<FeatureDefinition> featureDefinitions) throws IOException {
		Check.stateIsTrue(!_alreadyInstancesExported, IllegalMethodCallSequenceException.class);
		Check.stateIsTrue(!_metaDataAlreadyExported, MethodNotCallableTwiceException.class);
		_metaDataAlreadyExported = true;

		if (_exportFeatureNames) {
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
		}
	}
}
