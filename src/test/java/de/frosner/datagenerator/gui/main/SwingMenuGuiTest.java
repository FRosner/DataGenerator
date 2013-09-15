package de.frosner.datagenerator.gui.main;

import static org.fest.assertions.Assertions.assertThat;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;

import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import de.frosner.datagenerator.gui.verifiers.InputVerifier;

public class SwingMenuGuiTest {

	private SwingMenu _frame;
	private SwingMenuTestUtil _frameTestUtil;

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
		_frameTestUtil = new SwingMenuTestUtil(_frame);
	}

	@Test
	public void testVerifyFeatureName() {
		_frameTestUtil.enterText(_frame._gaussianMeanField, "0");
		_frameTestUtil.enterText(_frame._gaussianSigmaField, "1.0");

		_frameTestUtil.clickButton(_frame._addFeatureButton);
		assertThat(_frame._featureListModel.getSize()).isEqualTo(0);
		assertThat(_frame._gaussianNameField.getBackground()).isEqualTo(InputVerifier.INVALID_INPUT_RED);
	}

	@Test
	public void testVerifyFeatureMean() {
		_frameTestUtil.enterText(_frame._gaussianNameField, "Feature");
		_frameTestUtil.enterText(_frame._gaussianSigmaField, "1.0");

		_frameTestUtil.clickButton(_frame._addFeatureButton);
		assertThat(_frame._featureListModel.getSize()).isEqualTo(0);
		assertThat(_frame._gaussianMeanField.getBackground()).isEqualTo(InputVerifier.INVALID_INPUT_RED);
	}

	@Test
	public void testVerifyFeatureSigma() {
		_frameTestUtil.enterText(_frame._gaussianNameField, "Feature");
		_frameTestUtil.enterText(_frame._gaussianMeanField, "0");

		_frameTestUtil.clickButton(_frame._addFeatureButton);
		assertThat(_frame._featureListModel.getSize()).isEqualTo(0);
		assertThat(_frame._gaussianSigmaField.getBackground()).isEqualTo(InputVerifier.INVALID_INPUT_RED);
	}

	@Test
	public void testVerifyFeatureList() {
		File selectedFile = new File("src/test/resources/" + SwingMenuGuiTest.class.getSimpleName() + ".tmp");
		assertThat(selectedFile).doesNotExist();
		_frameTestUtil.enterText(_frame._numberOfInstancesField, "10");
		_frameTestUtil.selectFile(selectedFile);

		_frameTestUtil.clickButton(_frame._generateDataButton);
		assertThat(selectedFile).doesNotExist();
		assertThat(_frame._featureList.getBackground()).isEqualTo(InputVerifier.INVALID_INPUT_RED);
	}

	@Test
	public void testVerifyNumberOfInstancesList() {
		File selectedFile = new File("src/test/resources/" + SwingMenuGuiTest.class.getSimpleName() + ".tmp");
		assertThat(selectedFile).doesNotExist();
		_frameTestUtil.selectFile(selectedFile);
		_frameTestUtil.enterText(_frame._gaussianNameField, "Feature");
		_frameTestUtil.enterText(_frame._gaussianMeanField, "0");
		_frameTestUtil.enterText(_frame._gaussianSigmaField, "1.0");
		_frameTestUtil.clickButton(_frame._addFeatureButton);

		_frameTestUtil.clickButton(_frame._generateDataButton);
		assertThat(selectedFile).doesNotExist();
		assertThat(_frame._numberOfInstancesField.getBackground()).isEqualTo(InputVerifier.INVALID_INPUT_RED);
	}

	@Test
	public void testVerifyExportFile() {
		_frameTestUtil.enterText(_frame._gaussianNameField, "Feature");
		_frameTestUtil.enterText(_frame._gaussianMeanField, "0");
		_frameTestUtil.enterText(_frame._gaussianSigmaField, "1.0");
		_frameTestUtil.clickButton(_frame._addFeatureButton);
		_frameTestUtil.enterText(_frame._numberOfInstancesField, "10");

		_frameTestUtil.clickButton(_frame._generateDataButton);
		assertThat(_frame._exportFileField.getBackground()).isEqualTo(InputVerifier.INVALID_INPUT_RED);
	}

	@Test
	public void testAddAndRemoveFeature() throws InterruptedException {
		assertThat(_frame._featureListModel.getSize()).isEqualTo(0);
		_frameTestUtil.enterText(_frame._gaussianNameField, "Feature");
		_frameTestUtil.enterText(_frame._gaussianMeanField, "0");
		_frameTestUtil.enterText(_frame._gaussianSigmaField, "1.0");
		_frameTestUtil.clickButton(_frame._addFeatureButton);
		assertThat(_frame._featureListModel.get(0)).isEqualTo("Feature");

		_frameTestUtil.selectFeature(0);
		_frameTestUtil.clickButton(_frame._removeFeatureButton);
		assertThat(_frame._featureListModel.getSize()).isEqualTo(0);
	}

	@Test
	public void testSelectExportFile() throws InterruptedException {
		assertThat(_frame._exportFileField.isEditable()).isFalse();
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
		_frameTestUtil.clickButton(_frame._exportFileButton);
		assertThat(_frame._exportFileField.getText()).isEqualTo("t");
	}

	@Test
	public void testLogging() throws InterruptedException {
		TextAreaLogger.info("Test");
		Thread.sleep(250);
		assertThat(_frame._logArea.getText()).contains("Test");
	}

}
