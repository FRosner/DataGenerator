package de.frosner.datagenerator.gui;

import javax.swing.SwingUtilities;

public final class SwingLauncher {

	public static SwingMenu GUI; 
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				GUI = new SwingMenu();
				GUI.setVisible(true);
			}
		});
	}
	
}
