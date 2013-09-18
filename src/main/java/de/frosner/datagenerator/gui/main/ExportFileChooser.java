package de.frosner.datagenerator.gui.main;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import de.frosner.datagenerator.util.ApplicationMetaData;

public class ExportFileChooser extends JFileChooser {

	private static final long serialVersionUID = ApplicationMetaData.SERIAL_VERSION_UID;

	public ExportFileChooser() {
		super(new File(System.getProperty("user.dir")));
	}

	@Override
	public void approveSelection() {
		if (getDialogType() == OPEN_DIALOG) {
			addFileExtensionDependingOnSelectedFilter();
			File selectedFile = getSelectedFile();
			if ((selectedFile != null) && selectedFile.exists()) {
				JOptionPane.showMessageDialog(this, "The file " + selectedFile.getName()
						+ " already exists. It will be overwritten during generation.", "Warning",
						JOptionPane.WARNING_MESSAGE);
			}
		}

		super.approveSelection();
	}

	private void addFileExtensionDependingOnSelectedFilter() {
		File selectedFile = getSelectedFile();
		if (getFileFilter().equals(SwingMenu.CSV_FILE_FILTER)) {
			if (!selectedFile.getName().endsWith(".csv")) {
				setSelectedFile(new File(selectedFile.getPath() + ".csv"));
			}
		} else if (getFileFilter().equals(SwingMenu.ALL_FILE_FILTER)) {

		} else {
			throw new UnsupportedFileFilterException(getFileFilter());
		}
	}

}
