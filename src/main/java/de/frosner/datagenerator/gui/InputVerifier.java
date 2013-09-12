package de.frosner.datagenerator.gui;

import java.awt.Color;

import javax.swing.JComponent;

import org.apache.commons.lang.StringUtils;

import de.frosner.datagenerator.util.VisibleForTesting;

public abstract class InputVerifier {

	@VisibleForTesting
	static final Color INVALID_INPUT_RED = new Color(255, 200, 200);
	@VisibleForTesting
	static final Color VALID_INPUT_WHITE = Color.WHITE;

	protected boolean _verified;

	protected InputVerifier(boolean isVerified) {
		_verified = isVerified;
	}

	public boolean verify() {
		return _verified;
	}

	public static boolean verifyComponent(JComponent component, boolean verification) {
		if (verification) {
			component.setBackground(VALID_INPUT_WHITE);
		} else {
			component.setBackground(INVALID_INPUT_RED);
		}
		return verification;
	}

	public static IntegerVerifier isInteger(String input) {
		try {
			return new IntegerVerifier(Integer.parseInt(input));
		} catch (NumberFormatException e) {
			return IntegerVerifier.NO_INTEGER;
		}
	}

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

	public static NameVerifier isName(String input) {
		if (StringUtils.isNotBlank(input)) {
			return new NameVerifier(input);
		} else {
			return NameVerifier.NO_NAME;
		}
	}

}
