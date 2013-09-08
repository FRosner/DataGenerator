package de.frosner.datagenerator.gui;

import java.awt.Color;

import javax.swing.JComponent;
import javax.swing.JTextField;

import net.sf.qualitycheck.Check;

public class DoubleVerifier {

	private DoubleVerifier() {
		throw new UnsupportedOperationException();
	}

	public static boolean verify(JComponent input) {
		Check.instanceOf(JTextField.class, input);
		JTextField textField = (JTextField) input;
		String text = textField.getText();
		try {
			Double.parseDouble(text);
			textField.setBackground(Color.white);
			return true;
		} catch (NumberFormatException e) {
			textField.setBackground(new Color(255, 200, 200));
			return false;
		}
	}

}
