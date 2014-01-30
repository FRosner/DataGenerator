package de.frosner.datagenerator.gui.main;

import static org.fest.assertions.Assertions.assertThat;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

public class SwingMenuExportIntegrationTest extends SwingMenuIntegrationTest {

	@Test
	public void testSelectExportFile() {
		assertThat(_frame._exportFileField.isEditable()).isFalse();
		_frameTestUtil.selectFileUsingFileChooserDialog(new File("t"));
		assertThat(_frame._exportFileField.getText()).endsWith("t");
	}

	@Test
	public void testSelectExportFile_fileExtensionCsv() {
		_frameTestUtil.setExportFileFilter(SwingMenu.CSV_FILE_FILTER);
		_frameTestUtil.selectFileUsingFileChooserDialog(new File("t"));
		assertThat(_frame._exportFileField.getText()).endsWith("t.csv");
	}

	@Test
	public void testSelectExportFile_fileExtensionNotAddedTwice() throws InterruptedException {
		_frameTestUtil.setExportFileFilter(SwingMenu.CSV_FILE_FILTER);
		_frameTestUtil.selectFileUsingFileChooserDialog(new File("t.csv"));
		assertThat(_frame._exportFileField.getText()).endsWith("t.csv");
	}

	@Test
	public void testSelectedDirectoryEqualsCurrentDirectoryAtStartUp() throws IOException {
		assertThat(_frame._exportFileDialog.getCurrentDirectory().getCanonicalPath()).isEqualTo(
				new File("").getCanonicalPath());
	}

}
