package de.frosner.datagenerator.gui;

import javax.annotation.Nonnull;
import javax.swing.JComponent;

import net.sf.qualitycheck.Check;

public final class VerticalRuler {

	private VerticalRuler() {

	}

	public static void alignRightAt(int atX, @Nonnull JComponent... components) {
		Check.stateIsTrue(atX >= 0, RulerOutOfBoundsException.class);
		Check.noNullElements(components);
		for (JComponent component : components) {
			int newX = atX - component.getWidth();
			component.setBounds(newX, component.getY(), component.getWidth(), component.getHeight());
		}
	}

	public static void alignLeftAt(int atX, @Nonnull JComponent... components) {
		Check.stateIsTrue(atX >= 0, RulerOutOfBoundsException.class);
		Check.noNullElements(components);
		for (JComponent component : components) {
			int newX = atX;
			component.setBounds(newX, component.getY(), component.getWidth(), component.getHeight());
		}
	}

}
