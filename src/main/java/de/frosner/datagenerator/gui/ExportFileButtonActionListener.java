package de.frosner.datagenerator.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public final class ExportFileButtonActionListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		SwingLauncher.GUI.openExportFileChooserDialog();
	}

}
