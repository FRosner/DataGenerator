package de.frosner.datagenerator.gui.main;

import java.io.File;

public class DummyDirectory extends File {

	private static final long serialVersionUID = 1L;

	public DummyDirectory(String dirName) {
		super(dirName);
	}

	@Override
	public boolean isDirectory() {
		return true;
	}

}
