package de.frosner.datagenerator.gui.verifiers;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;

public class DoubleVerifierTest {

	@Test
	public void testIsDouble() {
		assertThat(InputVerifier.isDouble(" ").verify()).isFalse();
		assertThat(InputVerifier.isDouble("x").verify()).isFalse();
		assertThat(InputVerifier.isDouble("1.7976931348623157E309").verify()).isFalse();
		assertThat(InputVerifier.isDouble("1").verify()).isTrue();
		assertThat(InputVerifier.isDouble("1.0").verify()).isTrue();
		assertThat(InputVerifier.isDouble("-1.0").verify()).isTrue();
		assertThat(InputVerifier.isDouble("1.02E-10").verify()).isTrue();
		assertThat(InputVerifier.isDouble("1.02E10").verify()).isTrue();
		assertThat(InputVerifier.isDouble("1.7976931348623157E308").verify()).isTrue();
	}

	@Test
	public void testIsPositive() {
		assertThat(InputVerifier.isDouble("1.0").isPositive().verify()).isTrue();
		assertThat(InputVerifier.isDouble("0").isPositive().verify()).isFalse();
		assertThat(InputVerifier.isDouble("-1.0").isPositive().verify()).isFalse();
	}

	@Test
	public void testIsProbability() {
		assertThat(InputVerifier.isDouble("0.0").isProbability().verify()).isTrue();
		assertThat(InputVerifier.isDouble("1.0").isProbability().verify()).isTrue();
		assertThat(InputVerifier.isDouble("1.1").isProbability().verify()).isFalse();
		assertThat(InputVerifier.isDouble("-0.1").isProbability().verify()).isFalse();
	}

}
