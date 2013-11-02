package de.frosner.datagenerator.gui.verifiers;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;

public class IntegerVerifierTest {

	@Test
	public void testIsInteger() {
		assertThat(InputVerifier.isInteger("").verify()).isFalse();
		assertThat(InputVerifier.isInteger(" ").verify()).isFalse();
		assertThat(InputVerifier.isInteger("x").verify()).isFalse();
		assertThat(InputVerifier.isInteger("-1.0").verify()).isFalse();
		assertThat(InputVerifier.isInteger("1.02E-10").verify()).isFalse();
		assertThat(InputVerifier.isInteger("1.02E10").verify()).isFalse();
		assertThat(InputVerifier.isInteger("2147483648").verify()).isFalse();
		assertThat(InputVerifier.isInteger("-2147483649").verify()).isFalse();
		assertThat(InputVerifier.isInteger("1").verify()).isTrue();
		assertThat(InputVerifier.isInteger("-11").verify()).isTrue();
		assertThat(InputVerifier.isInteger("2147483647").verify()).isTrue();
		assertThat(InputVerifier.isInteger("-2147483648").verify()).isTrue();
	}

	@Test
	public void testIsPositive() {
		assertThat(InputVerifier.isInteger("1").isPositive().verify()).isTrue();
		assertThat(InputVerifier.isInteger("0").isPositive().verify()).isFalse();
		assertThat(InputVerifier.isInteger("-1").isPositive().verify()).isFalse();
	}

	@Test
	public void testIsInInterval() {
		assertThat(InputVerifier.isInteger("1").isInInterval(1, 1).verify()).isTrue();
		assertThat(InputVerifier.isInteger("1").isInInterval(1, 100).verify()).isTrue();
		assertThat(InputVerifier.isInteger("1").isInInterval(-100, 1).verify()).isTrue();
		assertThat(InputVerifier.isInteger("0").isInInterval(1, 100).verify()).isFalse();
		assertThat(InputVerifier.isInteger("-1").isInInterval(-100, -2).verify()).isFalse();
	}

}
