package de.frosner.datagenerator.gui;

import java.awt.Color;

import javax.swing.JComponent;
import javax.swing.JTextField;

import net.sf.qualitycheck.Check;

public class IntegerVerifier {

	private IntegerVerifier() {
		throw new UnsupportedOperationException();
	}

	public static boolean verify(JComponent input) {
		Check.instanceOf(JTextField.class, input);
		JTextField textField = (JTextField) input;
		String text = textField.getText();
		String regex = "^\\-?[0-9]+$";

		if (text.matches(regex)) {
			textField.setBackground(Color.white);
			return true;
		} else {
			textField.setBackground(SwingMenu.INVALID_INPUT_RED);
			return false;
		}
	}

}
