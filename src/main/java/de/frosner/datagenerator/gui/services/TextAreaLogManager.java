package de.frosner.datagenerator.gui.services;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Nonnull;
import javax.swing.JEditorPane;
import javax.swing.SwingUtilities;
import javax.swing.text.BadLocationException;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

import net.sf.qualitycheck.Check;
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
	public static void setLogArea(@Nonnull JEditorPane logArea) {
		_logArea = Check.notNull(logArea, "logArea");
		_doc = (HTMLDocument) _logArea.getDocument();
		_editorKit = (HTMLEditorKit) _logArea.getEditorKit();
	}

	/**
	 * Unset the managed log area.
	 */
	public static void unsetLogArea() {
		_logArea = null;
		_doc = null;
		_editorKit = null;
	}

	/**
	 * Log an information.
	 * 
	 * @param message
	 *            to log
	 * 
	 * @throws UncheckedIOException
	 *             if the underlying {@linkplain HTMLEditorKit} throws an {@linkplain IOException}
	 * @throws UncheckedBadLocationException
	 *             if the underlying {@linkplain HTMLEditorKit} throws a {@linkplain BadLocationException}
	 */
	public static void info(@Nonnull final String message) {
		Check.notNull(message, "message");
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
	 * 
	 * @throws UncheckedIOException
	 *             if the underlying {@linkplain HTMLEditorKit} throws an {@linkplain IOException}
	 * @throws UncheckedBadLocationException
	 *             if the underlying {@linkplain HTMLEditorKit} throws a {@linkplain BadLocationException}
	 */
	public static void warn(@Nonnull final String message) {
		Check.notNull(message, "message");
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
	 * 
	 * @throws UncheckedIOException
	 *             if the underlying {@linkplain HTMLEditorKit} throws an {@linkplain IOException}
	 * @throws UncheckedBadLocationException
	 *             if the underlying {@linkplain HTMLEditorKit} throws a {@linkplain BadLocationException}
	 */
	public static void error(@Nonnull final String message) {
		Check.notNull(message, "message");
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
		RED("#FF0000"), ORANGE("#FF6600"), BLACK("#000000"), GRAY("#808080"), DEFAULT("");
		private final String _hexRepresentation;

		HtmlColor(String hexRepresentation) {
			_hexRepresentation = hexRepresentation;
		}

		public String getHexRepresentation() {
			return _hexRepresentation;
		}

	}

}
