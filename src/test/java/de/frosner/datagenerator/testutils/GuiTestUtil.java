package de.frosner.datagenerator.testutils;

import static org.fest.assertions.Fail.fail;
import static org.fest.swing.edt.GuiActionRunner.execute;

import java.awt.AWTException;
import java.awt.Robot;

import javax.swing.AbstractButton;
import javax.swing.JButton;

import org.fest.swing.edt.GuiQuery;
import org.fest.swing.edt.GuiTask;

public class GuiTestUtil {

	protected static final int DIALOG_OPEN_DELAY = 750;
	protected static final int ROBOT_DELAY = 75;

	private Robot _robot;

	public GuiTestUtil() {
		try {
			_robot = new Robot();
		} catch (AWTException e) {
			fail(e.getMessage());
		}
	}

	public void pressAndReleaseKey(int key) {
		_robot.delay(ROBOT_DELAY);
		_robot.keyPress(key);
		_robot.delay(ROBOT_DELAY);
		_robot.keyRelease(key);
		_robot.delay(ROBOT_DELAY);
	}

	public void delay(int ms) {
		_robot.delay(ms);
	}

	public void delay() {
		delay(ROBOT_DELAY);
	}

	public static AbstractButton createNewButton(final String name) {
		return execute(new GuiQuery<JButton>() {
			@Override
			public JButton executeInEDT() {
				return new JButton(name);
			}
		});
	}

	public static void enableButton(final AbstractButton button, final boolean enable) {
		execute(new GuiTask() {
			@Override
			protected void executeInEDT() throws Throwable {
				button.setEnabled(enable);
			}
		});
	}

}
