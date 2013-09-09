package de.frosner.datagenerator.gui;

import static org.fest.assertions.Assertions.assertThat;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.fixture.FrameFixture;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class SwingMenuGuiTest {

	private FrameFixture _testFrame;
	private SwingMenu _frame;

	@BeforeClass
	public static void setUpOnce() {
		FailOnThreadViolationRepaintManager.install();
	}

	@Before
	public void setUp() {
		_frame = GuiActionRunner.execute(new GuiQuery<SwingMenu>() {
			@Override
			protected SwingMenu executeInEDT() {
				return new SwingMenu();
			}
		});
		_testFrame = new FrameFixture(_frame);
		_testFrame.show();
		_testFrame.target.toFront();
	}

	@After
	public void tearDown() {
		_testFrame.cleanUp();
	}

	@Test
	public void testVerifyFeatureName() {
		_testFrame.textBox(SwingMenu.TestUtils.FEATURE_MEAN_FIELD_NAME).enterText("0");
		_testFrame.textBox(SwingMenu.TestUtils.FEATURE_SIGMA_FIELD_NAME).enterText("1.0");

		_testFrame.textBox(SwingMenu.TestUtils.FEATURE_NAME_FIELD_NAME).enterText("");
		_frame.testUtils().clickAddFeatureButton();
		assertThat(_frame.testUtils().getFeatureDefinitionListModel().size()).isEqualTo(0);
	}

	@Test
	public void testVerifyFeatureMean() {
		_testFrame.textBox(SwingMenu.TestUtils.FEATURE_NAME_FIELD_NAME).enterText("Feature 1");
		_testFrame.textBox(SwingMenu.TestUtils.FEATURE_SIGMA_FIELD_NAME).enterText("1.0");

		_testFrame.textBox(SwingMenu.TestUtils.FEATURE_MEAN_FIELD_NAME).enterText("");
		_frame.testUtils().clickAddFeatureButton();
		assertThat(_frame.testUtils().getFeatureDefinitionListModel().size()).isEqualTo(0);
	}

	@Test
	public void testVerifyFeatureSigma() {
		_testFrame.textBox(SwingMenu.TestUtils.FEATURE_NAME_FIELD_NAME).enterText("Feature 1");
		_testFrame.textBox(SwingMenu.TestUtils.FEATURE_MEAN_FIELD_NAME).enterText("1");

		_testFrame.textBox(SwingMenu.TestUtils.FEATURE_SIGMA_FIELD_NAME).enterText("");
		_frame.testUtils().clickAddFeatureButton();
		assertThat(_frame.testUtils().getFeatureDefinitionListModel().size()).isEqualTo(0);
	}

	@Test
	public void testAddAndRemoveFeature() throws InterruptedException {
		assertThat(_frame.testUtils().getFeatureDefinitionListModel().size()).isEqualTo(0);
		_testFrame.textBox(SwingMenu.TestUtils.FEATURE_NAME_FIELD_NAME).enterText("Feature 1");
		_testFrame.textBox(SwingMenu.TestUtils.FEATURE_MEAN_FIELD_NAME).enterText("0");
		_testFrame.textBox(SwingMenu.TestUtils.FEATURE_SIGMA_FIELD_NAME).enterText("1.0");
		_frame.testUtils().clickAddFeatureButton();
		assertThat(_frame.testUtils().getFeatureDefinitionListModel().get(0)).isEqualTo("Feature 1");

		_frame.testUtils().selectFeature(0);
		_frame.testUtils().clickRemoveFeatureButton();
		assertThat(_frame.testUtils().getFeatureDefinitionListModel().size()).isEqualTo(0);
	}

	@Test
	public void testSelectExportFile() throws InterruptedException {
		assertThat(_frame.testUtils().getExportFileField().isEditable()).isFalse();
		new Thread(new Runnable() {
			@Override
			public void run() {
				Robot robot;
				try {
					robot = new Robot();
					robot.delay(500);
					int delay = 50;
					robot.keyPress(KeyEvent.VK_ALT);
					robot.delay(delay);
					robot.keyPress(KeyEvent.VK_N);
					robot.delay(delay);
					robot.keyRelease(KeyEvent.VK_ALT);
					robot.delay(delay);
					robot.keyRelease(KeyEvent.VK_N);
					robot.delay(delay);
					robot.keyPress(KeyEvent.VK_T);
					robot.delay(delay);
					robot.keyRelease(KeyEvent.VK_T);
					robot.delay(delay);
					robot.keyPress(KeyEvent.VK_ENTER);
					robot.delay(delay);
					robot.keyRelease(KeyEvent.VK_ENTER);
				} catch (AWTException e) {
					e.printStackTrace();
				}
			}
		}).start();
		_frame.testUtils().clickExportFileDialogButton();
		assertThat(_frame.testUtils().getExportFileField().getText()).isEqualTo("t");
	}

	@Test
	public void testLogging() throws InterruptedException {
		TextAreaLogger.log("Test");
		Thread.sleep(250);
		assertThat(_frame.testUtils().getLog()).contains("Test");
	}

}
