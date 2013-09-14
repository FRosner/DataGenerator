package de.frosner.datagenerator.gui.main;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public final class TextAreaLogger {

	private static JTextArea _logArea;
	private static SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");

	private TextAreaLogger() {
		throw new UnsupportedOperationException();
	}

	public static void setLogArea(JTextArea logArea) {
		_logArea = logArea;
	}

	public static void info(final String message) {
		if (_logArea != null) {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					String newLine = "\n";
					if (_logArea.getText().equals("")) {
						newLine = "";
					}
					_logArea
							.setText(_logArea.getText() + newLine + "[" + formatter.format(new Date()) + "] " + message);
				}
			});
		}
	}

	public static void warn(final String message) {
		if (_logArea != null) {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					String newLine = "\n";
					if (_logArea.getText().equals("")) {
						newLine = "";
					}
					_logArea
							.setText(_logArea.getText() + newLine + "[" + formatter.format(new Date()) + "] " + message);
				}
			});
		}
	}

	public static void error(final String message) {
		if (_logArea != null) {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					String newLine = "\n";
					if (_logArea.getText().equals("")) {
						newLine = "";
					}
					_logArea
							.setText(_logArea.getText() + newLine + "[" + formatter.format(new Date()) + "] " + message);
				}
			});
		}
	}
}
