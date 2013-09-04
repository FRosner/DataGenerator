package de.frosner.datagenerator.gui;

import javax.annotation.Nonnull;
import javax.swing.JComponent;

import net.sf.qualitycheck.Check;

public final class VerticalRuler {

	private final int _x;

	public VerticalRuler(int x) {
		Check.stateIsTrue(x >= 0, RulerOutOfBoundsException.class);
		_x = x;
	}

	public void alignRight(@Nonnull JComponent... components) {
		Check.noNullElements(components);
		for (JComponent component : components) {
			int newX = _x - component.getWidth();
			component.setBounds(newX, component.getY(), component.getWidth(), component.getHeight());
		}
	}

	public void alignLeft(@Nonnull JComponent... components) {
		Check.noNullElements(components);
		for (JComponent component : components) {
			int newX = _x;
			component.setBounds(newX, component.getY(), component.getWidth(), component.getHeight());
		}
	}

}
