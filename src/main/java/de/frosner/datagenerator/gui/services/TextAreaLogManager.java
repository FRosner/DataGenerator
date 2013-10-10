package de.frosner.datagenerator.gui.services;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JEditorPane;
import javax.swing.SwingUtilities;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

/**
 * Service managing a text area to log messages to.
 */
public final class TextAreaLogManager {

	private static JEditorPane _logArea;
	private static Document _doc;
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
	public static void setLogArea(JEditorPane logArea) {
		_logArea = logArea;
		_doc = _logArea.getDocument();
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
					if (_doc.getLength() == 0) {
						newLine = "";
					}
					try {
						_doc.insertString(_doc.getLength(), newLine + "[" + formatter.format(new Date()) + "] "
								+ message, null);
					} catch (BadLocationException e) {
						e.printStackTrace();
					}
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
					if (_doc.getLength() == 0) {
						newLine = "";
					}
					try {
						_doc.insertString(_doc.getLength(), newLine + "[" + formatter.format(new Date()) + "] "
								+ message, null);
					} catch (BadLocationException e) {
						e.printStackTrace();
					}
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
					if (_doc.getLength() == 0) {
						newLine = "";
					}
					try {
						_doc.insertString(_doc.getLength(), newLine + "[" + formatter.format(new Date()) + "] "
								+ message, null);
					} catch (BadLocationException e) {
						e.printStackTrace();
					}
				}
			});
		}
	}

}
