package de.frosner.datagenerator.gui.services;

import java.util.List;

import javax.swing.SwingUtilities;
import javax.swing.table.TableModel;

import de.frosner.datagenerator.export.ExportConnection;
import de.frosner.datagenerator.features.FeatureDefinition;
import de.frosner.datagenerator.features.FeatureValue;
import de.frosner.datagenerator.generator.DataGenerator;
import de.frosner.datagenerator.generator.Instance;
import de.frosner.datagenerator.gui.main.VariableColumnCountTableModel;

/**
 * Service managing the data generation preview {@linkplain TableModel}. It can be used to populate the table with
 * preview data generated from a list of {@linkplain FeatureDefinition} instances.
 */
public final class PreviewTableManager {

	private static VariableColumnCountTableModel _table;
	private static int _originalColumnCount;

	private static class PreviewTableExportConnection extends ExportConnection {

		private VariableColumnCountTableModel _table;
		private int _rowIndex = 1;

		public PreviewTableExportConnection(VariableColumnCountTableModel table) {
			_table = table;
			_originalColumnCount = _table.getColumnCount();
		}

		@Override
		public void exportInstanceStrategy(Instance instance) {
			int columnIndex = 0;
			for (FeatureValue value : instance) {
				if (columnIndex < _table.getColumnCount()) {
					_table.setValueAt(value.getValueAsString(), _rowIndex, columnIndex++);
				}
			}
			_rowIndex++;
		}

		@Override
		public void close() {
		}

		@Override
		public void exportMetaDataStrategy(List<FeatureDefinition> featureDefinitions) {
			int columnIndex = 0;
			for (FeatureDefinition featureDefinition : featureDefinitions) {
				if (columnIndex < _table.getColumnCount()) {
					_table.setValueAt(featureDefinition.getName(), 0, columnIndex++);
				}
			}
		}

		@Override
		public String getExportLocation() {
			return "Preview Table";
		}

	}

	private PreviewTableManager() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Set the preview table to be managed.
	 * 
	 * @param table
	 *            to be managed
	 */
	public static void setPreviewTable(VariableColumnCountTableModel table) {
		_table = table;
	}

	/**
	 * Populate the managed table with generated feature values.
	 * 
	 * @param features
	 *            to generate values from
	 */
	public static void generatePreview(final List<FeatureDefinition> features) {
		clearPreviewTable();
		if (_table != null && !features.isEmpty()) {
			if (features.size() > _table.getColumnCount()) {

			} else if (_table.getColumnCount() > Math.max(features.size(), _originalColumnCount)) {

			}
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					new DataGenerator(_table.getRowCount() - 1, new PreviewTableExportConnection(_table), features)
							.generate();
				}
			});
		}
	}

	private static void clearPreviewTable() {
		if (_table != null) {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					for (int i = 0; i < _table.getColumnCount(); i++) {
						for (int j = 0; j < _table.getRowCount(); j++) {
							_table.setValueAt("", j, i);
						}
					}
				}
			});
		}
	}

}
