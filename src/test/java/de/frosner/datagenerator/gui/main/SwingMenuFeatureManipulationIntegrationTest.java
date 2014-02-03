package de.frosner.datagenerator.gui.main;

import static org.fest.assertions.Assertions.assertThat;

import java.util.List;

import org.junit.Test;

import com.google.common.collect.Lists;

import de.frosner.datagenerator.distributions.BernoulliDistribution;
import de.frosner.datagenerator.distributions.CategorialDistribution;
import de.frosner.datagenerator.distributions.FixedParameter;
import de.frosner.datagenerator.distributions.GaussianDistribution;
import de.frosner.datagenerator.features.FeatureDefinition;

public class SwingMenuFeatureManipulationIntegrationTest extends SwingMenuIntegrationTest {

	@Test
	public void testAddAndRemoveFeature_gaussian() {
		assertThat(_frame._featureGraph.getModel().getRootCount()).isEqualTo(0);
		_frameTestUtil.enterText(_frame._featureNameField, "Feature");
		_frameTestUtil.enterText(_frame._gaussianMeanField, "0");
		_frameTestUtil.enterText(_frame._gaussianSigmaField, "1.0");
		_frameTestUtil.selectOption(_frame._distributionSelector, GaussianFeatureEntry.KEY);
		_frameTestUtil.tryToAddEnteredFeature(_frame._addFeatureButton);
		_frameTestUtil.delay(500);
		assertThat(_frame._featureGraph.getModel().getRootAt(0).toString()).isEqualTo(
				new GaussianFeatureEntry(new FeatureDefinition("Feature", new GaussianDistribution(
						new FixedParameter<Double>(0d), new FixedParameter<Double>(1d))), "0", "1.0").toString());
		assertThat((String) _frame._previewTableModel.getValueAt(0, 0)).isEqualTo("Feature");
		assertThat((String) _frame._previewTableModel.getValueAt(1, 0)).matches("^\\-?[0-9]+.*$");
		_frameTestUtil.selectFeatureDefinitionEntryByName("Feature");
		_frameTestUtil.clickButtonOrItem(_frame._removeFeatureButton);
		_frameTestUtil.delay(500);
		assertThat(_frame._featureGraph.getModel().getRootCount()).isEqualTo(0);
		assertThat((String) _frame._previewTableModel.getValueAt(0, 0)).isEmpty();
		assertThat((String) _frame._previewTableModel.getValueAt(1, 0)).isEmpty();
	}

	@Test
	public void testAddAndRemoveFeature_bernoulli() {
		assertThat(_frame._featureGraph.getModel().getRootCount()).isEqualTo(0);
		_frameTestUtil.enterText(_frame._featureNameField, "Feature");
		_frameTestUtil.enterText(_frame._bernoulliProbabilityField, "1.0");
		_frameTestUtil.selectOption(_frame._distributionSelector, BernoulliFeatureEntry.KEY);
		_frameTestUtil.tryToAddEnteredFeature(_frame._addFeatureButton);
		_frameTestUtil.delay(500);
		assertThat(_frame._featureGraph.getModel().getRootAt(0).toString()).isEqualTo(
				new BernoulliFeatureEntry(new FeatureDefinition("Feature", new BernoulliDistribution(
						new FixedParameter<Double>(1d))), "1.0").toString());
		assertThat((String) _frame._previewTableModel.getValueAt(0, 0)).isEqualTo("Feature");
		assertThat((String) _frame._previewTableModel.getValueAt(1, 0)).isEqualTo("1");
		_frameTestUtil.selectFeatureDefinitionEntryByName("Feature");
		_frameTestUtil.clickButtonOrItem(_frame._removeFeatureButton);
		_frameTestUtil.delay(500);
		assertThat(_frame._featureGraph.getModel().getRootCount()).isEqualTo(0);
		assertThat((String) _frame._previewTableModel.getValueAt(0, 0)).isEmpty();
		assertThat((String) _frame._previewTableModel.getValueAt(1, 0)).isEmpty();
	}

	@Test
	public void testAddAndRemoveFeature_uniformCategorial() {
		assertThat(_frame._featureGraph.getModel().getRootCount()).isEqualTo(0);
		_frameTestUtil.enterText(_frame._featureNameField, "Feature");
		_frameTestUtil.enterText(_frame._uniformCategorialNumberOfStatesField, "1");
		_frameTestUtil.selectOption(_frame._distributionSelector, UniformCategorialFeatureEntry.KEY);
		_frameTestUtil.tryToAddEnteredFeature(_frame._addFeatureButton);
		_frameTestUtil.delay(500);
		assertThat(_frame._featureGraph.getModel().getRootAt(0).toString()).isEqualTo(
				new UniformCategorialFeatureEntry(new FeatureDefinition("Feature", new CategorialDistribution(
						new FixedParameter<List<Double>>(Lists.newArrayList(1D)))), "1").toString());
		assertThat((String) _frame._previewTableModel.getValueAt(0, 0)).isEqualTo("Feature");
		assertThat((String) _frame._previewTableModel.getValueAt(1, 0)).isEqualTo("0");
		_frameTestUtil.selectFeatureDefinitionEntryByName("Feature");
		_frameTestUtil.clickButtonOrItem(_frame._removeFeatureButton);
		_frameTestUtil.delay(500);
		assertThat(_frame._featureGraph.getModel().getRootCount()).isEqualTo(0);
		assertThat((String) _frame._previewTableModel.getValueAt(0, 0)).isEmpty();
		assertThat((String) _frame._previewTableModel.getValueAt(1, 0)).isEmpty();
	}

	@Test
	public void testAddFeatureWithMenuItem() {
		assertThat(_frame._featureGraph.getModel().getRootCount()).isEqualTo(0);
		_frameTestUtil.enterText(_frame._featureNameField, "Feature");
		_frameTestUtil.enterText(_frame._gaussianMeanField, "0");
		_frameTestUtil.enterText(_frame._gaussianSigmaField, "1.0");
		_frameTestUtil.selectOption(_frame._distributionSelector, GaussianFeatureEntry.KEY);
		_frameTestUtil.tryToAddEnteredFeature(_frame._addFeatureMenuItem);
		_frameTestUtil.delay(500);
		assertThat(_frame._featureGraph.getModel().getRootAt(0).toString()).isEqualTo(
				new GaussianFeatureEntry(new FeatureDefinition("Feature", new GaussianDistribution(
						new FixedParameter<Double>(0d), new FixedParameter<Double>(1d))), "0", "1.0").toString());
		assertThat((String) _frame._previewTableModel.getValueAt(0, 0)).isEqualTo("Feature");
		assertThat((String) _frame._previewTableModel.getValueAt(1, 0)).matches("^\\-?[0-9]+.*$");
	}

	@Test
	public void testEditFeature_gaussian() {
		assertThat(_frame._featureGraph.getModel().getRootCount()).isEqualTo(0);
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
		assertThat(_frame._featureGraph.getModel().getRootCount()).isEqualTo(1);
		assertThat(_frame._featureGraph.getModel().getRootAt(0).toString()).isEqualTo(
				new GaussianFeatureEntry(new FeatureDefinition("NewFeatureName", new GaussianDistribution(
						new FixedParameter<Double>(-1000d), new FixedParameter<Double>(2d))), "-1000", "2").toString());
		assertThat((String) _frame._previewTableModel.getValueAt(0, 0)).isEqualTo("NewFeatureName");
		assertThat((String) _frame._previewTableModel.getValueAt(1, 0)).matches("^\\-[0-9]+.*$");
	}

	@Test
	public void testEditFeature_bernoulli() {
		assertThat(_frame._featureGraph.getModel().getRootCount()).isEqualTo(0);
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
		assertThat(_frame._featureGraph.getModel().getRootCount()).isEqualTo(1);
		assertThat(_frame._featureGraph.getModel().getRootAt(0).toString()).isEqualTo(
				new BernoulliFeatureEntry(new FeatureDefinition("NewFeatureName", new BernoulliDistribution(
						new FixedParameter<Double>(1d))), "1.0").toString());
		assertThat((String) _frame._previewTableModel.getValueAt(0, 0)).isEqualTo("NewFeatureName");
		assertThat((String) _frame._previewTableModel.getValueAt(1, 0)).isEqualTo("1");
	}

	@Test
	public void testEditFeature_uniformCategorial() {
		assertThat(_frame._featureGraph.getModel().getRootCount()).isEqualTo(0);
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
		assertThat(_frame._featureGraph.getModel().getRootCount()).isEqualTo(1);
		assertThat(_frame._featureGraph.getModel().getRootAt(0).toString()).isEqualTo(
				new UniformCategorialFeatureEntry(new FeatureDefinition("NewFeatureName", new CategorialDistribution(
						new FixedParameter<List<Double>>(Lists.newArrayList(0.5, 0.5)))), "2").toString());
		assertThat((String) _frame._previewTableModel.getValueAt(0, 0)).isEqualTo("NewFeatureName");
		assertThat((String) _frame._previewTableModel.getValueAt(1, 0)).matches("^(0|1)$");
	}

}
