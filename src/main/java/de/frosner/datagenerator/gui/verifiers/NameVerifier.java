package de.frosner.datagenerator.gui.verifiers;

/**
 * {@linkplain InputVerifier} to verify that a given text satisfies certain conditions.
 */
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

	/**
	 * Verify that the text is not longer than the given size.
	 * 
	 * @param size
	 *            not to be exceeded
	 */
	public NameVerifier isNotLongerThan(int size) {
		if (_verified) {
			_verified = _name.length() <= size;
		}
		return this;
	}

	/**
	 * Verify that the text could be a valid file name.
	 */
	public NameVerifier isFileName() {
		if (_verified) {
			_verified = !_name.startsWith(" ") && !_name.endsWith(" ");
		}
		return this;
	}

}
