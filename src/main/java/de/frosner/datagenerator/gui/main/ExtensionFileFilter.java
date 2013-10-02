package de.frosner.datagenerator.gui.main;

import java.io.File;

import javax.annotation.Nonnull;
import javax.swing.filechooser.FileFilter;

import net.sf.qualitycheck.Check;

/**
 * {@link FileFilter} that filters files depending on the specified extension.
 */
public class ExtensionFileFilter extends FileFilter {

	private final String _description;
	private final String _extension;

	/**
	 * Creates an {@link ExtensionFileFilter} with the specified description filtering files with the specified
	 * extension. Extensions have to be without. That is "csv" instead of ".csv".
	 * 
	 * @param description
	 * @param extension
	 */
	public ExtensionFileFilter(@Nonnull String description, @Nonnull String extension) {
		_description = Check.notNull(description);
		_extension = Check.notNull(extension);
	}

	@Override
	public boolean accept(File f) {
		return f.isDirectory() || f.getAbsolutePath().endsWith("." + _extension);
	}

	@Override
	public String getDescription() {
		return _description;
	}

}
