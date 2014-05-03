package de.frosner.datagenerator.gui.services;

import static org.fest.assertions.Assertions.assertThat;

import java.awt.AWTException;

import javax.swing.JEditorPane;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import de.frosner.datagenerator.testutils.GuiTestUtil;
import de.frosner.datagenerator.testutils.SwingTests;

@Category(SwingTests.class)
public class TextAreaLogManagerTest {

	private GuiTestUtil _testUtil;
	private JEditorPane _editorPane;
	private static final String END_OF_HTML_DOCUMENT = "</font>\n  </body>\n</html>\n";

	@Before
	public void initGUI() throws AWTException {
		_editorPane = new JEditorPane("text/html", null);
		TextAreaLogManager.manageLogArea(_editorPane);
		_testUtil = new GuiTestUtil();
	}

	@After
	public void unsetLogArea() {
		TextAreaLogManager.stopManaging();
	}

	@Test
	public void testLogInfoText() {
		TextAreaLogManager.info("Test");
		_testUtil.delay();
		assertThat(_editorPane.getText()).contains("Test" + END_OF_HTML_DOCUMENT);
		TextAreaLogManager.info("Test2");
		_testUtil.delay();
		assertThat(_editorPane.getText()).contains("Test").contains("Test2" + END_OF_HTML_DOCUMENT);
	}

	@Test
	public void testLogInfoColor() {
		TextAreaLogManager.info("Info");
		_testUtil.delay();
		assertThat(_editorPane.getText()).contains("<font color=\"#000000\">").contains("</font>");
	}

	@Test
	public void testLogWarnText() {
		TextAreaLogManager.warn("Test");
		_testUtil.delay();
		assertThat(_editorPane.getText()).contains("Test" + END_OF_HTML_DOCUMENT);
		TextAreaLogManager.warn("Test2");
		_testUtil.delay();
		assertThat(_editorPane.getText()).contains("Test").contains("Test2" + END_OF_HTML_DOCUMENT);
	}

	@Test
	public void testLogWarnColor() {
		TextAreaLogManager.warn("Warning");
		_testUtil.delay();
		assertThat(_editorPane.getText()).contains("<font color=\"#FF6600\">").contains("</font>");
	}

	@Test
	public void testLogErrorText() {
		TextAreaLogManager.error("Test");
		_testUtil.delay();
		assertThat(_editorPane.getText()).contains("Test" + END_OF_HTML_DOCUMENT);
		TextAreaLogManager.error("Test2");
		_testUtil.delay();
		assertThat(_editorPane.getText()).contains("Test").contains("Test2" + END_OF_HTML_DOCUMENT);
	}

	@Test
	public void testLogErrorColor() {
		TextAreaLogManager.error("Error");
		_testUtil.delay();
		assertThat(_editorPane.getText()).contains("<font color=\"#FF0000\">").contains("</font>");
	}

}
