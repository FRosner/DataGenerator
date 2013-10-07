package de.frosner.datagenerator.gui.services;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.edt.GuiActionRunner.execute;

import java.awt.AWTException;

import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.edt.GuiQuery;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.common.collect.Lists;

import de.frosner.datagenerator.distributions.DummyDistribution;
import de.frosner.datagenerator.features.FeatureDefinition;
import de.frosner.datagenerator.gui.main.SwingMenuTestUtil;
import de.frosner.datagenerator.gui.main.VariableColumnCountTableModel;

public class PreviewTableManagerTest {

	private VariableColumnCountTableModel _table;

	@BeforeClass
	public static void setUpOnce() {
		FailOnThreadViolationRepaintManager.install();
	}

	@Before
	public void initGUI() throws AWTException {
		_table = execute(new GuiQuery<VariableColumnCountTableModel>() {
			@Override
			public VariableColumnCountTableModel executeInEDT() {
				return new VariableColumnCountTableModel(5, 1);
			}
		});
		PreviewTableManager.setPreviewTable(_table);
	}

	@Test
	public void testGeneratePreview() {
		PreviewTableManager.generatePreview(Lists.newArrayList(new FeatureDefinition("Test", new DummyDistribution())));
		SwingMenuTestUtil.delay();
		assertThat(_table.getValueAt(0, 0)).isEqualTo("Test");
		for (int row = 1; row < _table.getRowCount(); row++) {
			assertThat(_table.getValueAt(row, 0)).isEqualTo(DummyDistribution.ANY_SAMPLE.toString());
		}
	}

}
