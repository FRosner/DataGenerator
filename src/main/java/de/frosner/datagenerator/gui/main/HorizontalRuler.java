package de.frosner.datagenerator.gui.main;

import javax.annotation.Nonnull;
import javax.swing.JComponent;

import net.sf.qualitycheck.Check;

public final class HorizontalRuler {

	private HorizontalRuler() {
		throw new UnsupportedOperationException();
	}

	public static void alignBottomAt(int atY, @Nonnull JComponent... components) {
		Check.stateIsTrue(atY >= 0, RulerOutOfBoundsException.class);
		Check.noNullElements(components);
		for (JComponent component : components) {
			int newY = atY - component.getHeight();
			component.setBounds(component.getX(), newY, component.getWidth(), component.getHeight());
		}
	}

	public static void alignTopAt(int atY, @Nonnull JComponent... components) {
		Check.stateIsTrue(atY >= 0, RulerOutOfBoundsException.class);
		Check.noNullElements(components);
		for (JComponent component : components) {
			int newY = atY;
			component.setBounds(component.getX(), newY, component.getWidth(), component.getHeight());
		}
	}

}
