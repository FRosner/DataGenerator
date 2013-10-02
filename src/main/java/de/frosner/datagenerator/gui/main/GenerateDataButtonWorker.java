package de.frosner.datagenerator.gui.main;

import javax.swing.SwingWorker;

import de.frosner.datagenerator.export.CsvExportConfiguration;
import de.frosner.datagenerator.gui.services.DataGeneratorService;

/**
 * {@link SwingWorker} accessing the {@link DataGeneratorService} in a separate thread. It also toggles the generate and
 * abort buttons.
 */
public final class GenerateDataButtonWorker extends SwingWorker<Void, Void> {

	private final int _numberOfInstances;
	private final CsvExportConfiguration _config;

	public GenerateDataButtonWorker(int numberOfInstances, CsvExportConfiguration config) {
		_numberOfInstances = numberOfInstances;
		_config = config;
	}

	@Override
	protected Void doInBackground() {
		SwingLauncher.GUI.enableGenerateDataButton(false);
		SwingLauncher.GUI.enableAbortDataGenerationButton(true);
		DataGeneratorService.INSTANCE.generateData(_numberOfInstances, _config);
		return null;
	}

	@Override
	protected void done() {
		SwingLauncher.GUI.enableGenerateDataButton(true);
		SwingLauncher.GUI.enableAbortDataGenerationButton(false);
		SwingLauncher.GUI.detachGenerateDataButtonWorker();
	}

}
