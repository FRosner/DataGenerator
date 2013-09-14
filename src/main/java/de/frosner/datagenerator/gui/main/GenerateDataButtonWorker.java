package de.frosner.datagenerator.gui.main;

import java.io.File;

import javax.swing.SwingWorker;

import de.frosner.datagenerator.generator.DataGeneratorService;

public class GenerateDataButtonWorker extends SwingWorker<Void, Void> {

	private final int _numberOfInstances;
	private final File _exportFile;

	public GenerateDataButtonWorker(int numberOfInstances, File exportFile) {
		_numberOfInstances = numberOfInstances;
		_exportFile = exportFile;
	}

	@Override
	protected Void doInBackground() throws Exception {
		SwingLauncher.GUI.enableGenerateDataButton(false);
		SwingLauncher.GUI.enableAbortDataGenerationButton(true);
		DataGeneratorService.INSTANCE.generateData(_numberOfInstances, _exportFile);
		return null;
	}

	@Override
	protected void done() {
		SwingLauncher.GUI.enableGenerateDataButton(true);
		SwingLauncher.GUI.enableAbortDataGenerationButton(false);
		SwingLauncher.GUI.detachGenerateDataButtonWorker();
	}

}
