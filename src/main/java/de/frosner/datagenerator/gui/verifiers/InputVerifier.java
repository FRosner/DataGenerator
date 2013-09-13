package de.frosner.datagenerator.gui.verifiers;

import java.awt.Color;

import javax.swing.JComponent;

import org.apache.commons.lang.StringUtils;

import de.frosner.datagenerator.util.VisibleForTesting;

/**
 * Class to verify text input and change the color of a specified component when the input cannot be verified. It is
 * used much like a builder but instead of building it verifies the input given the conditions selected.
 * <p>
 * Example: {@code InputVerifier.isNumber("14").isPositive().verify()} returns {@code true}.
 */
public abstract class InputVerifier {

	@VisibleForTesting
	static final Color INVALID_INPUT_RED = new Color(255, 200, 200);
	@VisibleForTesting
	static final Color VALID_INPUT_WHITE = Color.WHITE;

	protected boolean _verified;

	protected InputVerifier(boolean isVerified) {
		_verified = isVerified;
	}

	/**
	 * Returns whether the input could be verified given the specified conditions.
	 * 
	 * @return whether the input could be verified
	 */
	public boolean verify() {
		return _verified;
	}

	/**
	 * This method is used to change the color of a component if verification was not successful. This will enable the
	 * user to see where he did a mistake. The verification boolean is returned to be used by calling control sequence
	 * as well.
	 * <p>
	 * Example: {@code InputVerifier.verifyComponent(myTextArea, InputVerifier.isDouble(myTextArea.getText()).verify())}
	 * will change the color of the text area if the entered text cannot be parsed to a {@code Double}.
	 * 
	 * @param component
	 *            to change the color of in case of unsuccessful verification
	 * @param verification
	 *            is a boolean indicating whether the verification was successful
	 * @return <b>verification</b>
	 */
	public static boolean verifyComponent(JComponent component, boolean verification) {
		if (verification) {
			component.setBackground(VALID_INPUT_WHITE);
		} else {
			component.setBackground(INVALID_INPUT_RED);
		}
		return verification;
	}

	/**
	 * Verifies whether the specified input text can be parsed to an {@code Integer}.
	 * 
	 * @param input
	 *            to verify
	 */
	public static IntegerVerifier isInteger(String input) {
		try {
			return new IntegerVerifier(Integer.parseInt(input));
		} catch (NumberFormatException e) {
			return IntegerVerifier.NO_INTEGER;
		}
	}

	/**
	 * Verifies whether the specified input text can be parsed to a {@code Double} that is not infinite or NaN.
	 * 
	 * @param input
	 *            to verify
	 */
	public static DoubleVerifier isDouble(String input) {
		try {
			Double doubleInput = Double.parseDouble(input);
			if (doubleInput.isNaN() || doubleInput.isInfinite()) {
				return DoubleVerifier.NO_DOUBLE;
			}
			return new DoubleVerifier(doubleInput);
		} catch (NumberFormatException e) {
			return DoubleVerifier.NO_DOUBLE;
		}
	}

	/**
	 * Verifies whether the specified input text can be used as a name. That is it must not be blank.
	 * 
	 * @param input
	 *            to verify
	 */
	public static NameVerifier isName(String input) {
		if (StringUtils.isNotBlank(input)) {
			return new NameVerifier(input);
		} else {
			return NameVerifier.NO_NAME;
		}
	}

}
