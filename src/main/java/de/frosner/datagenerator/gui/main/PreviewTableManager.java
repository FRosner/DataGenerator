package de.frosner.datagenerator.gui.main;

import java.io.IOException;
import java.util.List;

import javax.swing.SwingUtilities;
import javax.swing.table.TableModel;

import de.frosner.datagenerator.export.ExportConnection;
import de.frosner.datagenerator.features.FeatureDefinition;
import de.frosner.datagenerator.features.FeatureValue;
import de.frosner.datagenerator.generator.DataGenerator;
import de.frosner.datagenerator.generator.Instance;

public class PreviewTableManager {

	private static TableModel _table;

	private static class PreviewTableExportConnection implements ExportConnection {

		private final TableModel _table;
		private int rowIndex = 1;

		public PreviewTableExportConnection(TableModel table) {
			_table = table;
		}

		@Override
		public void export(Instance instance) throws IOException {
			int columnIndex = 0;
			for (FeatureValue value : instance) {
				if (columnIndex < _table.getColumnCount()) {
					_table.setValueAt(value.getValueAsString(), rowIndex, columnIndex++);
				}
			}
			rowIndex++;
		}

		@Override
		public void close() throws IOException {
		}

	}

	private PreviewTableManager() {
		throw new UnsupportedOperationException();
	}

	public static void setPreviewTable(TableModel table) {
		_table = table;
	}

	public static void generatePreview(final List<FeatureDefinition> features) {
		clearPreviewTable();
		if (_table != null && !features.isEmpty()) {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					try {
						new DataGenerator(_table.getRowCount() - 1, new PreviewTableExportConnection(_table), features)
								.generate(false);
					} catch (IOException e) {
						// PreviewTableExportConnection does not throw IOException
					}
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
