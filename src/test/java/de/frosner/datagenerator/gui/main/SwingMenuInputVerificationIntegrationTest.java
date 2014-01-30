package de.frosner.datagenerator.gui.main;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.edt.GuiActionRunner.execute;

import java.awt.AWTException;
import java.io.File;

import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.edt.GuiTask;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import de.frosner.datagenerator.distributions.BernoulliDistribution;
import de.frosner.datagenerator.distributions.FixedParameter;
import de.frosner.datagenerator.features.FeatureDefinition;
import de.frosner.datagenerator.gui.services.DataGeneratorService;
import de.frosner.datagenerator.gui.verifiers.InputVerifier;

public class SwingMenuInputVerificationIntegrationTest {

	private SwingMenu _frame;
	private SwingMenuTestUtil _frameTestUtil;
	private File _testFile = new File("src/test/resources/" + SwingMenuMiscIntegrationTest.class.getSimpleName() + ".tmp");

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
	public void testVerifyFeatureName_addGaussian() {
		_frameTestUtil.enterText(_frame._gaussianMeanField, "0");
		_frameTestUtil.enterText(_frame._gaussianSigmaField, "1.0");
		_frameTestUtil.selectOption(_frame._addFeatureDistributionSelection, GaussianFeatureEntry.KEY);

		assertThat(
				_frameTestUtil.clickAddButtonAndCheckComponentVerification(_frame._addFeatureButton,
						_frame._featureNameField)).isFalse();
	}

	@Test
	public void testVerifyFeatureName_editGaussian() {
		assertThat(_frame._featureGraph.getModel().getRootCount()).isEqualTo(0);
		_frameTestUtil.enterText(_frame._featureNameField, "FeatureName");
		_frameTestUtil.enterText(_frame._gaussianMeanField, "0");
		_frameTestUtil.enterText(_frame._gaussianSigmaField, "1.0");
		_frameTestUtil.selectOption(_frame._addFeatureDistributionSelection, GaussianFeatureEntry.KEY);
		_frameTestUtil.tryToAddEnteredFeature(_frame._addFeatureButton);
		_frameTestUtil.delay(500);

		_frameTestUtil.selectFeatureDefinitionEntryByName("FeatureName");
		_frameTestUtil.enterText(_frame._featureNameField, "");
		_frameTestUtil.enterText(_frame._gaussianMeanField, "0");
		_frameTestUtil.enterText(_frame._gaussianSigmaField, "1.0");
		assertThat(_frameTestUtil.clickEditButtonAndCheckComponentVerification(_frame._featureNameField)).isFalse();
	}

	@Test
	public void testVerifyFeatureName_addBernoulli() {
		_frameTestUtil.enterText(_frame._bernoulliProbabilityField, "0.4");
		_frameTestUtil.selectOption(_frame._addFeatureDistributionSelection, BernoulliFeatureEntry.KEY);

		assertThat(
				_frameTestUtil.clickAddButtonAndCheckComponentVerification(_frame._addFeatureButton,
						_frame._featureNameField)).isFalse();
	}

	@Test
	public void testVerifyFeatureName_editBernoulli() {
		assertThat(_frame._featureGraph.getModel().getRootCount()).isEqualTo(0);
		_frameTestUtil.enterText(_frame._featureNameField, "FeatureName");
		_frameTestUtil.enterText(_frame._bernoulliProbabilityField, "0.0");
		_frameTestUtil.selectOption(_frame._addFeatureDistributionSelection, BernoulliFeatureEntry.KEY);
		_frameTestUtil.tryToAddEnteredFeature(_frame._addFeatureButton);
		_frameTestUtil.delay(500);

		_frameTestUtil.selectFeatureDefinitionEntryByName("FeatureName");
		_frameTestUtil.enterText(_frame._featureNameField, "");
		_frameTestUtil.enterText(_frame._bernoulliProbabilityField, "0.0");
		assertThat(_frameTestUtil.clickEditButtonAndCheckComponentVerification(_frame._featureNameField)).isFalse();
	}

	@Test
	public void testVerifyFeatureName_addUniformCategorial() {
		_frameTestUtil.enterText(_frame._uniformCategorialNumberOfStatesField, "5");
		_frameTestUtil.selectOption(_frame._addFeatureDistributionSelection, UniformCategorialFeatureEntry.KEY);

		assertThat(
				_frameTestUtil.clickAddButtonAndCheckComponentVerification(_frame._addFeatureButton,
						_frame._featureNameField)).isFalse();
	}

	@Test
	public void testVerifyFeatureName_editUniformCategorial() {
		assertThat(_frame._featureGraph.getModel().getRootCount()).isEqualTo(0);
		_frameTestUtil.enterText(_frame._featureNameField, "FeatureName");
		_frameTestUtil.enterText(_frame._uniformCategorialNumberOfStatesField, "1");
		_frameTestUtil.selectOption(_frame._addFeatureDistributionSelection, UniformCategorialFeatureEntry.KEY);
		_frameTestUtil.tryToAddEnteredFeature(_frame._addFeatureButton);
		_frameTestUtil.delay(500);

		_frameTestUtil.selectFeatureDefinitionEntryByName("FeatureName");
		_frameTestUtil.enterText(_frame._featureNameField, "");
		_frameTestUtil.enterText(_frame._uniformCategorialNumberOfStatesField, "1");
		assertThat(_frameTestUtil.clickEditButtonAndCheckComponentVerification(_frame._featureNameField)).isFalse();
	}

	@Test
	public void testVerifyGaussianMean_addFeature() {
		_frameTestUtil.enterText(_frame._featureNameField, "Feature");
		_frameTestUtil.enterText(_frame._gaussianSigmaField, "1.0");
		_frameTestUtil.selectOption(_frame._addFeatureDistributionSelection, GaussianFeatureEntry.KEY);

		assertThat(
				_frameTestUtil.clickAddButtonAndCheckComponentVerification(_frame._addFeatureButton,
						_frame._gaussianMeanField)).isFalse();
	}

	@Test
	public void testVerifyGaussianMean_editFeature() {
		assertThat(_frame._featureGraph.getModel().getRootCount()).isEqualTo(0);
		_frameTestUtil.enterText(_frame._featureNameField, "FeatureName");
		_frameTestUtil.enterText(_frame._gaussianMeanField, "0");
		_frameTestUtil.enterText(_frame._gaussianSigmaField, "1.0");
		_frameTestUtil.selectOption(_frame._addFeatureDistributionSelection, GaussianFeatureEntry.KEY);
		_frameTestUtil.tryToAddEnteredFeature(_frame._addFeatureButton);
		_frameTestUtil.delay(500);

		_frameTestUtil.selectFeatureDefinitionEntryByName("FeatureName");
		_frameTestUtil.enterText(_frame._featureNameField, "FeatureName");
		_frameTestUtil.enterText(_frame._gaussianMeanField, "");
		_frameTestUtil.enterText(_frame._gaussianSigmaField, "1.0");
		assertThat(_frameTestUtil.clickEditButtonAndCheckComponentVerification(_frame._gaussianMeanField)).isFalse();
	}

	@Test
	public void testVerifyGaussianSigma_addFeature() {
		_frameTestUtil.enterText(_frame._featureNameField, "Feature");
		_frameTestUtil.enterText(_frame._gaussianMeanField, "0");
		_frameTestUtil.selectOption(_frame._addFeatureDistributionSelection, GaussianFeatureEntry.KEY);

		assertThat(
				_frameTestUtil.clickAddButtonAndCheckComponentVerification(_frame._addFeatureButton,
						_frame._gaussianSigmaField)).isFalse();
	}

	@Test
	public void testVerifyGaussianSigma_editFeature() {
		assertThat(_frame._featureGraph.getModel().getRootCount()).isEqualTo(0);
		_frameTestUtil.enterText(_frame._featureNameField, "FeatureName");
		_frameTestUtil.enterText(_frame._gaussianMeanField, "0");
		_frameTestUtil.enterText(_frame._gaussianSigmaField, "1.0");
		_frameTestUtil.selectOption(_frame._addFeatureDistributionSelection, GaussianFeatureEntry.KEY);
		_frameTestUtil.tryToAddEnteredFeature(_frame._addFeatureButton);
		_frameTestUtil.delay(500);

		_frameTestUtil.selectFeatureDefinitionEntryByName("FeatureName");
		_frameTestUtil.enterText(_frame._featureNameField, "FeatureName");
		_frameTestUtil.enterText(_frame._gaussianMeanField, "0");
		_frameTestUtil.enterText(_frame._gaussianSigmaField, "");
		assertThat(_frameTestUtil.clickEditButtonAndCheckComponentVerification(_frame._gaussianSigmaField)).isFalse();
	}

	@Test
	public void testVerifyBernoulliProbability_addFeature() {
		_frameTestUtil.enterText(_frame._featureNameField, "Feature");
		_frameTestUtil.selectOption(_frame._addFeatureDistributionSelection, BernoulliFeatureEntry.KEY);

		assertThat(
				_frameTestUtil.clickAddButtonAndCheckComponentVerification(_frame._addFeatureButton,
						_frame._bernoulliProbabilityField)).isFalse();
	}

	@Test
	public void testVerifyBernoulliProbability_editFeature() {
		assertThat(_frame._featureGraph.getModel().getRootCount()).isEqualTo(0);
		_frameTestUtil.enterText(_frame._featureNameField, "FeatureName");
		_frameTestUtil.enterText(_frame._bernoulliProbabilityField, "0.0");
		_frameTestUtil.selectOption(_frame._addFeatureDistributionSelection, BernoulliFeatureEntry.KEY);
		_frameTestUtil.tryToAddEnteredFeature(_frame._addFeatureButton);
		_frameTestUtil.delay(500);

		_frameTestUtil.selectFeatureDefinitionEntryByName("FeatureName");
		_frameTestUtil.enterText(_frame._featureNameField, "FeatureName");
		_frameTestUtil.enterText(_frame._bernoulliProbabilityField, "");
		assertThat(_frameTestUtil.clickEditButtonAndCheckComponentVerification(_frame._bernoulliProbabilityField))
				.isFalse();
	}

	@Test
	public void testVerifyUniformCategorialNumberOfStates_addFeature() {
		_frameTestUtil.enterText(_frame._featureNameField, "Feature");
		_frameTestUtil.selectOption(_frame._addFeatureDistributionSelection, UniformCategorialFeatureEntry.KEY);

		assertThat(
				_frameTestUtil.clickAddButtonAndCheckComponentVerification(_frame._addFeatureButton,
						_frame._uniformCategorialNumberOfStatesField)).isFalse();
	}

	@Test
	public void testVerifyUniformCategorialNumberOfStates_editFeature() {
		assertThat(_frame._featureGraph.getModel().getRootCount()).isEqualTo(0);
		_frameTestUtil.enterText(_frame._featureNameField, "FeatureName");
		_frameTestUtil.enterText(_frame._uniformCategorialNumberOfStatesField, "1");
		_frameTestUtil.selectOption(_frame._addFeatureDistributionSelection, UniformCategorialFeatureEntry.KEY);
		_frameTestUtil.tryToAddEnteredFeature(_frame._addFeatureButton);
		_frameTestUtil.delay(500);

		_frameTestUtil.selectFeatureDefinitionEntryByName("FeatureName");
		_frameTestUtil.enterText(_frame._featureNameField, "FeatureName");
		_frameTestUtil.enterText(_frame._uniformCategorialNumberOfStatesField, "0");
		assertThat(
				_frameTestUtil
						.clickEditButtonAndCheckComponentVerification(_frame._uniformCategorialNumberOfStatesField))
				.isFalse();
	}

	@Test
	public void testVerifyWithCancel_addFeatureDialog_button() {
		_frameTestUtil.enterText(_frame._featureNameField, "Feature");
		_frameTestUtil.selectOption(_frame._addFeatureDistributionSelection, BernoulliFeatureEntry.KEY);

		_frameTestUtil.openAndCancelAddFeatureDialog(_frame._addFeatureButton);
		assertThat(_frame._featureGraph.getModel().getRootCount()).isEqualTo(0);
		assertThat(_frame._bernoulliProbabilityField.getBackground()).isEqualTo(InputVerifier.INVALID_INPUT_RED);
	}

	@Test
	public void testVerifyWithCancel_addFeatureDialog_menuItem() {
		_frameTestUtil.enterText(_frame._featureNameField, "Feature");
		_frameTestUtil.selectOption(_frame._addFeatureDistributionSelection, BernoulliFeatureEntry.KEY);

		_frameTestUtil.openAndCancelAddFeatureDialog(_frame._addFeatureMenuItem);
		assertThat(_frame._featureGraph.getModel().getRootCount()).isEqualTo(0);
		assertThat(_frame._bernoulliProbabilityField.getBackground()).isEqualTo(InputVerifier.INVALID_INPUT_RED);
	}

	@Test
	public void testVerifyWithCancel_editFeatureDialog() {
		assertThat(_frame._featureGraph.getModel().getRootCount()).isEqualTo(0);
		_frameTestUtil.enterText(_frame._featureNameField, "FeatureName");
		_frameTestUtil.enterText(_frame._bernoulliProbabilityField, "0.0");
		_frameTestUtil.selectOption(_frame._addFeatureDistributionSelection, BernoulliFeatureEntry.KEY);
		_frameTestUtil.tryToAddEnteredFeature(_frame._addFeatureButton);
		_frameTestUtil.delay(500);

		_frameTestUtil.selectFeatureDefinitionEntryByName("FeatureName");
		_frameTestUtil.enterText(_frame._featureNameField, "FeatureName");
		_frameTestUtil.enterText(_frame._bernoulliProbabilityField, "");
		_frameTestUtil.openAndCancelEditFeatureDialog();
		_frameTestUtil.delay(500);

		assertThat(_frame._featureGraph.getModel().getRootCount()).isEqualTo(1);
		assertThat(_frame._featureGraph.getModel().getRootAt(0).toString()).isEqualTo(
				new BernoulliFeatureEntry(new FeatureDefinition("FeatureName", new BernoulliDistribution(
						new FixedParameter<Double>(0d))), "0.0").toString());
		assertThat((String) _frame._previewTableModel.getValueAt(0, 0)).isEqualTo("FeatureName");
		assertThat((String) _frame._previewTableModel.getValueAt(1, 0)).isEqualTo("0");
		assertThat(_frame._bernoulliProbabilityField.getBackground()).isEqualTo(InputVerifier.INVALID_INPUT_RED);
	}

	@Test
	public void testVerifyWithOk_addFeatureDialog_menuItem() {
		_frameTestUtil.enterText(_frame._featureNameField, "Feature");
		_frameTestUtil.selectOption(_frame._addFeatureDistributionSelection, BernoulliFeatureEntry.KEY);

		assertThat(
				_frameTestUtil.clickAddButtonAndCheckComponentVerification(_frame._addFeatureMenuItem,
						_frame._bernoulliProbabilityField)).isFalse();
	}

	@Test
	public void testVerifyFeatureList() {
		assertThat(_testFile).doesNotExist();
		_frameTestUtil.enterText(_frame._numberOfInstancesField, "10");
		_frameTestUtil.selectFileUsingFileChooserDialog(_testFile);

		_frameTestUtil.clickButtonOrItem(_frame._generateDataButton);
		assertThat(_testFile).doesNotExist();
		assertThat(_frame._featureGraph.getBackground()).isEqualTo(InputVerifier.INVALID_INPUT_RED);
	}

	@Test
	public void testVerifyNumberOfInstancesList() {
		assertThat(_testFile).doesNotExist();
		_frameTestUtil.selectFileUsingFileChooserDialog(_testFile);
		_frameTestUtil.enterText(_frame._featureNameField, "Feature");
		_frameTestUtil.enterText(_frame._gaussianMeanField, "0");
		_frameTestUtil.enterText(_frame._gaussianSigmaField, "1.0");
		_frameTestUtil.selectOption(_frame._addFeatureDistributionSelection, GaussianFeatureEntry.KEY);
		_frameTestUtil.tryToAddEnteredFeature(_frame._addFeatureButton);

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
		_frameTestUtil.tryToAddEnteredFeature(_frame._addFeatureButton);
		_frameTestUtil.enterText(_frame._numberOfInstancesField, "10");

		_frameTestUtil.clickButtonOrItem(_frame._generateDataButton);
		assertThat(_frame._exportFileField.getBackground()).isEqualTo(InputVerifier.INVALID_INPUT_RED);
	}

}
