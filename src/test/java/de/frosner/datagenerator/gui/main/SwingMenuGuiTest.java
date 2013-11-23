package de.frosner.datagenerator.gui.main;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.edt.GuiActionRunner.execute;

import java.awt.AWTException;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.edt.GuiTask;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.google.common.collect.Lists;

import de.frosner.datagenerator.distributions.BernoulliDistribution;
import de.frosner.datagenerator.distributions.CategorialDistribution;
import de.frosner.datagenerator.distributions.GaussianDistribution;
import de.frosner.datagenerator.features.FeatureDefinition;
import de.frosner.datagenerator.gui.services.DataGeneratorService;
import de.frosner.datagenerator.gui.services.TextAreaLogManager;
import de.frosner.datagenerator.gui.verifiers.InputVerifier;
import de.frosner.datagenerator.util.LongRunningSwingTests;

@Category(LongRunningSwingTests.class)
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
		SwingMenuTestUtil.resetComponentManagers();
	}

	@Test
	public void testVerifyFeatureName_gaussian() {
		_frameTestUtil.enterText(_frame._gaussianMeanField, "0");
		_frameTestUtil.enterText(_frame._gaussianSigmaField, "1.0");
		_frameTestUtil.selectOption(_frame._addFeatureDistributionSelection, GaussianFeatureEntry.KEY);

		_frameTestUtil.tryToAddEnteredFeatureAndGiveUp();
		assertThat(_frame._featureListModel.getSize()).isEqualTo(0);
		assertThat(_frame._featureNameField.getBackground()).isEqualTo(InputVerifier.INVALID_INPUT_RED);
	}

	@Test
	public void testVerifyFeatureName_bernoulli() {
		_frameTestUtil.enterText(_frame._bernoulliProbabilityField, "0.4");
		_frameTestUtil.selectOption(_frame._addFeatureDistributionSelection, BernoulliFeatureEntry.KEY);

		_frameTestUtil.tryToAddEnteredFeatureAndGiveUp();
		assertThat(_frame._featureListModel.getSize()).isEqualTo(0);
		assertThat(_frame._featureNameField.getBackground()).isEqualTo(InputVerifier.INVALID_INPUT_RED);
	}

	@Test
	public void testVerifyFeatureName_uniformCategorial() {
		_frameTestUtil.enterText(_frame._uniformCategorialNumberOfStatesField, "5");
		_frameTestUtil.selectOption(_frame._addFeatureDistributionSelection, UniformCategorialFeatureEntry.KEY);

		_frameTestUtil.tryToAddEnteredFeatureAndGiveUp();
		assertThat(_frame._featureListModel.getSize()).isEqualTo(0);
		assertThat(_frame._featureNameField.getBackground()).isEqualTo(InputVerifier.INVALID_INPUT_RED);
	}

	@Test
	public void testVerifyGaussianMean() {
		_frameTestUtil.enterText(_frame._featureNameField, "Feature");
		_frameTestUtil.enterText(_frame._gaussianSigmaField, "1.0");
		_frameTestUtil.selectOption(_frame._addFeatureDistributionSelection, GaussianFeatureEntry.KEY);

		_frameTestUtil.tryToAddEnteredFeatureAndGiveUp();
		assertThat(_frame._featureListModel.getSize()).isEqualTo(0);
		assertThat(_frame._gaussianMeanField.getBackground()).isEqualTo(InputVerifier.INVALID_INPUT_RED);
	}

	@Test
	public void testVerifyGaussianSigma() {
		_frameTestUtil.enterText(_frame._featureNameField, "Feature");
		_frameTestUtil.enterText(_frame._gaussianMeanField, "0");
		_frameTestUtil.selectOption(_frame._addFeatureDistributionSelection, GaussianFeatureEntry.KEY);

		_frameTestUtil.tryToAddEnteredFeatureAndGiveUp();
		assertThat(_frame._featureListModel.getSize()).isEqualTo(0);
		assertThat(_frame._gaussianSigmaField.getBackground()).isEqualTo(InputVerifier.INVALID_INPUT_RED);
	}

	@Test
	public void testVerifyUniformCategorialNumberOfStates() {
		_frameTestUtil.enterText(_frame._featureNameField, "Feature");
		_frameTestUtil.selectOption(_frame._addFeatureDistributionSelection, UniformCategorialFeatureEntry.KEY);

		_frameTestUtil.tryToAddEnteredFeatureAndGiveUp();
		assertThat(_frame._featureListModel.getSize()).isEqualTo(0);
		assertThat(_frame._uniformCategorialNumberOfStatesField.getBackground()).isEqualTo(
				InputVerifier.INVALID_INPUT_RED);
	}

	@Test
	public void testVerifyBernoulliProbability() {
		_frameTestUtil.enterText(_frame._featureNameField, "Feature");
		_frameTestUtil.selectOption(_frame._addFeatureDistributionSelection, BernoulliFeatureEntry.KEY);

		_frameTestUtil.tryToAddEnteredFeatureAndGiveUp();
		assertThat(_frame._featureListModel.getSize()).isEqualTo(0);
		assertThat(_frame._bernoulliProbabilityField.getBackground()).isEqualTo(InputVerifier.INVALID_INPUT_RED);
	}

	@Test
	public void testVerifyAfterEscapingFeatureDefinitionDialog() {
		_frameTestUtil.enterText(_frame._featureNameField, "Feature");
		_frameTestUtil.selectOption(_frame._addFeatureDistributionSelection, BernoulliFeatureEntry.KEY);

		_frameTestUtil.escapeEnteredFeature();
		assertThat(_frame._featureListModel.getSize()).isEqualTo(0);
		assertThat(_frame._bernoulliProbabilityField.getBackground()).isEqualTo(InputVerifier.INVALID_INPUT_RED);
	}

	@Test
	public void testVerifyFeatureList() {
		assertThat(_testFile).doesNotExist();
		_frameTestUtil.enterText(_frame._numberOfInstancesField, "10");
		_frameTestUtil.selectFileUsingFileChooserDialog(_testFile);

		_frameTestUtil.clickButtonOrItem(_frame._generateDataButton);
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
		_frameTestUtil.selectOption(_frame._addFeatureDistributionSelection, GaussianFeatureEntry.KEY);
		_frameTestUtil.addEnteredFeature(_frame._addFeatureButton);

		_frameTestUtil.clickButtonOrItem(_frame._generateDataButton);
		assertThat(_testFile).doesNotExist();
		assertThat(_frame._numberOfInstancesField.getBackground()).isEqualTo(InputVerifier.INVALID_INPUT_RED);
	}

	@Test
	public void testVerifyExportFile() {
		_frameTestUtil.enterText(_frame._featureNameField, "Feature");
		_frameTestUtil.enterText(_frame._gaussianMeanField, "0");
		_frameTestUtil.enterText(_frame._gaussianSigmaField, "1.0");
		_frameTestUtil.selectOption(_frame._addFeatureDistributionSelection, GaussianFeatureEntry.KEY);
		_frameTestUtil.addEnteredFeature(_frame._addFeatureButton);
		_frameTestUtil.enterText(_frame._numberOfInstancesField, "10");

		_frameTestUtil.clickButtonOrItem(_frame._generateDataButton);
		assertThat(_frame._exportFileField.getBackground()).isEqualTo(InputVerifier.INVALID_INPUT_RED);
	}

	@Test
	public void testAddAndRemoveFeature_gaussian() {
		assertThat(_frame._featureListModel.getSize()).isEqualTo(0);
		_frameTestUtil.enterText(_frame._featureNameField, "Feature");
		_frameTestUtil.enterText(_frame._gaussianMeanField, "0");
		_frameTestUtil.enterText(_frame._gaussianSigmaField, "1.0");
		_frameTestUtil.selectOption(_frame._addFeatureDistributionSelection, GaussianFeatureEntry.KEY);
		_frameTestUtil.addEnteredFeature(_frame._addFeatureButton);
		_frameTestUtil.delay(500);
		assertThat(_frame._featureListModel.get(0)).isEqualTo(
				new GaussianFeatureEntry(new FeatureDefinition("Feature", new GaussianDistribution(0, 1)), "0", "1.0"));
		assertThat((String) _frame._previewTableModel.getValueAt(0, 0)).isEqualTo("Feature");
		assertThat((String) _frame._previewTableModel.getValueAt(1, 0)).matches("^\\-?[0-9]+.*$");
		_frameTestUtil.selectFeature(0);
		_frameTestUtil.clickButtonOrItem(_frame._removeFeatureButton);
		_frameTestUtil.delay(500);
		assertThat(_frame._featureListModel.getSize()).isEqualTo(0);
		assertThat((String) _frame._previewTableModel.getValueAt(0, 0)).isEmpty();
		assertThat((String) _frame._previewTableModel.getValueAt(1, 0)).isEmpty();
	}

	@Test
	public void testAddAndRemoveFeature_bernoulli() {
		assertThat(_frame._featureListModel.getSize()).isEqualTo(0);
		_frameTestUtil.enterText(_frame._featureNameField, "Feature");
		_frameTestUtil.enterText(_frame._bernoulliProbabilityField, "1.0");
		_frameTestUtil.selectOption(_frame._addFeatureDistributionSelection, BernoulliFeatureEntry.KEY);
		_frameTestUtil.addEnteredFeature(_frame._addFeatureButton);
		_frameTestUtil.delay(500);
		assertThat(_frame._featureListModel.get(0)).isEqualTo(
				new BernoulliFeatureEntry(new FeatureDefinition("Feature", new BernoulliDistribution(1)), "1.0"));
		assertThat((String) _frame._previewTableModel.getValueAt(0, 0)).isEqualTo("Feature");
		assertThat((String) _frame._previewTableModel.getValueAt(1, 0)).isEqualTo("1");
		_frameTestUtil.selectFeature(0);
		_frameTestUtil.clickButtonOrItem(_frame._removeFeatureButton);
		_frameTestUtil.delay(500);
		assertThat(_frame._featureListModel.getSize()).isEqualTo(0);
		assertThat((String) _frame._previewTableModel.getValueAt(0, 0)).isEmpty();
		assertThat((String) _frame._previewTableModel.getValueAt(1, 0)).isEmpty();
	}

	@Test
	public void testAddAndRemoveFeature_uniformCategorial() {
		assertThat(_frame._featureListModel.getSize()).isEqualTo(0);
		_frameTestUtil.enterText(_frame._featureNameField, "Feature");
		_frameTestUtil.enterText(_frame._uniformCategorialNumberOfStatesField, "1");
		_frameTestUtil.selectOption(_frame._addFeatureDistributionSelection, UniformCategorialFeatureEntry.KEY);
		_frameTestUtil.addEnteredFeature(_frame._addFeatureButton);
		_frameTestUtil.delay(500);
		assertThat(_frame._featureListModel.get(0)).isEqualTo(
				new UniformCategorialFeatureEntry(new FeatureDefinition("Feature", new CategorialDistribution(Lists
						.newArrayList(1D))), "1"));
		assertThat((String) _frame._previewTableModel.getValueAt(0, 0)).isEqualTo("Feature");
		assertThat((String) _frame._previewTableModel.getValueAt(1, 0)).isEqualTo("0");
		_frameTestUtil.selectFeature(0);
		_frameTestUtil.clickButtonOrItem(_frame._removeFeatureButton);
		_frameTestUtil.delay(500);
		assertThat(_frame._featureListModel.getSize()).isEqualTo(0);
		assertThat((String) _frame._previewTableModel.getValueAt(0, 0)).isEmpty();
		assertThat((String) _frame._previewTableModel.getValueAt(1, 0)).isEmpty();
	}

	@Test
	public void testAddFeatureWithMenuItem() {
		assertThat(_frame._featureListModel.getSize()).isEqualTo(0);
		_frameTestUtil.enterText(_frame._featureNameField, "Feature");
		_frameTestUtil.enterText(_frame._gaussianMeanField, "0");
		_frameTestUtil.enterText(_frame._gaussianSigmaField, "1.0");
		_frameTestUtil.selectOption(_frame._addFeatureDistributionSelection, GaussianFeatureEntry.KEY);
		_frameTestUtil.addEnteredFeature(_frame._addFeatureMenuItem);
		_frameTestUtil.delay(500);
		assertThat(_frame._featureListModel.get(0)).isEqualTo(
				new GaussianFeatureEntry(new FeatureDefinition("Feature", new GaussianDistribution(0, 1)), "0", "1.0"));
		assertThat((String) _frame._previewTableModel.getValueAt(0, 0)).isEqualTo("Feature");
		assertThat((String) _frame._previewTableModel.getValueAt(1, 0)).matches("^\\-?[0-9]+.*$");
	}

	@Test
	public void testEditFeature_gaussian() {
		assertThat(_frame._featureListModel.getSize()).isEqualTo(0);
		_frameTestUtil.enterText(_frame._featureNameField, "OldFeatureName");
		_frameTestUtil.enterText(_frame._gaussianMeanField, "0");
		_frameTestUtil.enterText(_frame._gaussianSigmaField, "1.0");
		_frameTestUtil.selectOption(_frame._addFeatureDistributionSelection, GaussianFeatureEntry.KEY);
		_frameTestUtil.addEnteredFeature(_frame._addFeatureButton);
		_frameTestUtil.delay(500);
		_frameTestUtil.selectFeature(0);
		_frameTestUtil.enterText(_frame._featureNameField, "NewFeatureName");
		_frameTestUtil.enterText(_frame._gaussianMeanField, "-1000");
		_frameTestUtil.enterText(_frame._gaussianSigmaField, "2");
		_frameTestUtil.updateSelectedFeature();
		_frameTestUtil.delay(500);
		assertThat(_frame._featureListModel.getSize()).isEqualTo(1);
		assertThat(_frame._featureListModel.get(0)).isEqualTo(
				new GaussianFeatureEntry(new FeatureDefinition("NewFeatureName", new GaussianDistribution(-1000, 2)),
						"-1000", "2"));
		assertThat((String) _frame._previewTableModel.getValueAt(0, 0)).isEqualTo("NewFeatureName");
		assertThat((String) _frame._previewTableModel.getValueAt(1, 0)).matches("^\\-[0-9]+.*$");
	}

	@Test
	public void testEditFeature_bernoulli() {
		assertThat(_frame._featureListModel.getSize()).isEqualTo(0);
		_frameTestUtil.enterText(_frame._featureNameField, "OldFeatureName");
		_frameTestUtil.enterText(_frame._bernoulliProbabilityField, "0.0");
		_frameTestUtil.selectOption(_frame._addFeatureDistributionSelection, BernoulliFeatureEntry.KEY);
		_frameTestUtil.addEnteredFeature(_frame._addFeatureButton);
		_frameTestUtil.delay(500);
		_frameTestUtil.selectFeature(0);
		_frameTestUtil.enterText(_frame._featureNameField, "NewFeatureName");
		_frameTestUtil.enterText(_frame._bernoulliProbabilityField, "1.0");
		_frameTestUtil.updateSelectedFeature();
		_frameTestUtil.delay(500);
		assertThat(_frame._featureListModel.getSize()).isEqualTo(1);
		assertThat(_frame._featureListModel.get(0))
				.isEqualTo(
						new BernoulliFeatureEntry(
								new FeatureDefinition("NewFeatureName", new BernoulliDistribution(1)), "1.0"));
		assertThat((String) _frame._previewTableModel.getValueAt(0, 0)).isEqualTo("NewFeatureName");
		assertThat((String) _frame._previewTableModel.getValueAt(1, 0)).isEqualTo("1");
	}

	@Test
	public void testEditFeature_uniformCategorial() {
		assertThat(_frame._featureListModel.getSize()).isEqualTo(0);
		_frameTestUtil.enterText(_frame._featureNameField, "OldFeatureName");
		_frameTestUtil.enterText(_frame._uniformCategorialNumberOfStatesField, "1");
		_frameTestUtil.selectOption(_frame._addFeatureDistributionSelection, UniformCategorialFeatureEntry.KEY);
		_frameTestUtil.addEnteredFeature(_frame._addFeatureButton);
		_frameTestUtil.delay(500);
		_frameTestUtil.selectFeature(0);
		_frameTestUtil.enterText(_frame._featureNameField, "NewFeatureName");
		_frameTestUtil.enterText(_frame._uniformCategorialNumberOfStatesField, "2");
		_frameTestUtil.updateSelectedFeature();
		_frameTestUtil.delay(500);
		assertThat(_frame._featureListModel.getSize()).isEqualTo(1);
		assertThat(_frame._featureListModel.get(0)).isEqualTo(
				new UniformCategorialFeatureEntry(new FeatureDefinition("NewFeatureName", new CategorialDistribution(
						Lists.newArrayList(0.5, 0.5))), "2"));
		assertThat((String) _frame._previewTableModel.getValueAt(0, 0)).isEqualTo("NewFeatureName");
		assertThat((String) _frame._previewTableModel.getValueAt(1, 0)).matches("^(0|1)$");
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
	public void testGenerateData_clickButton() throws InterruptedException {
		assertThat(_frame._progressBar.getValue()).isEqualTo(0);
		assertThat(_testFile).doesNotExist();
		_frameTestUtil.enterText(_frame._featureNameField, "Feature 1");
		_frameTestUtil.enterText(_frame._gaussianMeanField, "0");
		_frameTestUtil.enterText(_frame._gaussianSigmaField, "1");
		_frameTestUtil.selectOption(_frame._addFeatureDistributionSelection, GaussianFeatureEntry.KEY);
		_frameTestUtil.addEnteredFeature(_frame._addFeatureButton);
		_frameTestUtil.enterText(_frame._featureNameField, "Feature 2");
		_frameTestUtil.enterText(_frame._bernoulliProbabilityField, "0.4");
		_frameTestUtil.selectOption(_frame._addFeatureDistributionSelection, BernoulliFeatureEntry.KEY);
		_frameTestUtil.addEnteredFeature(_frame._addFeatureButton);
		_frameTestUtil.enterText(_frame._featureNameField, "Feature 3");
		_frameTestUtil.enterText(_frame._uniformCategorialNumberOfStatesField, "5");
		_frameTestUtil.selectOption(_frame._addFeatureDistributionSelection, UniformCategorialFeatureEntry.KEY);
		_frameTestUtil.addEnteredFeature(_frame._addFeatureButton);
		_frameTestUtil.selectFileUsingFileChooserDialog(_testFile);
		_frameTestUtil.enterText(_frame._numberOfInstancesField, "10");
		_frameTestUtil.clickButtonOrItem(_frame._generateDataButton);
		while (!_testFile.exists()) {
			Thread.sleep(50);
		}
		assertThat(_testFile).exists();
		_frameTestUtil.delay(200);
		assertThat(_frame._progressBar.getValue()).isEqualTo(_frame._progressBar.getMaximum());
	}

	@Test(timeout = 5000)
	public void testGenerateData_clickMenuItem() throws InterruptedException {
		assertThat(_frame._progressBar.getValue()).isEqualTo(0);
		assertThat(_testFile).doesNotExist();
		_frameTestUtil.enterText(_frame._featureNameField, "Feature 1");
		_frameTestUtil.enterText(_frame._gaussianMeanField, "0");
		_frameTestUtil.enterText(_frame._gaussianSigmaField, "1");
		_frameTestUtil.selectOption(_frame._addFeatureDistributionSelection, GaussianFeatureEntry.KEY);
		_frameTestUtil.addEnteredFeature(_frame._addFeatureButton);
		_frameTestUtil.enterText(_frame._featureNameField, "Feature 2");
		_frameTestUtil.enterText(_frame._bernoulliProbabilityField, "0.4");
		_frameTestUtil.selectOption(_frame._addFeatureDistributionSelection, BernoulliFeatureEntry.KEY);
		_frameTestUtil.addEnteredFeature(_frame._addFeatureButton);
		_frameTestUtil.enterText(_frame._featureNameField, "Feature 3");
		_frameTestUtil.enterText(_frame._uniformCategorialNumberOfStatesField, "5");
		_frameTestUtil.selectOption(_frame._addFeatureDistributionSelection, UniformCategorialFeatureEntry.KEY);
		_frameTestUtil.addEnteredFeature(_frame._addFeatureButton);
		_frameTestUtil.selectFileUsingFileChooserDialog(_testFile);
		_frameTestUtil.enterText(_frame._numberOfInstancesField, "10");
		_frameTestUtil.clickButtonOrItem(_frame._generateDataMenuItem);
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
		_frameTestUtil.enterText(_frame._featureNameField, "Feature 1");
		_frameTestUtil.enterText(_frame._gaussianMeanField, "0");
		_frameTestUtil.enterText(_frame._gaussianSigmaField, "1");
		_frameTestUtil.selectOption(_frame._addFeatureDistributionSelection, GaussianFeatureEntry.KEY);
		_frameTestUtil.addEnteredFeature(_frame._addFeatureButton);
		_frameTestUtil.enterText(_frame._featureNameField, "Feature 2");
		_frameTestUtil.enterText(_frame._bernoulliProbabilityField, "0.4");
		_frameTestUtil.selectOption(_frame._addFeatureDistributionSelection, BernoulliFeatureEntry.KEY);
		_frameTestUtil.addEnteredFeature(_frame._addFeatureButton);
		_frameTestUtil.enterText(_frame._featureNameField, "Feature 3");
		_frameTestUtil.enterText(_frame._uniformCategorialNumberOfStatesField, "5");
		_frameTestUtil.selectOption(_frame._addFeatureDistributionSelection, UniformCategorialFeatureEntry.KEY);
		_frameTestUtil.addEnteredFeature(_frame._addFeatureButton);
		_frameTestUtil.selectFileUsingFileChooserDialog(_testFile);
		_frameTestUtil.enterText(_frame._numberOfInstancesField, "10000000");
		_frameTestUtil.clickButtonOrItem(_frame._generateDataButton);
		while (!_testFile.exists()) {
			Thread.sleep(50);
		}
		_frameTestUtil.delay(100);
		assertThat(_frame._generateDataButton.isEnabled()).isFalse();
		assertThat(_frame._abortDataGenerationButton.isEnabled()).isTrue();
		_frameTestUtil.clickButtonOrItem(_frame._abortDataGenerationButton);
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

	@Ignore("See #172")
	@Test
	public void testExitMenuItem() {
	}

	@Test
	public void testWhetherExitItemIsInFileMenu() {
		assertThat(_frame._menuBar.getMenu(0).getItem(1)).isEqualTo(_frame._closeMenuItem);
	}

	@Test(timeout = 6000)
	public void testChangeToEditModeWhenEditButtonWasClicked() throws InterruptedException {
		assertThat(_frame._featureDefinitionDialog.isInEditMode()).isFalse();

		// dummy feature entry, as at least one feature is needed for editing
		_frameTestUtil.enterText(_frame._featureNameField, "FeatureToEdit");
		_frameTestUtil.enterText(_frame._uniformCategorialNumberOfStatesField, "1");
		_frameTestUtil.selectOption(_frame._addFeatureDistributionSelection, UniformCategorialFeatureEntry.KEY);
		_frameTestUtil.addEnteredFeature(_frame._addFeatureButton);
		_frameTestUtil.delay(500);
		_frameTestUtil.selectFeature(0);

		Thread thread;
		thread = new Thread(new Runnable() {
			@Override
			public void run() {
				_frameTestUtil.delay(500);
				assertThat(_frame._featureDefinitionDialog.isInEditMode()).isTrue();
				_frameTestUtil.pressAndReleaseKey(KeyEvent.VK_ESCAPE);
			}
		});
		thread.start();
		GuiActionRunner.execute(new GuiTask() {
			@Override
			protected void executeInEDT() {
				_frame.actionPerformed(new ActionEvent(_frame._editFeatureButton, 1, ""));
			}
		});
		while (thread.isAlive()) {
			Thread.sleep(50);
		}

		assertThat(_frame._featureDefinitionDialog.isInEditMode()).isFalse();
	}
}
