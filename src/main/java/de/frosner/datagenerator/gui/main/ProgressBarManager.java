package de.frosner.datagenerator.gui.main;

import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;

public class ProgressBarManager {

	private static JProgressBar _progressBar;

	private ProgressBarManager() {
		throw new UnsupportedOperationException();
	}

	public static void setProgressBar(JProgressBar progressBar) {
		_progressBar = progressBar;
	}

	public static void setProgressBarMaximumValue(final int maxValue) {
		if (_progressBar != null) {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					_progressBar.setMaximum(maxValue);

				}
			});
		}
	}

	public static void increaseProgress() {
		if (_progressBar != null) {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					_progressBar.setValue(_progressBar.getValue() + 1);

				}
			});
		}
	}

	public static void resetProgress() {
		if (_progressBar != null) {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					_progressBar.setValue(0);

				}
			});
		}
	}

}
