package de.frosner.datagenerator.gui.main;

import java.io.File;

public class DummyDirectory extends File {

	public DummyDirectory(String dirName) {
		super(dirName);
	}

	@Override
	public boolean isDirectory() {
		return true;
	}

}
