package de.frosner.datagenerator.export;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Iterator;

import de.frosner.datagenerator.features.FeatureValue;
import de.frosner.datagenerator.main.Instance;

public class CsvExportConnection implements ExportConnection {

	BufferedWriter _out;

	public CsvExportConnection(OutputStream out) {
		_out = new BufferedWriter(new OutputStreamWriter(out));
	}

	@Override
	public void close() throws IOException {
		_out.close();
	}

	@Override
	public void export(Instance instance) throws IOException {
		Iterator<FeatureValue> values = instance.iterator();
		while (values.hasNext()) {
			_out.write(values.next().getValue().toString());
			if (values.hasNext()) {
				_out.write(",");
			}
		}
		_out.write("\n");
	}
}
