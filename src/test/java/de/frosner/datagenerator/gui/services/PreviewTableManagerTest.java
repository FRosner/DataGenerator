package de.frosner.datagenerator.gui.services;

import static org.fest.assertions.Assertions.assertThat;

import java.awt.AWTException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.google.common.collect.Lists;

import de.frosner.datagenerator.distributions.DummyDistribution;
import de.frosner.datagenerator.features.FeatureDefinition;
import de.frosner.datagenerator.generator.FeatureDefinitionGraph;
import de.frosner.datagenerator.gui.main.VariableColumnCountTableModel;
import de.frosner.datagenerator.testutils.GuiTestUtil;
import de.frosner.datagenerator.testutils.SwingTests;

@Category(SwingTests.class)
public class PreviewTableManagerTest {

	private GuiTestUtil _testUtil;
	private VariableColumnCountTableModel _table;

	@Before
	public void initGUI() throws AWTException {
		_table = new VariableColumnCountTableModel(5, 1);
		PreviewTableManager.managePreviewTable(_table);
		_testUtil = new GuiTestUtil();
	}

	@After
	public void unsetPreviewTable() {
		PreviewTableManager.stopManaging();
	}

	@Test
	public void testGeneratePreview() {
		PreviewTableManager.generatePreview(FeatureDefinitionGraph.createFromList(Lists
				.newArrayList(new FeatureDefinition("Test", new DummyDistribution()))));
		_testUtil.delay();
		assertThat(_table.getValueAt(0, 0)).isEqualTo("Test");
		for (int row = 1; row < _table.getRowCount(); row++) {
			assertThat(_table.getValueAt(row, 0)).isEqualTo(DummyDistribution.ANY_SAMPLE.toString());
		}
	}

	@Test
	public void testGeneratePreview_increaseAndDecreaseColumnsOfTableModel() {
		assertThat(_table.getColumnCount()).isEqualTo(1);
		PreviewTableManager.generatePreview(FeatureDefinitionGraph.createFromList(Lists.newArrayList(
				new FeatureDefinition("Test", new DummyDistribution()), new FeatureDefinition("Foo",
						new DummyDistribution()))));
		_testUtil.delay();
		assertThat(_table.getValueAt(0, 0)).isEqualTo("Test");
		assertThat(_table.getValueAt(0, 1)).isEqualTo("Foo");
		for (int row = 1; row < _table.getRowCount(); row++) {
			assertThat(_table.getValueAt(row, 0)).isEqualTo(DummyDistribution.ANY_SAMPLE.toString());
			assertThat(_table.getValueAt(row, 1)).isEqualTo(DummyDistribution.ANY_SAMPLE.toString());
		}
		PreviewTableManager.generatePreview(FeatureDefinitionGraph.createFromList(Lists
				.newArrayList(new FeatureDefinition("Test", new DummyDistribution()))));
		_testUtil.delay();
		assertThat(_table.getColumnCount()).isEqualTo(1);
	}

}
