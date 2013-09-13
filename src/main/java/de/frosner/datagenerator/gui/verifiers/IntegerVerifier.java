package de.frosner.datagenerator.gui.verifiers;

public final class IntegerVerifier extends InputVerifier {

	private int _integer;

	public final static IntegerVerifier NO_INTEGER = new IntegerVerifier(false);

	private IntegerVerifier(boolean isVerified) {
		super(isVerified);
	}

	public IntegerVerifier(int integer) {
		super(true);
		_integer = integer;
	}

	public IntegerVerifier isPositive() {
		if (_verified) {
			_verified = _integer > 0;
		}
		return this;
	}

}
