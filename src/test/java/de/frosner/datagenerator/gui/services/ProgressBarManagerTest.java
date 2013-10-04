package de.frosner.datagenerator.gui.services;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.edt.GuiActionRunner.execute;

import java.awt.AWTException;
import java.awt.Robot;

import javax.swing.JProgressBar;

import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.edt.GuiQuery;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import de.frosner.datagenerator.gui.main.SwingMenuTestUtil;

public class ProgressBarManagerTest {

	private JProgressBar _progressBar;
	private Robot _robot;

	@BeforeClass
	public static void setUpOnce() {
		FailOnThreadViolationRepaintManager.install();
	}

	@Before
	public void initGUI() throws AWTException {
		_progressBar = execute(new GuiQuery<JProgressBar>() {
			@Override
			public JProgressBar executeInEDT() {
				return new JProgressBar();
			}
		});
		ProgressBarManager.setProgressBar(_progressBar);
		_robot = new Robot();
	}

	@Test
	public void testIncreaseAndResetProgress() {
		ProgressBarManager.setProgressBarMaximumValue(100);
		_robot.delay(SwingMenuTestUtil.ROBOT_DELAY);
		assertThat(_progressBar.getMaximum()).isEqualTo(100);
		assertThat(_progressBar.getValue()).isEqualTo(0);
		ProgressBarManager.increaseProgress();
		_robot.delay(SwingMenuTestUtil.ROBOT_DELAY);
		assertThat(_progressBar.getValue()).isEqualTo(1);
		ProgressBarManager.increaseProgress();
		_robot.delay(SwingMenuTestUtil.ROBOT_DELAY);
		assertThat(_progressBar.getValue()).isEqualTo(2);
		ProgressBarManager.resetProgress();
		_robot.delay(SwingMenuTestUtil.ROBOT_DELAY);
		assertThat(_progressBar.getValue()).isEqualTo(0);
	}

	@Test
	public void testIncreaseProgress_moreThanMaximum() {
		ProgressBarManager.setProgressBarMaximumValue(1);
		_robot.delay(SwingMenuTestUtil.ROBOT_DELAY);
		ProgressBarManager.increaseProgress();
		_robot.delay(SwingMenuTestUtil.ROBOT_DELAY);
		assertThat(_progressBar.getValue()).isEqualTo(1);
		ProgressBarManager.increaseProgress();
		_robot.delay(SwingMenuTestUtil.ROBOT_DELAY);
		assertThat(_progressBar.getValue()).isEqualTo(1);
	}

}
