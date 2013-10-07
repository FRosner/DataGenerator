package de.frosner.datagenerator.gui.main;

import javax.swing.SwingWorker;

import de.frosner.datagenerator.export.ExportConfiguration;
import de.frosner.datagenerator.gui.services.DataGeneratorService;

/**
 * {@linkplain SwingWorker} accessing the {@linkplain DataGeneratorService} in a separate thread. It also toggles the
 * generate and abort buttons.
 */
public final class GenerateDataButtonWorker extends SwingWorker<Void, Void> {

	private final int _numberOfInstances;
	private final ExportConfiguration _config;

	/**
	 * Creates a new {@linkplain GenerateDataButtonWorker} that will call
	 * {@linkplain DataGeneratorService#generateData(int, ExportConfiguration)} to generate and export data with the
	 * specified {@linkplain ExportConfiguration}.
	 * 
	 * @param numberOfInstances
	 *            to generate and export
	 * @param configuration
	 *            of the export connection
	 */
	public GenerateDataButtonWorker(int numberOfInstances, ExportConfiguration configuration) {
		_numberOfInstances = numberOfInstances;
		_config = configuration;
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
