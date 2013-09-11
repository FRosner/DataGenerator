package de.frosner.datagenerator.gui;

import java.awt.Color;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JComponent;
import javax.swing.JTextField;

import net.sf.qualitycheck.Check;

public class DoubleVerifier {

	static String _doubleFormat = "^\\-?[0-9]+(\\.[0-9]+(E\\-?[0-9]+)?)?$";
	static Pattern _pattern = Pattern.compile(_doubleFormat);
	static Matcher _matcher;

	private DoubleVerifier() {
		throw new UnsupportedOperationException();
	}

	public static boolean verify(JComponent input) {
		Check.instanceOf(JTextField.class, input);
		JTextField textField = (JTextField) input;
		String text = textField.getText();
		_matcher = _pattern.matcher(text);

		if (_matcher.matches()) {
			textField.setBackground(Color.white);
			return true;
		} else {
			textField.setBackground(SwingMenu.INVALID_INPUT_RED);
			return false;
		}
	}

}
