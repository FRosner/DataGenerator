package de.frosner.datagenerator.gui.main;

import java.io.File;

import javax.swing.filechooser.FileFilter;

/**
 * {@linkplain FileFilter} accepting any file.
 */
public final class AllFileFilter extends FileFilter {

	@Override
	public boolean accept(File f) {
		return true;
	}

	@Override
	public String getDescription() {
		return "Any File (*)";
	}

}
