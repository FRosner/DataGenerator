package de.frosner.datagenerator.gui;

public final class DoubleVerifier extends InputVerifier {

	private double _double;

	public final static DoubleVerifier NO_DOUBLE = new DoubleVerifier(false);

	private DoubleVerifier(boolean isVerified) {
		super(isVerified);
	}

	public DoubleVerifier(double integer) {
		super(true);
		_double = integer;
	}

	public DoubleVerifier isPositive() {
		if (_verified) {
			_verified = _double > 0;
		}
		return this;
	}

}
