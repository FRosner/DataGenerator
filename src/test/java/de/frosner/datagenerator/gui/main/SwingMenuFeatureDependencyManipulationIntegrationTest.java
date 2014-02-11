package de.frosner.datagenerator.gui.main;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;

import de.frosner.datagenerator.distributions.VariableParameter;
import de.frosner.datagenerator.gui.services.GraphVisualizationTestUtil;

public class SwingMenuFeatureDependencyManipulationIntegrationTest extends SwingMenuIntegrationTest {

	@Test
	public void testAddGaussianFeatureWithGaussianPriorForMean() {
		_frameTestUtil.enterText(_frame._featureNameField, "GaussianFeature");
		_frameTestUtil.enterText(_frame._gaussianMeanField, "0");
		_frameTestUtil.enterText(_frame._gaussianSigmaField, "1.0");
		_frameTestUtil.selectOption(_frame._distributionSelector, GaussianFeatureEntry.KEY);
		_frameTestUtil.tryToAddEnteredFeature(_frame._addFeatureButton);
		_frameTestUtil.delay(500);

		assertThat(_frame._gaussianMeanSelector.getItemCount()).isEqualTo(1);
		assertThat(_frame._gaussianMeanSelector.getItemAt(0).toString()).contains("GaussianFeature");

		_frameTestUtil.enterText(_frame._featureNameField, "DependentGaussianFeature");
		_frameTestUtil.selectOption(_frame._gaussianMeanParameterTypeSelector, VariableParameter.KEY);
		_frameTestUtil.selectOption(_frame._gaussianMeanSelector, _frame._gaussianMeanSelector.getItemAt(0));
		_frameTestUtil.enterText(_frame._gaussianSigmaField, "1.0");
		_frameTestUtil.selectOption(_frame._distributionSelector, GaussianFeatureEntry.KEY);
		_frameTestUtil.tryToAddEnteredFeature(_frame._addFeatureButton);
		_frameTestUtil.delay(500);

		assertThat(_frame._gaussianMeanSelector.getItemCount()).isEqualTo(2);
		assertThat(_frame._gaussianMeanSelector.getItemAt(0).toString()).contains("GaussianFeature");
		assertThat(_frame._gaussianMeanSelector.getItemAt(1).toString()).contains("DependentGaussianFeature");

		GraphVisualizationTestUtil.assertEdgeExists("GaussianFeature", "DependentGaussianFeature");
	}
}
