package de.frosner.datagenerator.gui.services;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.edt.GuiActionRunner.execute;

import java.awt.AWTException;

import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.edt.GuiTask;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import de.frosner.datagenerator.distributions.DummyDistribution;
import de.frosner.datagenerator.features.FeatureDefinition;
import de.frosner.datagenerator.gui.main.DummyFeatureDefinitionEntry;
import de.frosner.datagenerator.gui.main.FeatureDefinitionEntry;
import de.frosner.datagenerator.testutils.GuiTestUtil;
import de.frosner.datagenerator.testutils.SwingTests;

@Category(SwingTests.class)
public class FeatureDefinitionGraphVisualizationManagerTest {

	private GuiTestUtil _testUtil;
	private FeatureDefinitionEntry _entry1;
	private FeatureDefinitionEntry _entry2;

	@BeforeClass
	public static void setUpOnce() {
		FailOnThreadViolationRepaintManager.install();
	}

	@Before
	public void initGUI() throws AWTException {
		execute(new GuiTask() {
			@Override
			public void executeInEDT() {
				FeatureDefinitionGraphVisualizationManager.createNewManagedJGraph();
			}
		});
		_testUtil = new GuiTestUtil();
	}

	@Before
	public void initEntries() {
		_entry1 = new DummyFeatureDefinitionEntry(new FeatureDefinition("1", new DummyDistribution()));
		_entry2 = new DummyFeatureDefinitionEntry(new FeatureDefinition("2", new DummyDistribution()));
	}

	@After
	public void unsetGraph() {
		FeatureDefinitionGraphVisualizationManager.stopManaging();
	}

	@Test
	public void testAddVertex() {
		FeatureDefinitionGraphVisualizationManager.addVertex(_entry1);
		_testUtil.delay();

		assertThat(FeatureDefinitionGraphVisualizationManager._featureGraphModel.containsVertex(_entry1)).isTrue();
	}

	@Test
	public void testRemoveVertex() {
		FeatureDefinitionGraphVisualizationManager.addVertex(_entry1);
		_testUtil.delay();
		FeatureDefinitionGraphVisualizationManager.removeVertex(_entry1);
		_testUtil.delay();

		assertThat(FeatureDefinitionGraphVisualizationManager._featureGraphModel.vertexSet()).isEmpty();
	}

	@Test
	public void testAddEdge() {
		FeatureDefinitionGraphVisualizationManager.addVertex(_entry1);
		_testUtil.delay();
		FeatureDefinitionGraphVisualizationManager.addVertex(_entry2);
		_testUtil.delay();
		FeatureDefinitionGraphVisualizationManager.addEdge(_entry1, _entry2);
		_testUtil.delay();

		assertThat(FeatureDefinitionGraphVisualizationManager._featureGraphModel.containsEdge(_entry1, _entry2))
				.isTrue();
	}

}
