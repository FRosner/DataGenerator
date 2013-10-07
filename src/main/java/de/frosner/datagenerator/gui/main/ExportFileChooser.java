package de.frosner.datagenerator.gui.main;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

import de.frosner.datagenerator.util.ApplicationMetaData;

/**
 * {@link JFileChooser} for selecting an export file. As the default all file filter is disabled, you must specify at
 * least one custom file filter.
 * <p>
 * Supported filters:
 * <ul>
 * <li>{@linkplain AllFileFilter}</li>
 * <li>{@linkplain ExtensionFileFilter}</li>
 * </ul>
 */
public final class ExportFileChooser extends JFileChooser {

	private static final long serialVersionUID = ApplicationMetaData.SERIAL_VERSION_UID;

	/**
	 * Creates an ExportFileChooser with the current directory as default selection and the specified file filter.
	 * Additional file filters can be added by using {@link ExportFileChooser#addChoosableFileFilter(FileFilter)}.
	 * 
	 * @throws UnsupportedFileFilterException
	 *             if the given file filter is not supported by the {@linkplain ExportFileChooser}.
	 */
	public ExportFileChooser(FileFilter fileFilter) {
		super(new File(System.getProperty("user.dir")));
		checkFileFilterIsSupported(fileFilter);
		setAcceptAllFileFilterUsed(false);
		addChoosableFileFilter(fileFilter);
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

	/**
	 * @throws UnsupportedFileFilterException
	 *             if the given file filter is not supported by the {@linkplain ExportFileChooser}.
	 */
	@Override
	public void addChoosableFileFilter(FileFilter fileFilter) {
		checkFileFilterIsSupported(fileFilter);
		super.addChoosableFileFilter(fileFilter);
	}

	private void addFileExtensionDependingOnSelectedFilter() {
		File selectedFile = getSelectedFile();
		if (getFileFilter() instanceof ExtensionFileFilter) {
			ExtensionFileFilter extensionFileFilter = (ExtensionFileFilter) getFileFilter();
			if (!extensionFileFilter.accept(selectedFile)) {
				setSelectedFile(new File(selectedFile.getPath() + ".csv"));
			}
		} else {
			// no extension added
		}
	}

	private void checkFileFilterIsSupported(FileFilter fileFilter) {
		if (!(fileFilter instanceof AllFileFilter || fileFilter instanceof ExtensionFileFilter)) {
			throw new UnsupportedFileFilterException(fileFilter);
		}
	}
}
