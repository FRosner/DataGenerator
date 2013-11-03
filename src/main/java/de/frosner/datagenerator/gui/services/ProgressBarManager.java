package de.frosner.datagenerator.gui.services;

import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;

import net.sf.qualitycheck.Check;

/**
 * Service managing a {@linkplain JProgressBar}. It can be used to increase or reset progress.
 */
public final class ProgressBarManager {

	private static JProgressBar _progressBar;

	private ProgressBarManager() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Set the progress bar to be managed.
	 * 
	 * @param progressBar
	 *            to be managed.
	 */
	public static void setProgressBar(JProgressBar progressBar) {
		_progressBar = Check.notNull(progressBar, "progressBar");
	}

	/**
	 * Unset the managed progress bar.
	 */
	public static void unsetProgressBar() {
		_progressBar = null;
	}

	/**
	 * Set the progress bar maximum value.
	 * 
	 * @param maxValue
	 *            of progress bar
	 */
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

	/**
	 * Increase the value of the progress bar by one.
	 */
	public static void increaseProgress() {
		if (_progressBar != null && (_progressBar.getValue() < _progressBar.getMaximum())) {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					_progressBar.setValue(_progressBar.getValue() + 1);
				}
			});
		}
	}

	/**
	 * Reset progress of the progress bar.
	 */
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
