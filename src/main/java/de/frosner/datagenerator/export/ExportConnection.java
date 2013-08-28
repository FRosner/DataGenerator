package de.frosner.datagenerator.export;

import java.io.Closeable;

import de.frosner.datagenerator.main.Instance;

public interface ExportConnection extends Closeable {

	public void export(Instance instance);

}
