package de.frosner.datagenerator.gui.services;

import javax.annotation.Nonnull;
import javax.swing.JComboBox;
import javax.swing.SwingUtilities;

import net.sf.qualitycheck.Check;
import de.frosner.datagenerator.gui.main.FeatureDefinitionEntry;
import de.frosner.datagenerator.gui.main.GaussianFeatureEntry;

public class FeatureParameterDependencySelectorManager {

	private FeatureParameterDependencySelectorManager() {
		throw new UnsupportedOperationException();
	}

	private static JComboBox _gaussianMeanSelector;

	public static void manageGaussianMeanSelector(@Nonnull JComboBox gaussianMeanSelector) {
		_gaussianMeanSelector = Check.notNull(gaussianMeanSelector);
	}

	public static void stopManaging() {
		_gaussianMeanSelector = null;
	}

	public static void addFeatureDefinitionEntry(@Nonnull final FeatureDefinitionEntry entry) {
		Check.notNull(entry);

		if (_gaussianMeanSelector != null) {
			if (entry instanceof GaussianFeatureEntry) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						_gaussianMeanSelector.addItem(entry);
					}
				});
			}
		}
	}

	public static void removeFeatureDefinitionEntry(@Nonnull final FeatureDefinitionEntry entry) {
		Check.notNull(entry);
		if (_gaussianMeanSelector != null) {
			if (entry instanceof GaussianFeatureEntry) {
				checkThatEntryExists(_gaussianMeanSelector, entry);
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						_gaussianMeanSelector.removeItem(entry);
					}
				});
			}
		}
	}

	private static void checkThatEntryExists(JComboBox comboBox, FeatureDefinitionEntry entry) {
		for (int i = 0; i < comboBox.getItemCount(); i++) {
			if (comboBox.getItemAt(i).equals(entry)) {
				return;
			}
		}
		throw new IllegalArgumentException("Item " + entry + " could not be removed because it was not present in "
				+ _gaussianMeanSelector + ".");
	}

}