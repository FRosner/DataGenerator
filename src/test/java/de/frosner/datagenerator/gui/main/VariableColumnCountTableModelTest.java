package de.frosner.datagenerator.gui.main;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;

public class VariableColumnCountTableModelTest {

	private VariableColumnCountTableModel _tableModel;

	@Before
	public void createTableModel() {
		_tableModel = new VariableColumnCountTableModel(2, 1);
	}

	@Test
	public void testCreateWithCorrectNumberOfColumnsAndRows() {
		assertThat(_tableModel.getColumnCount()).isEqualTo(1);
		assertThat(_tableModel.getRowCount()).isEqualTo(2);
	}

	@Test
	public void testAccessElements() {
		assertThat(_tableModel.getValueAt(0, 0)).isEqualTo("");
		assertThat(_tableModel.getValueAt(1, 0)).isEqualTo("");
		_tableModel.setValueAt("Test", 0, 0);
		assertThat(_tableModel.getValueAt(0, 0)).isEqualTo("Test");
	}

	@Test
	public void testAddAndRemoveColumn() {
		assertThat(_tableModel.getColumnCount()).isEqualTo(1);
		_tableModel.addColumn();
		assertThat(_tableModel.getColumnCount()).isEqualTo(2);
		assertThat(_tableModel.getValueAt(0, 0)).isEqualTo("");
		assertThat(_tableModel.getValueAt(1, 0)).isEqualTo("");
		assertThat(_tableModel.getValueAt(0, 1)).isEqualTo("");
		assertThat(_tableModel.getValueAt(1, 1)).isEqualTo("");
		_tableModel.setValueAt("Test", 0, 1);
		assertThat(_tableModel.getValueAt(0, 1)).isEqualTo("Test");
		_tableModel.removeColumn();
		assertThat(_tableModel.getColumnCount()).isEqualTo(1);
		assertThat(_tableModel.getValueAt(0, 0)).isEqualTo("");
		assertThat(_tableModel.getValueAt(1, 0)).isEqualTo("");
	}

}
