package de.frosner.datagenerator.gui.services;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JEditorPane;
import javax.swing.SwingUtilities;
import javax.swing.text.BadLocationException;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

/**
 * Service managing a text area to log messages to.
 */
public final class TextAreaLogManager {

	private static JEditorPane _logArea;
	private static HTMLDocument _doc;
	private static HTMLEditorKit _editorKit;
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
		_doc = (HTMLDocument) _logArea.getDocument();
		_editorKit = (HTMLEditorKit) _logArea.getEditorKit();
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
					try {
						InsertMessageAtEndOfHtmlDocument(message, HtmlColor.BLACK);
					} catch (BadLocationException e) {
						e.printStackTrace();
					} catch (IOException e) {
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
					try {
						InsertMessageAtEndOfHtmlDocument(message, HtmlColor.ORANGE);
					} catch (BadLocationException e) {
						e.printStackTrace();
					} catch (IOException e) {
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
					try {
						InsertMessageAtEndOfHtmlDocument(message, HtmlColor.RED);
					} catch (BadLocationException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			});
		}
	}

	private static void InsertMessageAtEndOfHtmlDocument(final String message, HtmlColor color)
			throws BadLocationException, IOException {
		_editorKit.insertHTML(_doc, _doc.getLength(), "<font color=\"" + color + "\">[" + formatter.format(new Date())
				+ "] " + message + "</font>", 0, 0, null);
	}

	private enum HtmlColor {
		RED("#FF0000"), ORANGE("#FFA500"), BLACK("#000000"), GRAY("#808080"), DEFAULT("");
		private final String _stringRepresantation;

		HtmlColor(String stringRepresentation) {
			_stringRepresantation = stringRepresentation;
		}

		@Override
		public String toString() {
			return _stringRepresantation;
		}
	}

}
