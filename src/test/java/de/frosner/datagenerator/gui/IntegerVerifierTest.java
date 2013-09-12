package de.frosner.datagenerator.gui;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;

public class IntegerVerifierTest {

	@Test
	public void testIsInteger() {
		assertThat(InputVerifier.isInteger("").isVerified()).isFalse();
		assertThat(InputVerifier.isInteger(" ").isVerified()).isFalse();
		assertThat(InputVerifier.isInteger("x").isVerified()).isFalse();
		assertThat(InputVerifier.isInteger("-1.0").isVerified()).isFalse();
		assertThat(InputVerifier.isInteger("1.02E-10").isVerified()).isFalse();
		assertThat(InputVerifier.isInteger("1.02E10").isVerified()).isFalse();
		assertThat(InputVerifier.isInteger("1").isVerified()).isTrue();
		assertThat(InputVerifier.isInteger("-11").isVerified()).isTrue();
	}

	@Test
	public void testIsPositive() {
		assertThat(InputVerifier.isInteger("1").isPositive().isVerified()).isTrue();
		assertThat(InputVerifier.isInteger("0").isPositive().isVerified()).isFalse();
		assertThat(InputVerifier.isInteger("-1").isPositive().isVerified()).isFalse();
	}

}
