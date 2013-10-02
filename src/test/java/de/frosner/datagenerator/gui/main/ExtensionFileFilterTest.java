package de.frosner.datagenerator.gui.main;

import static org.fest.assertions.Assertions.assertThat;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

public class ExtensionFileFilterTest {

	private ExtensionFileFilter _csvFilter;

	@Before
	public void createFilter() {
		_csvFilter = new ExtensionFileFilter("", "csv");
	}

	@Test
	public void testAccept_files() {
		assertThat(_csvFilter.accept(new File("abc"))).isFalse();
		assertThat(_csvFilter.accept(new File("abc.txt"))).isFalse();
		assertThat(_csvFilter.accept(new File("abc.csv"))).isTrue();
	}

	@Test
	public void testAccept_folder() {
		assertThat(_csvFilter.accept(new DummyDirectory("dir"))).isTrue();
	}

}
