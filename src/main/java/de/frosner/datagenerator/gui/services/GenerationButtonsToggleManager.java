package de.frosner.datagenerator.gui.services;

import javax.swing.JButton;
import javax.swing.SwingUtilities;

import net.sf.qualitycheck.Check;

public class GenerationButtonsToggleManager {

	private GenerationButtonsToggleManager() {
		throw new UnsupportedOperationException();
	}

	private static JButton _button1;
	private static JButton _button2;

	public static void setButtons(JButton button1, JButton button2) {
		_button1 = Check.notNull(button1);
		_button2 = Check.notNull(button2);
	}

	public static void unsetButtons() {
		_button1 = null;
		_button2 = null;
	}

	public static void toggle() {
		if (_button1 != null && _button2 != null) {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					_button1.setEnabled(!_button1.isEnabled());
					_button2.setEnabled(!_button2.isEnabled());
				}
			});
		}
	}

}
