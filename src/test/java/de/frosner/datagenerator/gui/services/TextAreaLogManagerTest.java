package de.frosner.datagenerator.gui.services;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.edt.GuiActionRunner.execute;

import java.awt.AWTException;

import javax.swing.JTextArea;

import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.edt.GuiQuery;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import de.frosner.datagenerator.gui.main.SwingMenuTestUtil;

public class TextAreaLogManagerTest {
	private JTextArea _textArea;

	@BeforeClass
	public static void setUpOnce() {
		FailOnThreadViolationRepaintManager.install();
	}

	@Before
	public void initGUI() throws AWTException {
		_textArea = execute(new GuiQuery<JTextArea>() {
			@Override
			public JTextArea executeInEDT() {
				return new JTextArea();
			}
		});
		TextAreaLogManager.setLogArea(_textArea);
	}

	@Test
	public void testLogInfo() {
		TextAreaLogManager.info("Test");
		SwingMenuTestUtil.delay();
		assertThat(_textArea.getText()).endsWith("Test");
		TextAreaLogManager.info("Test2");
		SwingMenuTestUtil.delay();
		assertThat(_textArea.getText()).contains("\n").contains("Test").endsWith("Test2");
	}

	@Test
	public void testLogWarn() {
		TextAreaLogManager.warn("Test");
		SwingMenuTestUtil.delay();
		assertThat(_textArea.getText()).endsWith("Test");
		TextAreaLogManager.warn("Test2");
		SwingMenuTestUtil.delay();
		assertThat(_textArea.getText()).contains("\n").contains("Test").endsWith("Test2");
	}

	@Test
	public void testLogError() {
		TextAreaLogManager.error("Test");
		SwingMenuTestUtil.delay();
		assertThat(_textArea.getText()).endsWith("Test");
		TextAreaLogManager.error("Test2");
		SwingMenuTestUtil.delay();
		assertThat(_textArea.getText()).contains("\n").contains("Test").endsWith("Test2");
	}
}
