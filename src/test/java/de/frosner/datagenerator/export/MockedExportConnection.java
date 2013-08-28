package de.frosner.datagenerator.export;

import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.List;

import com.google.common.collect.Lists;

import de.frosner.datagenerator.main.Instance;

public class MockedExportConnection implements ExportConnection {

	private List<Instance> _exportedInstances = Lists.newArrayList();
	private List<Instance> _expectedInstances = Lists.newArrayList();
	private boolean _closed = false;

	@Override
	public void export(Instance instance) {
		_exportedInstances.add(instance);
	}

	@Override
	public void close() throws IOException {
		_closed = true;
	}

	public void addExpectedExport(Instance instance) {
		_expectedInstances.add(instance);
	}

	public void verify() {
		if (!_closed) {
			fail("Connection not closed.");
		}
		assertThat(_expectedInstances).isEqualTo(_exportedInstances);
	}

}
