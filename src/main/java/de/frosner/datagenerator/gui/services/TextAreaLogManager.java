package de.frosner.datagenerator.gui.services;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JEditorPane;
import javax.swing.SwingUtilities;
import javax.swing.text.BadLocationException;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

import de.frosner.datagenerator.exceptions.UncheckedBadLocationException;
import de.frosner.datagenerator.exceptions.UncheckedIOException;

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
					insertMessageAtEndOfHtmlDocument(message, HtmlColor.BLACK);
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
					insertMessageAtEndOfHtmlDocument(message, HtmlColor.ORANGE);
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
					insertMessageAtEndOfHtmlDocument(message, HtmlColor.RED);
				}
			});
		}
	}

	private static void insertMessageAtEndOfHtmlDocument(final String message, HtmlColor color) {
		try {
			_editorKit.insertHTML(_doc, _doc.getLength(), "<font color=\"" + color.getHexRepresentation() + "\">["
					+ formatter.format(new Date()) + "] " + message + "</font>", 0, 0, null);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		} catch (BadLocationException e) {
			throw new UncheckedBadLocationException(e);
		}
	}

	private enum HtmlColor {
		RED("#FF0000"), ORANGE("#FFA500"), BLACK("#000000"), GRAY("#808080"), DEFAULT("");
		private final String _hexRepresentation;

		HtmlColor(String hexRepresentation) {
			_hexRepresentation = hexRepresentation;
		}

		public String getHexRepresentation() {
			return _hexRepresentation;
		}

	}

}
