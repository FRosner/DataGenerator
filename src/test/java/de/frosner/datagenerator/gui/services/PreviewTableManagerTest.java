package de.frosner.datagenerator.gui.services;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.edt.GuiActionRunner.execute;

import java.awt.AWTException;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.edt.GuiQuery;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.common.collect.Lists;

import de.frosner.datagenerator.distributions.DummyDistribution;
import de.frosner.datagenerator.features.FeatureDefinition;
import de.frosner.datagenerator.gui.main.SwingMenuTestUtil;

public class PreviewTableManagerTest {

	private TableModel _table;

	@BeforeClass
	public static void setUpOnce() {
		FailOnThreadViolationRepaintManager.install();
	}

	@Before
	public void initGUI() throws AWTException {
		_table = execute(new GuiQuery<TableModel>() {
			@Override
			public TableModel executeInEDT() {
				return new DefaultTableModel();
			}
		});
		PreviewTableManager.setPreviewTable(_table);
	}

	@Test
	public void testGeneratePreview() {
		assertThat(_table.getValueAt(0, 0)).isNull();
		for (int row = 1; row < _table.getRowCount(); row++) {
			assertThat(_table.getValueAt(row, 0)).isNull();
		}
		PreviewTableManager.generatePreview(Lists.newArrayList(new FeatureDefinition("Test", new DummyDistribution())));
		SwingMenuTestUtil.delay();
		assertThat(_table.getValueAt(0, 0)).isEqualTo("Test");
		for (int row = 1; row < _table.getRowCount(); row++) {
			assertThat(_table.getValueAt(row, 0)).isEqualTo(DummyDistribution.ANY_SAMPLE.toString());
		}
	}

}
