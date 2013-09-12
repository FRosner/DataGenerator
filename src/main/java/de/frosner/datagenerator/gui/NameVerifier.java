package de.frosner.datagenerator.gui;

public final class NameVerifier extends InputVerifier {

	private String _name;

	public final static NameVerifier NO_NAME = new NameVerifier(false);

	private NameVerifier(boolean isVerified) {
		super(isVerified);
	}

	public NameVerifier(String string) {
		super(true);
		_name = string;
	}

	public NameVerifier isNotLongerThan(int size) {
		if (_verified) {
			_verified = _name.length() <= size;
		}
		return this;
	}

	public NameVerifier isFileName() {
		if (_verified) {
			_verified = !_name.startsWith(" ") && !_name.endsWith(" ");
		}
		return this;
	}

}
