package de.frosner.datagenerator.gui.main;

import static org.fest.assertions.Assertions.assertThat;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

public class AllFileFilterTest {

	private AllFileFilter _filter;

	@Before
	public void createFilter() {
		_filter = new AllFileFilter();
	}

	@Test
	public void testAccept_files() {
		assertThat(_filter.accept(new File("abc"))).isTrue();
		assertThat(_filter.accept(new File("abc.txt"))).isTrue();
	}

	@Test
	public void testAccept_folder() {
		assertThat(_filter.accept(new DummyDirectory("dir"))).isTrue();
	}
}
