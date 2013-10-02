package de.frosner.datagenerator.gui.main;

import java.io.File;

import javax.swing.filechooser.FileFilter;

/**
 * {@link FileFilter} accepting any file.
 */
public class AllFileFilter extends FileFilter {

	@Override
	public boolean accept(File f) {
		return true;
	}

	@Override
	public String getDescription() {
		return "Any File (*)";
	}

}
