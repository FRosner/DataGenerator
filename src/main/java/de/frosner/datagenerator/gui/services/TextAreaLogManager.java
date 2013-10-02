package de.frosner.datagenerator.gui.services;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

/**
 * Service managing a text area to log messages to.
 */
public final class TextAreaLogManager {

	private static JTextArea _logArea;
	private static final SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");

	private TextAreaLogManager() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Set the text area to manage log to.
	 * 
	 * @param logArea
	 *            to manage
	 */
	public static void setLogArea(JTextArea logArea) {
		_logArea = logArea;
	}

	/**
	 * Log an information.
	 * 
	 * @param message
	 *            to log
	 */
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

	/**
	 * Log a warning message.
	 * 
	 * @param message
	 *            to log
	 */
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

	/**
	 * Log an error message.
	 * 
	 * @param message
	 *            to log
	 */
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
