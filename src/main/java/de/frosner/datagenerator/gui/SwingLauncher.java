package de.frosner.datagenerator.gui;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import de.frosner.datagenerator.util.ExceptionUtil;

public final class SwingLauncher {

	public static SwingMenu GUI;

	private SwingLauncher() {
		throw new UnsupportedOperationException();
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				setSwingLookAndFeel();
				GUI = new SwingMenu();
				GUI.setVisible(true);
			}

			private void setSwingLookAndFeel() {
				try {
					for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
						if ("Nimbus".equals(info.getName())) {
							UIManager.setLookAndFeel(info.getClassName());
							break;
						}
					}
				} catch (Exception e) {
					try {
						UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
					} catch (Exception e2) {
						ExceptionUtil.uncheckException(e);
					}
				}
			}
		});
	}

}
