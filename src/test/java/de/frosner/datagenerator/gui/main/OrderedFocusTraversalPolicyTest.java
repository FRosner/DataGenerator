package de.frosner.datagenerator.gui.main;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.edt.GuiActionRunner.execute;

import java.awt.AWTException;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.edt.GuiTask;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.common.collect.Lists;

import de.frosner.datagenerator.gui.main.SwingMenu.OrderedFocusTraversalPolicy.NoEnabledComponentInFocusOrderException;

public class OrderedFocusTraversalPolicyTest {

	private JButton _button;
	private JTextField _textField;
	private JFrame _frame;

	@BeforeClass
	public static void setUpOnce() {
		FailOnThreadViolationRepaintManager.install();
	}

	@Before
	public void initGUI() {
		_button = execute(new GuiQuery<JButton>() {
			@Override
			public JButton executeInEDT() {
				return new JButton("Button");
			}
		});
		_textField = execute(new GuiQuery<JTextField>() {
			@Override
			public JTextField executeInEDT() {
				return new JTextField("TextField");
			}
		});
		_frame = execute(new GuiQuery<JFrame>() {
			@Override
			public JFrame executeInEDT() {
				return new JFrame("Frame");
			}
		});
		execute(new GuiTask() {
			@Override
			public void executeInEDT() {
				_frame.setLocationRelativeTo(null);
				_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				_frame.setLayout(new FlowLayout());
				_frame.add(_button);
				_frame.add(_textField);
				_frame.setVisible(true);
				_frame.pack();
			}
		});
	}

	@After
	public void destroyGUI() {
		execute(new GuiTask() {
			@Override
			public void executeInEDT() {
				_frame.dispose();
			}
		});
	}

	@Test
	public void testTabbing() throws AWTException {
		final List<Component> tabOrder = Lists.newArrayList();
		tabOrder.add(_button);
		tabOrder.add(_textField);
		execute(new GuiTask() {
			@Override
			public void executeInEDT() {
				_frame.setFocusTraversalPolicy(new SwingMenu.OrderedFocusTraversalPolicy(tabOrder));
			}
		});
		int delay = 20;
		Robot robot = new Robot();
		robot.delay(delay);
		assertThat(_button.isFocusOwner()).isTrue();
		robot.keyPress(KeyEvent.VK_TAB);
		robot.delay(delay);
		robot.keyRelease(KeyEvent.VK_TAB);
		robot.delay(delay);
		assertThat(_textField.isFocusOwner()).isTrue();
	}

	@Test
	public void testTabbing_someElementsDisabled() throws AWTException {
		final List<Component> tabOrder = Lists.newArrayList();
		tabOrder.add(_button);
		tabOrder.add(_textField);
		execute(new GuiTask() {
			@Override
			public void executeInEDT() {
				_textField.setEnabled(false);
				_frame.setFocusTraversalPolicy(new SwingMenu.OrderedFocusTraversalPolicy(tabOrder));
			}
		});
		int delay = 20;
		Robot robot = new Robot();
		robot.delay(delay);
		assertThat(_button.isFocusOwner()).isTrue();
		robot.keyPress(KeyEvent.VK_TAB);
		robot.delay(delay);
		robot.keyRelease(KeyEvent.VK_TAB);
		robot.delay(delay);
		assertThat(_button.isFocusOwner()).isTrue();
	}

	@Test(expected = NoEnabledComponentInFocusOrderException.class)
	public void testTabbing_noEnabledElements() throws AWTException {
		final List<Component> tabOrder = Lists.newArrayList();
		tabOrder.add(_button);
		tabOrder.add(_textField);
		execute(new GuiTask() {
			@Override
			public void executeInEDT() {
				_button.setEnabled(false);
				_textField.setEnabled(false);
				_frame.setFocusTraversalPolicy(new SwingMenu.OrderedFocusTraversalPolicy(tabOrder));
			}
		});
	}
}
