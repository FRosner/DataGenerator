package de.frosner.datagenerator.gui;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;

public class NameVerifierTest {

	@Test
	public void testIsName() {
		assertThat(InputVerifier.isName("").isVerified()).isFalse();
		assertThat(InputVerifier.isName(" ").isVerified()).isFalse();
		assertThat(InputVerifier.isName("Feature").isVerified()).isTrue();
	}

	@Test
	public void testIsNotLongerThan() {
		assertThat(InputVerifier.isName("Feature").isNotLongerThan(7).isVerified()).isTrue();
		assertThat(InputVerifier.isName("Feature2").isNotLongerThan(7).isVerified()).isFalse();
	}

	@Test
	public void testIsFileName() {
		assertThat(InputVerifier.isName(" test.csv").isFileName().isVerified()).isFalse();
		assertThat(InputVerifier.isName("test.csv ").isFileName().isVerified()).isFalse();
		assertThat(InputVerifier.isName("test.csv").isFileName().isVerified()).isTrue();
	}

}
