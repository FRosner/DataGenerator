package de.frosner.datagenerator.gui;

import java.awt.Color;

import javax.swing.JComponent;
import javax.swing.JList;

import net.sf.qualitycheck.Check;

public class NonEmptyListVerifier {

	private NonEmptyListVerifier() {
		throw new UnsupportedOperationException();
	}

	public static boolean verify(JComponent input) {
		Check.instanceOf(JList.class, input);
		JList list = (JList) input;

		if (list.getModel().getSize() == 0) {
			list.setBackground(SwingMenu.INVALID_INPUT_RED);
			return false;
		} else {
			list.setBackground(Color.white);
			return true;
		}
	}

}
