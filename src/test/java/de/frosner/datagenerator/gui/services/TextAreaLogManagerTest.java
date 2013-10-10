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

	@BeforeClass
	public static void setUpOnce() {
		FailOnThreadViolationRepaintManager.install();
	}

	@Before
	public void initGUI() throws AWTException {
		_editorPane = execute(new GuiQuery<JEditorPane>() {
			@Override
			public JEditorPane executeInEDT() {
				return new JEditorPane();
			}
		});
		TextAreaLogManager.setLogArea(_editorPane);
	}

	@Test
	public void testLogInfoText() {
		TextAreaLogManager.info("Test");
		SwingMenuTestUtil.delay();
		assertThat(_editorPane.getText()).endsWith("Test");
		TextAreaLogManager.info("Test2");
		SwingMenuTestUtil.delay();
		assertThat(_editorPane.getText()).contains("\n").contains("Test").endsWith("Test2");
	}

	@Test
	public void testLogWarnText() {
		TextAreaLogManager.warn("Test");
		SwingMenuTestUtil.delay();
		assertThat(_editorPane.getText()).endsWith("Test");
		TextAreaLogManager.warn("Test2");
		SwingMenuTestUtil.delay();
		assertThat(_editorPane.getText()).contains("\n").contains("Test").endsWith("Test2");
	}

	@Test
	public void testLogErrorText() {
		TextAreaLogManager.error("Test");
		SwingMenuTestUtil.delay();
		assertThat(_editorPane.getText()).endsWith("Test");
		TextAreaLogManager.error("Test2");
		SwingMenuTestUtil.delay();
		assertThat(_editorPane.getText()).contains("\n").contains("Test").endsWith("Test2");
	}
}
