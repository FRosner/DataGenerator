package de.frosner.datagenerator.gui.services;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.edt.GuiActionRunner.execute;

import java.awt.AWTException;

import javax.swing.JEditorPane;

import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.edt.GuiQuery;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import de.frosner.datagenerator.gui.main.SwingMenuTestUtil;

public class TextAreaLogManagerTest {
	private JEditorPane _editorPane;
	private static final String END_OF_HTML_DOCUMENT = "</font>\n  </body>\n</html>\n";

	@BeforeClass
	public static void setUpOnce() {
		FailOnThreadViolationRepaintManager.install();
	}

	@Before
	public void initGUI() throws AWTException {
		_editorPane = execute(new GuiQuery<JEditorPane>() {
			@Override
			public JEditorPane executeInEDT() {
				return new JEditorPane("text/html", null);
			}
		});
		TextAreaLogManager.setLogArea(_editorPane);
	}

	@Test
	public void testLogInfoText() {
		TextAreaLogManager.info("Test");
		SwingMenuTestUtil.delay();
		assertThat(_editorPane.getText()).contains("Test" + END_OF_HTML_DOCUMENT);
		TextAreaLogManager.info("Test2");
		SwingMenuTestUtil.delay();
		assertThat(_editorPane.getText()).contains("Test").contains("Test2" + END_OF_HTML_DOCUMENT);
	}

	@Test
	public void testLogInfoColor() {
		TextAreaLogManager.info("Info");
		SwingMenuTestUtil.delay();
		assertThat(_editorPane.getText()).contains("<font color=\"#000000\">").contains("</font>");
	}

	@Test
	public void testLogWarnText() {
		TextAreaLogManager.warn("Test");
		SwingMenuTestUtil.delay();
		assertThat(_editorPane.getText()).contains("Test" + END_OF_HTML_DOCUMENT);
		TextAreaLogManager.warn("Test2");
		SwingMenuTestUtil.delay();
		assertThat(_editorPane.getText()).contains("Test").contains("Test2" + END_OF_HTML_DOCUMENT);
	}

	@Test
	public void testLogWarnColor() {
		TextAreaLogManager.warn("Warning");
		SwingMenuTestUtil.delay();
		assertThat(_editorPane.getText()).contains("<font color=\"#FFA500\">").contains("</font>");
	}

	@Test
	public void testLogErrorText() {
		TextAreaLogManager.error("Test");
		SwingMenuTestUtil.delay();
		assertThat(_editorPane.getText()).contains("Test" + END_OF_HTML_DOCUMENT);
		TextAreaLogManager.error("Test2");
		SwingMenuTestUtil.delay();
		assertThat(_editorPane.getText()).contains("Test").contains("Test2" + END_OF_HTML_DOCUMENT);
	}

	@Test
	public void testLogErrorColor() {
		TextAreaLogManager.error("Error");
		SwingMenuTestUtil.delay();
		assertThat(_editorPane.getText()).contains("<font color=\"#FF0000\">").contains("</font>");
	}

}
