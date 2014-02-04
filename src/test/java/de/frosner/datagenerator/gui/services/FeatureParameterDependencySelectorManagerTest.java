package de.frosner.datagenerator.gui.services;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.edt.GuiActionRunner.execute;

import java.awt.AWTException;

import javax.swing.JComboBox;

import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.edt.GuiQuery;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import de.frosner.datagenerator.distributions.DummyDistribution;
import de.frosner.datagenerator.features.FeatureDefinition;
import de.frosner.datagenerator.gui.main.DummyFeatureDefinitionEntry;
import de.frosner.datagenerator.gui.main.FeatureDefinitionEntry;
import de.frosner.datagenerator.gui.main.GaussianFeatureEntry;
import de.frosner.datagenerator.testutils.GuiTestUtil;
import de.frosner.datagenerator.testutils.SwingTests;

@Category(SwingTests.class)
public class FeatureParameterDependencySelectorManagerTest {

	private JComboBox _gaussianMeanComboBox;
	private GuiTestUtil _testUtil;
	private FeatureDefinitionEntry _gaussianEntry;
	private FeatureDefinitionEntry _unacceptableEntry;

	@BeforeClass
	public static void setUpOnce() {
		FailOnThreadViolationRepaintManager.install();
	}

	@Before
	public void initGUI() throws AWTException {
		_gaussianMeanComboBox = execute(new GuiQuery<JComboBox>() {
			@Override
			public JComboBox executeInEDT() {
				return new JComboBox();
			}
		});
		FeatureParameterDependencySelectorManager.manageGaussianMeanSelector(_gaussianMeanComboBox);
		_testUtil = new GuiTestUtil();
	}

	@Before
	public void setUpEntries() {
		_gaussianEntry = new GaussianFeatureEntry(new FeatureDefinition("Gaussian", new DummyDistribution()), "", "");
		_unacceptableEntry = new DummyFeatureDefinitionEntry(new FeatureDefinition("Unacceptable",
				new DummyDistribution()));
	}

	@After
	public void unsetSelectors() {
		FeatureParameterDependencySelectorManager.stopManaging();
	}

	@Test
	public void testAddGaussianFeature() {
		FeatureParameterDependencySelectorManager.addFeatureDefinitionEntry(_gaussianEntry);
		FeatureParameterDependencySelectorManager.addFeatureDefinitionEntry(_unacceptableEntry);
		_testUtil.delay();

		assertThat(_gaussianMeanComboBox.getItemCount()).isEqualTo(1);
		assertThat(_gaussianMeanComboBox.getItemAt(0)).isEqualTo(_gaussianEntry);
	}

	@Test
	public void testRemoveGaussianFeature() {
		FeatureParameterDependencySelectorManager.addFeatureDefinitionEntry(_gaussianEntry);
		_testUtil.delay();
		FeatureParameterDependencySelectorManager.removeFeatureDefinitionEntry(_gaussianEntry);
		_testUtil.delay();

		assertThat(_gaussianMeanComboBox.getItemCount()).isEqualTo(0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testRemoveGaussianFeature_noFeatureAvailable() {
		FeatureParameterDependencySelectorManager.removeFeatureDefinitionEntry(_gaussianEntry);
		_testUtil.delay();

		assertThat(_gaussianMeanComboBox.getItemCount()).isEqualTo(0);
	}

}
