package de.frosner.datagenerator.gui.main;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import net.sf.qualitycheck.Check;

import com.google.common.collect.Lists;

import de.frosner.datagenerator.util.ApplicationMetaData;

public class VariableColumnCountTableModel extends AbstractTableModel {

	private static final long serialVersionUID = ApplicationMetaData.SERIAL_VERSION_UID;

	private final List<List<String>> _elements;

	public VariableColumnCountTableModel(int rowCount, int columnCount) {
		Check.stateIsTrue(rowCount > 0, "Row count must be positive but was: " + rowCount);
		Check.stateIsTrue(columnCount > 0, "Column count must be positive but was: " + columnCount);
		_elements = Lists.newArrayList();
		for (int i = 0; i < columnCount; i++) {
			List<String> column = Lists.newArrayList();
			for (int j = 0; j < rowCount; j++) {
				column.add("");
			}
			_elements.add(column);
		}
	}

	@Override
	public int getColumnCount() {
		return _elements.size();
	}

	@Override
	public int getRowCount() {
		return _elements.get(0).size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return _elements.get(columnIndex).get(rowIndex);
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		Check.stateIsTrue(rowIndex < getRowCount());
		Check.stateIsTrue(columnIndex < getColumnCount());
		fireTableDataChanged();
		_elements.get(columnIndex).set(rowIndex, aValue.toString());
	}

	public void addColumn() {
		List<String> newColumn = Lists.newArrayList();
		for (int i = 0; i < getRowCount(); i++) {
			newColumn.add("");
		}
		_elements.add(newColumn);
	}

	public void removeColumn() {
		_elements.remove(_elements.size() - 1);
	}

}
