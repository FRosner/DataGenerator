package de.frosner.datagenerator.gui.services;

import java.util.Collection;

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
	 * <p>
	 * Neither of the {@linkplain Collection}s must be null, nor empty. In addition every contained
	 * {@linkplain AbstractButton} must not be null.
	 * 
	 * @param initiallyEnabledButtons
	 *            need to be initially enabled and will be toggled together
	 * @param initiallyDisabledButtons
	 *            need to be initially disabled and will be toggled together
	 */
	public static void manageButtons(@Nonnull Collection<AbstractButton> initiallyEnabledButtons,
			@Nonnull Collection<AbstractButton> initiallyDisabledButtons) {
		_initiallyEnabledButtons = Check.notEmpty(initiallyEnabledButtons, "initiallyEnabledButtons");
		Check.noNullElements(_initiallyEnabledButtons, "initiallyEnabledButtons");
		Check.stateIsTrue(buttonsHaveSameEnabledState(_initiallyEnabledButtons, true),
				"Buttons of this collection must be initially enabled (initiallyEnabledButtons).");

		_initiallyDisabledButtons = Check.notEmpty(initiallyDisabledButtons, "initiallyDisabledButtons");
		Check.noNullElements(_initiallyDisabledButtons, "initiallyDisabledButtons");
		Check.stateIsTrue(buttonsHaveSameEnabledState(_initiallyDisabledButtons, false),
				"Buttons of this collection must be initially disabled (initiallyDisabledButtons).");
	}

	public static void stopManaging() {
		_initiallyEnabledButtons = null;
		_initiallyDisabledButtons = null;
	}

	public static void toggle() {
		if (isCurrentlyManaging()) {
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

	private static boolean isCurrentlyManaging() {
		return _initiallyEnabledButtons != null && _initiallyDisabledButtons != null;
	}

	private static boolean buttonsHaveSameEnabledState(Collection<AbstractButton> buttons, boolean requiredState) {
		for (AbstractButton button : buttons) {
			if (!button.isEnabled() == requiredState) {
				return false;
			}
		}
		return true;
	}

}
