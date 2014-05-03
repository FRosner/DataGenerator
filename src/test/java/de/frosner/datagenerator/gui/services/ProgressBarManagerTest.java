package de.frosner.datagenerator.gui.services;

import static org.fest.assertions.Assertions.assertThat;

import java.awt.AWTException;

import javax.swing.JProgressBar;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import de.frosner.datagenerator.testutils.GuiTestUtil;
import de.frosner.datagenerator.testutils.SwingTests;

@Category(SwingTests.class)
public class ProgressBarManagerTest {

	private GuiTestUtil _testUtil;
	private JProgressBar _progressBar;

	@Before
	public void initGUI() throws AWTException {
		_progressBar = new JProgressBar();
		ProgressBarManager.manageProgressBar(_progressBar);
		_testUtil = new GuiTestUtil();
	}

	@After
	public void unsetProgressBar() {
		ProgressBarManager.stopManaging();
	}

	@Test
	public void testIncreaseAndResetProgress() {
		ProgressBarManager.setProgressBarMaximumValue(100);
		_testUtil.delay();
		assertThat(_progressBar.getMaximum()).isEqualTo(100);
		assertThat(_progressBar.getValue()).isEqualTo(0);
		ProgressBarManager.increaseProgress();
		_testUtil.delay();
		assertThat(_progressBar.getValue()).isEqualTo(1);
		ProgressBarManager.increaseProgress();
		_testUtil.delay();
		assertThat(_progressBar.getValue()).isEqualTo(2);
		ProgressBarManager.resetProgress();
		_testUtil.delay();
		assertThat(_progressBar.getValue()).isEqualTo(0);
	}

	@Test
	public void testIncreaseProgress_moreThanMaximum() {
		ProgressBarManager.setProgressBarMaximumValue(1);
		_testUtil.delay();
		ProgressBarManager.increaseProgress();
		_testUtil.delay();
		assertThat(_progressBar.getValue()).isEqualTo(1);
		ProgressBarManager.increaseProgress();
		_testUtil.delay();
		assertThat(_progressBar.getValue()).isEqualTo(1);
	}

}
