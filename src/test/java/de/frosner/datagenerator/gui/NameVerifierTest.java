package de.frosner.datagenerator.gui;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;

public class NameVerifierTest {

	@Test
	public void testIsName() {
		assertThat(InputVerifier.isName("").verify()).isFalse();
		assertThat(InputVerifier.isName(" ").verify()).isFalse();
		assertThat(InputVerifier.isName("Feature").verify()).isTrue();
	}

	@Test
	public void testIsNotLongerThan() {
		assertThat(InputVerifier.isName("Feature").isNotLongerThan(7).verify()).isTrue();
		assertThat(InputVerifier.isName("Feature2").isNotLongerThan(7).verify()).isFalse();
	}

	@Test
	public void testIsFileName() {
		assertThat(InputVerifier.isName(" test.csv").isFileName().verify()).isFalse();
		assertThat(InputVerifier.isName("test.csv ").isFileName().verify()).isFalse();
		assertThat(InputVerifier.isName("test.csv").isFileName().verify()).isTrue();
	}

}
