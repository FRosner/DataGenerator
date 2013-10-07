package de.frosner.datagenerator.gui.services;

import java.util.List;

import javax.swing.SwingUtilities;
import javax.swing.table.TableModel;

import de.frosner.datagenerator.export.ExportConnection;
import de.frosner.datagenerator.features.FeatureDefinition;
import de.frosner.datagenerator.features.FeatureValue;
import de.frosner.datagenerator.generator.DataGenerator;
import de.frosner.datagenerator.generator.Instance;

/**
 * Service managing the data generation preview {@linkplain TableModel}. It can be used to populate the table with
 * preview data generated from a list of {@linkplain FeatureDefinition} instances.
 */
public final class PreviewTableManager {

	private static TableModel _table;

	private static class PreviewTableExportConnection extends ExportConnection {

		private final TableModel _table;
		private int rowIndex = 1;

		public PreviewTableExportConnection(TableModel table) {
			_table = table;
		}

		@Override
		public void exportInstanceStrategy(Instance instance) {
			int columnIndex = 0;
			for (FeatureValue value : instance) {
				if (columnIndex < _table.getColumnCount()) {
					_table.setValueAt(value.getValueAsString(), rowIndex, columnIndex++);
				}
			}
			rowIndex++;
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
	public static void setPreviewTable(TableModel table) {
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
							_table.setValueAt(null, j, i);
						}
					}
				}
			});
		}
	}

}
