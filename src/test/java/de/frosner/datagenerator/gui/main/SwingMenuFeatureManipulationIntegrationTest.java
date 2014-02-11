package de.frosner.datagenerator.gui.main;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;

import de.frosner.datagenerator.gui.services.GraphVisualizationTestUtil;

public class SwingMenuFeatureManipulationIntegrationTest extends SwingMenuIntegrationTest {

	@Before
	public void assertGraphIsEmpty() {
		GraphVisualizationTestUtil.assertGraphIsEmpty();
	}

	@Test
	public void testAddAndRemoveFeature_gaussian() {
		_frameTestUtil.enterText(_frame._featureNameField, "Feature");
		_frameTestUtil.enterText(_frame._gaussianMeanField, "0");
		_frameTestUtil.enterText(_frame._gaussianSigmaField, "1.0");
		_frameTestUtil.selectOption(_frame._distributionSelector, GaussianFeatureEntry.KEY);
		_frameTestUtil.tryToAddEnteredFeature(_frame._addFeatureButton);
		_frameTestUtil.delay(500);

		GraphVisualizationTestUtil.assertNodeExists("Feature");
		assertThat((String) _frame._previewTableModel.getValueAt(0, 0)).isEqualTo("Feature");
		assertThat((String) _frame._previewTableModel.getValueAt(1, 0)).matches("^\\-?[0-9]+.*$");

		_frameTestUtil.selectFeatureDefinitionEntryByName("Feature");
		_frameTestUtil.clickButtonOrItem(_frame._removeFeatureButton);
		_frameTestUtil.delay(500);

		GraphVisualizationTestUtil.assertGraphIsEmpty();
		assertThat((String) _frame._previewTableModel.getValueAt(0, 0)).isEmpty();
		assertThat((String) _frame._previewTableModel.getValueAt(1, 0)).isEmpty();
	}

	@Test
	public void testAddAndRemoveFeature_bernoulli() {
		_frameTestUtil.enterText(_frame._featureNameField, "Feature");
		_frameTestUtil.enterText(_frame._bernoulliProbabilityField, "1.0");
		_frameTestUtil.selectOption(_frame._distributionSelector, BernoulliFeatureEntry.KEY);
		_frameTestUtil.tryToAddEnteredFeature(_frame._addFeatureButton);
		_frameTestUtil.delay(500);

		GraphVisualizationTestUtil.assertNodeExists("Feature");
		assertThat((String) _frame._previewTableModel.getValueAt(0, 0)).isEqualTo("Feature");
		assertThat((String) _frame._previewTableModel.getValueAt(1, 0)).isEqualTo("1");

		_frameTestUtil.selectFeatureDefinitionEntryByName("Feature");
		_frameTestUtil.clickButtonOrItem(_frame._removeFeatureButton);
		_frameTestUtil.delay(500);

		GraphVisualizationTestUtil.assertGraphIsEmpty();
		assertThat((String) _frame._previewTableModel.getValueAt(0, 0)).isEmpty();
		assertThat((String) _frame._previewTableModel.getValueAt(1, 0)).isEmpty();
	}

	@Test
	public void testAddAndRemoveFeature_uniformCategorial() {
		_frameTestUtil.enterText(_frame._featureNameField, "Feature");
		_frameTestUtil.enterText(_frame._uniformCategorialNumberOfStatesField, "1");
		_frameTestUtil.selectOption(_frame._distributionSelector, UniformCategorialFeatureEntry.KEY);
		_frameTestUtil.tryToAddEnteredFeature(_frame._addFeatureButton);
		_frameTestUtil.delay(500);

		GraphVisualizationTestUtil.assertNodeExists("Feature");
		assertThat((String) _frame._previewTableModel.getValueAt(0, 0)).isEqualTo("Feature");
		assertThat((String) _frame._previewTableModel.getValueAt(1, 0)).isEqualTo("0");

		_frameTestUtil.selectFeatureDefinitionEntryByName("Feature");
		_frameTestUtil.clickButtonOrItem(_frame._removeFeatureButton);
		_frameTestUtil.delay(500);

		GraphVisualizationTestUtil.assertGraphIsEmpty();
		assertThat((String) _frame._previewTableModel.getValueAt(0, 0)).isEmpty();
		assertThat((String) _frame._previewTableModel.getValueAt(1, 0)).isEmpty();
	}

	@Test
	public void testAddFeatureWithMenuItem() {
		_frameTestUtil.enterText(_frame._featureNameField, "Feature");
		_frameTestUtil.enterText(_frame._gaussianMeanField, "0");
		_frameTestUtil.enterText(_frame._gaussianSigmaField, "1.0");
		_frameTestUtil.selectOption(_frame._distributionSelector, GaussianFeatureEntry.KEY);
		_frameTestUtil.tryToAddEnteredFeature(_frame._addFeatureMenuItem);
		_frameTestUtil.delay(500);

		GraphVisualizationTestUtil.assertNodeExists("Feature");
		assertThat((String) _frame._previewTableModel.getValueAt(0, 0)).isEqualTo("Feature");
		assertThat((String) _frame._previewTableModel.getValueAt(1, 0)).matches("^\\-?[0-9]+.*$");
	}

	@Test
	public void testEditFeature_gaussian() {
		_frameTestUtil.enterText(_frame._featureNameField, "OldFeatureName");
		_frameTestUtil.enterText(_frame._gaussianMeanField, "0");
		_frameTestUtil.enterText(_frame._gaussianSigmaField, "1.0");
		_frameTestUtil.selectOption(_frame._distributionSelector, GaussianFeatureEntry.KEY);
		_frameTestUtil.tryToAddEnteredFeature(_frame._addFeatureButton);
		_frameTestUtil.delay(500);

		_frameTestUtil.selectFeatureDefinitionEntryByName("OldFeatureName");
		_frameTestUtil.enterText(_frame._featureNameField, "NewFeatureName");
		_frameTestUtil.enterText(_frame._gaussianMeanField, "-1000");
		_frameTestUtil.enterText(_frame._gaussianSigmaField, "2");
		_frameTestUtil.updateSelectedFeature();
		_frameTestUtil.delay(500);

		GraphVisualizationTestUtil.assertNodeExists("NewFeatureName");
		assertThat(GraphVisualizationTestUtil.getNodeCount()).isEqualTo(1);
		assertThat((String) _frame._previewTableModel.getValueAt(0, 0)).isEqualTo("NewFeatureName");
		assertThat((String) _frame._previewTableModel.getValueAt(1, 0)).matches("^\\-[0-9]+.*$");
	}

	@Test
	public void testEditFeature_bernoulli() {
		_frameTestUtil.enterText(_frame._featureNameField, "OldFeatureName");
		_frameTestUtil.enterText(_frame._bernoulliProbabilityField, "0.0");
		_frameTestUtil.selectOption(_frame._distributionSelector, BernoulliFeatureEntry.KEY);
		_frameTestUtil.tryToAddEnteredFeature(_frame._addFeatureButton);
		_frameTestUtil.delay(500);

		_frameTestUtil.selectFeatureDefinitionEntryByName("OldFeatureName");
		_frameTestUtil.enterText(_frame._featureNameField, "NewFeatureName");
		_frameTestUtil.enterText(_frame._bernoulliProbabilityField, "1.0");
		_frameTestUtil.updateSelectedFeature();
		_frameTestUtil.delay(500);

		GraphVisualizationTestUtil.assertNodeExists("NewFeatureName");
		assertThat(GraphVisualizationTestUtil.getNodeCount()).isEqualTo(1);
		assertThat((String) _frame._previewTableModel.getValueAt(0, 0)).isEqualTo("NewFeatureName");
		assertThat((String) _frame._previewTableModel.getValueAt(1, 0)).isEqualTo("1");
	}

	@Test
	public void testEditFeature_uniformCategorial() {
		_frameTestUtil.enterText(_frame._featureNameField, "OldFeatureName");
		_frameTestUtil.enterText(_frame._uniformCategorialNumberOfStatesField, "1");
		_frameTestUtil.selectOption(_frame._distributionSelector, UniformCategorialFeatureEntry.KEY);
		_frameTestUtil.tryToAddEnteredFeature(_frame._addFeatureButton);
		_frameTestUtil.delay(500);

		_frameTestUtil.selectFeatureDefinitionEntryByName("OldFeatureName");
		_frameTestUtil.enterText(_frame._featureNameField, "NewFeatureName");
		_frameTestUtil.enterText(_frame._uniformCategorialNumberOfStatesField, "2");
		_frameTestUtil.updateSelectedFeature();
		_frameTestUtil.delay(500);

		GraphVisualizationTestUtil.assertNodeExists("NewFeatureName");
		assertThat(GraphVisualizationTestUtil.getNodeCount()).isEqualTo(1);
		assertThat((String) _frame._previewTableModel.getValueAt(0, 0)).isEqualTo("NewFeatureName");
		assertThat((String) _frame._previewTableModel.getValueAt(1, 0)).matches("^(0|1)$");
	}

}
