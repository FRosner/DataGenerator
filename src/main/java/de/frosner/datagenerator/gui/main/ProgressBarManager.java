package de.frosner.datagenerator.gui.main;

import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;

/**
 * Service managing a {@link JProgressBar}. It can be used to increase or reset progress.
 */
public class ProgressBarManager {

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
		_progressBar = progressBar;
	}

	/**
	 * Increase the value of the progress bar by one.
	 */
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

	/**
	 * Set the value of the progress bar to maximum.
	 */
	public static void setProgressToMaximum() {
		if (_progressBar != null) {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					_progressBar.setValue(100);

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
