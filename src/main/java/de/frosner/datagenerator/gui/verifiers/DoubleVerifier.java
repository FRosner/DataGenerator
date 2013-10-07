package de.frosner.datagenerator.gui.verifiers;

/**
 * {@linkplain InputVerifier} to verify that a given double satisfies certain conditions.
 */
public final class DoubleVerifier extends InputVerifier {

	private double _double;

	public final static DoubleVerifier NO_DOUBLE = new DoubleVerifier(false);

	private DoubleVerifier(boolean isVerified) {
		super(isVerified);
	}

	public DoubleVerifier(double doubleValue) {
		super(true);
		_double = doubleValue;
	}

	/**
	 * Verify that the given double is positive.
	 */
	public DoubleVerifier isPositive() {
		if (_verified) {
			_verified = _double > 0;
		}
		return this;
	}

}
