package de.frosner.datagenerator.gui.main;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;

import de.frosner.datagenerator.distributions.VariableParameter;

public class SwingMenuDataGenerationIntegrationTest extends SwingMenuIntegrationTest {

	@Test(timeout = 10000)
	public void testGenerateData() throws InterruptedException {
		assertThat(_frame._progressBar.getValue()).isEqualTo(0);
		assertThat(_testFile).doesNotExist();

		_frameTestUtil.enterText(_frame._featureNameField, "Feature 1");
		_frameTestUtil.enterText(_frame._gaussianMeanField, "0");
		_frameTestUtil.enterText(_frame._gaussianSigmaField, "1");
		_frameTestUtil.selectOption(_frame._distributionSelector, GaussianFeatureEntry.KEY);
		_frameTestUtil.tryToAddEnteredFeature(_frame._addFeatureButton);

		_frameTestUtil.enterText(_frame._featureNameField, "DependentGaussianFeature");
		_frameTestUtil.selectOption(_frame._gaussianMeanParameterTypeSelector, VariableParameter.KEY);
		_frameTestUtil.selectOption(_frame._gaussianMeanSelector, _frame._gaussianMeanSelector.getItemAt(0));
		_frameTestUtil.enterText(_frame._gaussianSigmaField, "1.0");
		_frameTestUtil.selectOption(_frame._distributionSelector, GaussianFeatureEntry.KEY);
		_frameTestUtil.tryToAddEnteredFeature(_frame._addFeatureButton);

		_frameTestUtil.enterText(_frame._featureNameField, "Feature 2");
		_frameTestUtil.enterText(_frame._bernoulliProbabilityField, "0.4");
		_frameTestUtil.selectOption(_frame._distributionSelector, BernoulliFeatureEntry.KEY);
		_frameTestUtil.tryToAddEnteredFeature(_frame._addFeatureButton);

		_frameTestUtil.enterText(_frame._featureNameField, "Feature 3");
		_frameTestUtil.enterText(_frame._uniformCategorialNumberOfStatesField, "5");
		_frameTestUtil.selectOption(_frame._distributionSelector, UniformCategorialFeatureEntry.KEY);
		_frameTestUtil.tryToAddEnteredFeature(_frame._addFeatureButton);

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

	@Test(timeout = 10000)
	public void testGenerateData_clickMenuItem() throws InterruptedException {
		assertThat(_frame._progressBar.getValue()).isEqualTo(0);
		assertThat(_testFile).doesNotExist();

		_frameTestUtil.enterText(_frame._featureNameField, "Feature");
		_frameTestUtil.enterText(_frame._gaussianMeanField, "0");
		_frameTestUtil.enterText(_frame._gaussianSigmaField, "1");
		_frameTestUtil.selectOption(_frame._distributionSelector, GaussianFeatureEntry.KEY);
		_frameTestUtil.tryToAddEnteredFeature(_frame._addFeatureButton);

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

	@Test(timeout = 10000)
	public void testAbortGeneration() throws InterruptedException {
		assertThat(_frame._progressBar.getValue()).isEqualTo(0);
		assertThat(_testFile).doesNotExist();

		_frameTestUtil.enterText(_frame._featureNameField, "Feature 1");
		_frameTestUtil.enterText(_frame._gaussianMeanField, "0");
		_frameTestUtil.enterText(_frame._gaussianSigmaField, "1");
		_frameTestUtil.selectOption(_frame._distributionSelector, GaussianFeatureEntry.KEY);
		_frameTestUtil.tryToAddEnteredFeature(_frame._addFeatureButton);

		_frameTestUtil.enterText(_frame._featureNameField, "Feature 2");
		_frameTestUtil.enterText(_frame._bernoulliProbabilityField, "0.4");
		_frameTestUtil.selectOption(_frame._distributionSelector, BernoulliFeatureEntry.KEY);
		_frameTestUtil.tryToAddEnteredFeature(_frame._addFeatureButton);

		_frameTestUtil.enterText(_frame._featureNameField, "Feature 3");
		_frameTestUtil.enterText(_frame._uniformCategorialNumberOfStatesField, "5");
		_frameTestUtil.selectOption(_frame._distributionSelector, UniformCategorialFeatureEntry.KEY);
		_frameTestUtil.tryToAddEnteredFeature(_frame._addFeatureButton);

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

}
