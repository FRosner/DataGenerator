package de.frosner.datagenerator.gui.main;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.edt.GuiActionRunner.execute;

import java.awt.AWTException;
import java.io.File;
import java.io.IOException;

import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.edt.GuiTask;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import de.frosner.datagenerator.gui.services.DataGeneratorService;
import de.frosner.datagenerator.gui.services.TextAreaLogManager;
import de.frosner.datagenerator.gui.verifiers.InputVerifier;

public class SwingMenuGuiTest {

	private SwingMenu _frame;
	private SwingMenuTestUtil _frameTestUtil;
	private File _testFile = new File("src/test/resources/" + SwingMenuGuiTest.class.getSimpleName() + ".tmp");

	@BeforeClass
	public static void setUpOnce() {
		FailOnThreadViolationRepaintManager.install();
	}

	@Before
	public void setUp() throws AWTException {
		DataGeneratorService.INSTANCE.reset();
		_frame = GuiActionRunner.execute(new GuiQuery<SwingMenu>() {
			@Override
			protected SwingMenu executeInEDT() {
				return new SwingMenu();
			}
		});
		_frameTestUtil = new SwingMenuTestUtil(_frame);
		SwingLauncher.GUI = _frame;
		_frameTestUtil.setExportFileFilter(SwingMenu.ALL_FILE_FILTER);
		if (_testFile.exists()) {
			_testFile.delete();
		}
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
	public void testVerifyFeatureName() {
		_frameTestUtil.enterText(_frame._gaussianMeanField, "0");
		_frameTestUtil.enterText(_frame._gaussianSigmaField, "1.0");

		_frameTestUtil.tryToAddEnteredFeatureAndGiveUp();
		assertThat(_frame._featureListModel.getSize()).isEqualTo(0);
		assertThat(_frame._featureNameField.getBackground()).isEqualTo(InputVerifier.INVALID_INPUT_RED);
	}

	@Test
	public void testVerifyFeatureMean() {
		_frameTestUtil.enterText(_frame._featureNameField, "Feature");
		_frameTestUtil.enterText(_frame._gaussianSigmaField, "1.0");
		_frameTestUtil.selectOption(_frame._addFeatureDistributionSelection, "Gaussian");

		_frameTestUtil.tryToAddEnteredFeatureAndGiveUp();
		assertThat(_frame._featureListModel.getSize()).isEqualTo(0);
		assertThat(_frame._gaussianMeanField.getBackground()).isEqualTo(InputVerifier.INVALID_INPUT_RED);
	}

	@Test
	public void testVerifyFeatureSigma() {
		_frameTestUtil.enterText(_frame._featureNameField, "Feature");
		_frameTestUtil.enterText(_frame._gaussianMeanField, "0");
		_frameTestUtil.selectOption(_frame._addFeatureDistributionSelection, "Gaussian");

		_frameTestUtil.tryToAddEnteredFeatureAndGiveUp();
		assertThat(_frame._featureListModel.getSize()).isEqualTo(0);
		assertThat(_frame._gaussianSigmaField.getBackground()).isEqualTo(InputVerifier.INVALID_INPUT_RED);
	}

	@Test
	public void testVerifyFeatureList() {
		assertThat(_testFile).doesNotExist();
		_frameTestUtil.enterText(_frame._numberOfInstancesField, "10");
		_frameTestUtil.selectFileUsingFileChooserDialog(_testFile);

		_frameTestUtil.clickButton(_frame._generateDataButton);
		assertThat(_testFile).doesNotExist();
		assertThat(_frame._featureList.getBackground()).isEqualTo(InputVerifier.INVALID_INPUT_RED);
	}

	@Test
	public void testVerifyNumberOfInstancesList() {
		assertThat(_testFile).doesNotExist();
		_frameTestUtil.selectFileUsingFileChooserDialog(_testFile);
		_frameTestUtil.enterText(_frame._featureNameField, "Feature");
		_frameTestUtil.enterText(_frame._gaussianMeanField, "0");
		_frameTestUtil.enterText(_frame._gaussianSigmaField, "1.0");
		_frameTestUtil.selectOption(_frame._addFeatureDistributionSelection, "Gaussian");
		_frameTestUtil.addEnteredFeature();

		_frameTestUtil.clickButton(_frame._generateDataButton);
		assertThat(_testFile).doesNotExist();
		assertThat(_frame._numberOfInstancesField.getBackground()).isEqualTo(InputVerifier.INVALID_INPUT_RED);
	}

	@Test
	public void testVerifyExportFile() {
		_frameTestUtil.enterText(_frame._featureNameField, "Feature");
		_frameTestUtil.enterText(_frame._gaussianMeanField, "0");
		_frameTestUtil.enterText(_frame._gaussianSigmaField, "1.0");
		_frameTestUtil.selectOption(_frame._addFeatureDistributionSelection, "Gaussian");
		_frameTestUtil.addEnteredFeature();
		_frameTestUtil.enterText(_frame._numberOfInstancesField, "10");

		_frameTestUtil.clickButton(_frame._generateDataButton);
		assertThat(_frame._exportFileField.getBackground()).isEqualTo(InputVerifier.INVALID_INPUT_RED);
	}

	@Test
	public void testAddAndRemoveFeature() {
		assertThat(_frame._featureListModel.getSize()).isEqualTo(0);
		_frameTestUtil.enterText(_frame._featureNameField, "Feature");
		_frameTestUtil.enterText(_frame._gaussianMeanField, "0");
		_frameTestUtil.enterText(_frame._gaussianSigmaField, "1.0");
		_frameTestUtil.selectOption(_frame._addFeatureDistributionSelection, "Gaussian");
		_frameTestUtil.addEnteredFeature();
		_frameTestUtil.delay(500);
		assertThat(_frame._featureListModel.get(0)).isEqualTo("Feature (Gaussian, Mean = 0.0, Sigma = 1.0)");
		assertThat((String) _frame._previewTableModel.getValueAt(0, 0)).isEqualTo("Feature");
		assertThat((String) _frame._previewTableModel.getValueAt(1, 0)).matches("^\\-?[0-9]+.*$");
		_frameTestUtil.selectFeature(0);
		_frameTestUtil.clickButton(_frame._removeFeatureButton);
		_frameTestUtil.delay(500);
		assertThat(_frame._featureListModel.getSize()).isEqualTo(0);
		assertThat((String) _frame._previewTableModel.getValueAt(0, 0)).isEmpty();
		assertThat((String) _frame._previewTableModel.getValueAt(1, 0)).isEmpty();
	}

	@Test
	public void testSelectExportFile() {
		assertThat(_frame._exportFileField.isEditable()).isFalse();
		_frameTestUtil.selectFileUsingFileChooserDialog(new File("t"));
		assertThat(_frame._exportFileField.getText()).endsWith("t");
	}

	@Test
	public void testSelectExportFile_fileExtensionCsv() {
		_frameTestUtil.setExportFileFilter(SwingMenu.CSV_FILE_FILTER);
		_frameTestUtil.selectFileUsingFileChooserDialog(new File("t"));
		assertThat(_frame._exportFileField.getText()).endsWith("t.csv");
	}

	@Test
	public void testSelectExportFile_fileExtensionNotAddedTwice() throws InterruptedException {
		_frameTestUtil.setExportFileFilter(SwingMenu.CSV_FILE_FILTER);
		_frameTestUtil.selectFileUsingFileChooserDialog(new File("t.csv"));
		assertThat(_frame._exportFileField.getText()).endsWith("t.csv");
	}

	@Test(timeout = 5000)
	public void testGenerateData() throws InterruptedException {
		assertThat(_frame._progressBar.getValue()).isEqualTo(0);
		assertThat(_testFile).doesNotExist();
		_frameTestUtil.enterText(_frame._featureNameField, "Feature");
		_frameTestUtil.enterText(_frame._gaussianMeanField, "0");
		_frameTestUtil.enterText(_frame._gaussianSigmaField, "1");
		_frameTestUtil.selectOption(_frame._addFeatureDistributionSelection, "Gaussian");
		_frameTestUtil.addEnteredFeature();
		_frameTestUtil.selectFileUsingFileChooserDialog(_testFile);
		_frameTestUtil.enterText(_frame._numberOfInstancesField, "10");
		_frameTestUtil.clickButton(_frame._generateDataButton);
		while (!_testFile.exists()) {
			Thread.sleep(50);
		}
		assertThat(_testFile).exists();
		_frameTestUtil.delay(200);
		assertThat(_frame._progressBar.getValue()).isEqualTo(_frame._progressBar.getMaximum());
	}

	@Test(timeout = 5000)
	public void testAbortGeneration() throws InterruptedException {
		assertThat(_frame._progressBar.getValue()).isEqualTo(0);
		assertThat(_testFile).doesNotExist();
		_frameTestUtil.enterText(_frame._featureNameField, "Feature");
		_frameTestUtil.enterText(_frame._gaussianMeanField, "0");
		_frameTestUtil.enterText(_frame._gaussianSigmaField, "1");
		_frameTestUtil.selectOption(_frame._addFeatureDistributionSelection, "Gaussian");
		_frameTestUtil.addEnteredFeature();
		_frameTestUtil.selectFileUsingFileChooserDialog(_testFile);
		_frameTestUtil.enterText(_frame._numberOfInstancesField, "10000000");
		_frameTestUtil.clickButton(_frame._generateDataButton);
		while (!_testFile.exists()) {
			Thread.sleep(50);
		}
		_frameTestUtil.delay(100);
		assertThat(_frame._generateDataButton.isEnabled()).isFalse();
		assertThat(_frame._abortDataGenerationButton.isEnabled()).isTrue();
		_frameTestUtil.clickButton(_frame._abortDataGenerationButton);
		_frameTestUtil.delay(100);
		long fileSize = _testFile.length();
		_frameTestUtil.delay(100);
		assertThat(_testFile).hasSize(fileSize);
		assertThat(_frame._logArea.getText()).contains("Generation aborted.");
		assertThat(_frame._progressBar.getValue()).isGreaterThan(0).isLessThan(100);
	}

	@Test
	public void testSelectedDirectoryEqualsCurrentDirectoryAtStartUp() throws IOException {
		assertThat(_frame._exportFileDialog.getCurrentDirectory().getCanonicalPath()).isEqualTo(
				new File("").getCanonicalPath());
	}

	@Test
	public void testLogging() {
		TextAreaLogManager.info("Test");
		_frameTestUtil.delay(250);
		assertThat(_frame._logArea.getText()).contains("Test");
	}

}
