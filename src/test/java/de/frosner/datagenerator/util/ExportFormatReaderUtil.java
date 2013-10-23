package de.frosner.datagenerator.util;

import java.util.List;
import java.util.Map;

import net.sf.qualitycheck.Check;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public final class ExportFormatReaderUtil {

	private ExportFormatReaderUtil() {
		throw new UnsupportedOperationException();
	}

	public static Map<String, List<String>> readCsvWithHeader(String csv, String delim) {
		Check.notNull(csv);
		if (csv.isEmpty()) {
			throw new MalformedExportFormatException("CSV", "No header available");
		}
		Map<String, List<String>> resultColumns = Maps.newHashMap();
		List<List<String>> columns = Lists.newArrayList();
		List<String> lines = Lists.newArrayList(csv.split("\\n"));
		List<String> header = Lists.newArrayList(lines.get(0).split(delim));
		for (String columnHead : header) {
			List<String> column = Lists.newArrayList();
			columns.add(column);
			resultColumns.put(columnHead, column);
		}
		for (int i = 1; i < lines.size(); i++) {
			List<String> line = Lists.newArrayList(lines.get(i).split(delim));
			int elementIndex = 0;
			for (String element : line) {
				columns.get(elementIndex++).add(element);
			}
		}
		return resultColumns;
	}
}
