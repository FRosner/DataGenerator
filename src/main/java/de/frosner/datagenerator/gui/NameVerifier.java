package de.frosner.datagenerator.gui;

import java.awt.Color;

import javax.swing.JComponent;
import javax.swing.JTextField;

import net.sf.qualitycheck.Check;

import org.apache.commons.lang.StringUtils;

public class NameVerifier {

	private NameVerifier() {
		throw new UnsupportedOperationException();
	}

	public static boolean verify(JComponent input) {
		Check.instanceOf(JTextField.class, input);
		JTextField textField = (JTextField) input;
		String text = textField.getText();
		if (StringUtils.isBlank(text)) {
			textField.setBackground(SwingMenu.INVALID_INPUT_RED);
			return false;
		} else {
			textField.setBackground(Color.white);
			return true;
		}
	}

}
