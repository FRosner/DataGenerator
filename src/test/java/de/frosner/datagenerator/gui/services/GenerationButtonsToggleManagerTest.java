package de.frosner.datagenerator.gui.services;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.edt.GuiActionRunner.execute;

import java.awt.AWTException;

import javax.swing.JButton;

import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.edt.GuiTask;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import de.frosner.datagenerator.testutils.GuiTestUtil;
import de.frosner.datagenerator.testutils.SwingTests;

@Category(SwingTests.class)
public class GenerationButtonsToggleManagerTest {

	private GuiTestUtil _testUtil;
	private JButton _button1;
	private JButton _button2;

	@BeforeClass
	public static void setUpOnce() {
		FailOnThreadViolationRepaintManager.install();
	}

	@Before
	public void initGUI() throws AWTException {
		_button1 = execute(new GuiQuery<JButton>() {
			@Override
			public JButton executeInEDT() {
				return new JButton("Button1");
			}
		});
		_button2 = execute(new GuiQuery<JButton>() {
			@Override
			public JButton executeInEDT() {
				return new JButton("Button2");
			}
		});
		GenerationButtonsToggleManager.setButtons(_button1, _button2);
		_testUtil = new GuiTestUtil();
	}

	@After
	public void unsetPreviewTable() {
		GenerationButtonsToggleManager.unsetButtons();
	}

	@Test
	public void testToggleButtons_bothEnabled() {
		execute(new GuiTask() {
			@Override
			protected void executeInEDT() throws Throwable {
				_button2.setEnabled(true);
			}
		});
		execute(new GuiTask() {
			@Override
			protected void executeInEDT() throws Throwable {
				_button2.setEnabled(true);
			}
		});
		GenerationButtonsToggleManager.toggle();
		_testUtil.delay();
		assertThat(_button1.isEnabled()).isFalse();
		assertThat(_button2.isEnabled()).isFalse();
		GenerationButtonsToggleManager.toggle();
		_testUtil.delay();
		assertThat(_button1.isEnabled()).isTrue();
		assertThat(_button2.isEnabled()).isTrue();
	}

	@Test
	public void testToggleButtons_oneEnabled() {
		execute(new GuiTask() {
			@Override
			protected void executeInEDT() throws Throwable {
				_button2.setEnabled(true);
			}
		});
		execute(new GuiTask() {
			@Override
			protected void executeInEDT() throws Throwable {
				_button2.setEnabled(false);
			}
		});
		GenerationButtonsToggleManager.toggle();
		_testUtil.delay();
		assertThat(_button1.isEnabled()).isFalse();
		assertThat(_button2.isEnabled()).isTrue();
		GenerationButtonsToggleManager.toggle();
		_testUtil.delay();
		assertThat(_button1.isEnabled()).isTrue();
		assertThat(_button2.isEnabled()).isFalse();
	}

}
