package de.frosner.datagenerator.gui.main;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import de.frosner.datagenerator.util.ApplicationMetaData;

public final class SwingLauncher {

	public static SwingMenu GUI;

	private SwingLauncher() {
		throw new UnsupportedOperationException();
	}

	public static void main(String[] args) {
		loadManifestMetaData();

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				setSwingLookAndFeel();
				GUI = new SwingMenu();
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
					} catch (Exception e1) {
						e1.printStackTrace();
						System.exit(1);
					}
				}
			}
		});
	}

	private static void loadManifestMetaData() {
		try {
			Class<SwingLauncher> clazz = SwingLauncher.class;
			String className = clazz.getSimpleName() + ".class";
			String classPath = clazz.getResource(className).toString();
			if (classPath.startsWith("jar")) {
				String manifestPath = classPath.substring(0, classPath.lastIndexOf("!") + 1) + "/META-INF/MANIFEST.MF";
				Manifest manifest;
				manifest = new Manifest(new URL(manifestPath).openStream());
				Attributes attr = manifest.getMainAttributes();

				ApplicationMetaData.setVersion(attr.getValue("Implementation-Version"));
				ApplicationMetaData.setName(attr.getValue("Implementation-Title"));
				ApplicationMetaData.setRevision(attr.getValue("Revision"));
				ApplicationMetaData.setTimestamp(attr.getValue("Timestamp"));
			}
		} catch (MalformedURLException e) {
		} catch (IOException e) {
		}
	}

}
