package de.frosner.datagenerator.gui;

import javax.swing.SwingUtilities;

public final class SwingLauncher {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				SwingMenu ex = new SwingMenu();
				ex.setVisible(true);
			}
		});
	}

}
