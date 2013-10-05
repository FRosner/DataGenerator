package de.frosner.datagenerator.gui.services;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.edt.GuiActionRunner.execute;

import java.awt.AWTException;

import javax.swing.JProgressBar;

import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.edt.GuiQuery;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import de.frosner.datagenerator.gui.main.SwingMenuTestUtil;

public class ProgressBarManagerTest {

	private JProgressBar _progressBar;

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
	}

	@Test
	public void testIncreaseAndResetProgress() {
		ProgressBarManager.setProgressBarMaximumValue(100);
		SwingMenuTestUtil.delay();
		assertThat(_progressBar.getMaximum()).isEqualTo(100);
		assertThat(_progressBar.getValue()).isEqualTo(0);
		ProgressBarManager.increaseProgress();
		SwingMenuTestUtil.delay();
		assertThat(_progressBar.getValue()).isEqualTo(1);
		ProgressBarManager.increaseProgress();
		SwingMenuTestUtil.delay();
		assertThat(_progressBar.getValue()).isEqualTo(2);
		ProgressBarManager.resetProgress();
		SwingMenuTestUtil.delay();
		assertThat(_progressBar.getValue()).isEqualTo(0);
	}

	@Test
	public void testIncreaseProgress_moreThanMaximum() {
		ProgressBarManager.setProgressBarMaximumValue(1);
		SwingMenuTestUtil.delay();
		ProgressBarManager.increaseProgress();
		SwingMenuTestUtil.delay();
		assertThat(_progressBar.getValue()).isEqualTo(1);
		ProgressBarManager.increaseProgress();
		SwingMenuTestUtil.delay();
		assertThat(_progressBar.getValue()).isEqualTo(1);
	}

}
