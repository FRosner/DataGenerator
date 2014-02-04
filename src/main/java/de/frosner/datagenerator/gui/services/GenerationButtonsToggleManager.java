package de.frosner.datagenerator.gui.services;

import java.util.Collection;
import java.util.Iterator;

import javax.annotation.Nonnull;
import javax.swing.AbstractButton;
import javax.swing.SwingUtilities;

import net.sf.qualitycheck.Check;

public class GenerationButtonsToggleManager {

	private GenerationButtonsToggleManager() {
		throw new UnsupportedOperationException();
	}

	private static Collection<AbstractButton> _initiallyEnabledButtons;
	private static Collection<AbstractButton> _initiallyDisabledButtons;

	/**
	 * Manages every {@linkplain AbstractButton} of the passed {@linkplain Collection}s so the buttons can be toggled.
	 * Buttons belonging to the same collection must be consistent concerning their enabled state.
	 * <p>
	 * Neither of the {@linkplain Collection}s must be null, nor empty. In addition every contained
	 * {@linkplain AbstractButton} must not be null.
	 */
	public static void manageButtons(@Nonnull Collection<AbstractButton> initiallyEnabledButtons,
			@Nonnull Collection<AbstractButton> initiallyDisabledButtons) {
		_initiallyEnabledButtons = Check.notEmpty(initiallyEnabledButtons, "initiallyEnabledButtons");
		Check.noNullElements(_initiallyEnabledButtons, "initiallyEnabledButtons");
		Check.stateIsTrue(buttonsHaveSameEnabledState(_initiallyEnabledButtons),
				"Components of the same collection must have the same enabled state (initiallyEnabledButtons).");

		_initiallyDisabledButtons = Check.notEmpty(initiallyDisabledButtons, "initiallyDisabledButtons");
		Check.noNullElements(_initiallyDisabledButtons, "initiallyDisabledButtons");
		Check.stateIsTrue(buttonsHaveSameEnabledState(_initiallyDisabledButtons),
				"Components of the same collection must have the same enabled state (initiallyDisabledButtons).");
	}

	public static void stopManaging() {
		_initiallyEnabledButtons = null;
		_initiallyDisabledButtons = null;
	}

	public static void toggle() {
		if (_initiallyEnabledButtons != null && _initiallyDisabledButtons != null) {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					for (AbstractButton button : _initiallyEnabledButtons) {
						button.setEnabled(!button.isEnabled());
					}
					for (AbstractButton button : _initiallyDisabledButtons) {
						button.setEnabled(!button.isEnabled());
					}
				}
			});
		}
	}

	private static boolean buttonsHaveSameEnabledState(Collection<AbstractButton> buttons) {
		Iterator<AbstractButton> iterator1 = buttons.iterator();
		Iterator<AbstractButton> iterator2 = buttons.iterator();
		for (iterator2.next(); iterator2.hasNext();) {
			if (iterator1.next().isEnabled() != iterator2.next().isEnabled()) {
				return false;
			}
		}
		return true;
	}

}
