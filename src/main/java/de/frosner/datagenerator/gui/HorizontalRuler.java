package de.frosner.datagenerator.gui;

import javax.annotation.Nonnull;
import javax.swing.JComponent;

import net.sf.qualitycheck.Check;

public final class HorizontalRuler {

	public static final class RulerOutOfBoundsException extends RuntimeException {
		private static final long serialVersionUID = 1L;
	}

	private final int _y;

	public HorizontalRuler(int y) {
		Check.stateIsTrue(y >= 0, RulerOutOfBoundsException.class);
		_y = y;
	}

	public void alignBottom(@Nonnull JComponent... components) {
		Check.noNullElements(components);
		for (JComponent component : components) {
			int newY = _y - component.getHeight();
			component.setBounds(component.getX(), newY, component.getWidth(), component.getHeight());
		}
	}

	public void alignTop(@Nonnull JComponent... components) {
		Check.noNullElements(components);
		for (JComponent component : components) {
			int newY = _y;
			component.setBounds(component.getX(), newY, component.getWidth(), component.getHeight());
		}
	}

}
