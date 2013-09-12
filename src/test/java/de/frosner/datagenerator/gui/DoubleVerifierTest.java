package de.frosner.datagenerator.gui;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;

public class DoubleVerifierTest {

	@Test
	public void testIsDouble() {
		assertThat(InputVerifier.isDouble(" ").isVerified()).isFalse();
		assertThat(InputVerifier.isDouble("x").isVerified()).isFalse();
		assertThat(InputVerifier.isDouble("1").isVerified()).isTrue();
		assertThat(InputVerifier.isDouble("1.0").isVerified()).isTrue();
		assertThat(InputVerifier.isDouble("-1.0").isVerified()).isTrue();
		assertThat(InputVerifier.isDouble("1.02E-10").isVerified()).isTrue();
		assertThat(InputVerifier.isDouble("1.02E10").isVerified()).isTrue();
	}

	@Test
	public void testIsPositive() {
		assertThat(InputVerifier.isDouble("1.0").isPositive().isVerified()).isTrue();
		assertThat(InputVerifier.isDouble("0").isPositive().isVerified()).isFalse();
		assertThat(InputVerifier.isDouble("-1.0").isPositive().isVerified()).isFalse();
	}

}
