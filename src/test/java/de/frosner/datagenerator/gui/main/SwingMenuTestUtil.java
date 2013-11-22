package de.frosner.datagenerator.gui.main;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.AbstractButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;

import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiTask;

import de.frosner.datagenerator.gui.services.GenerationButtonsToggleManager;
import de.frosner.datagenerator.gui.services.PreviewTableManager;
import de.frosner.datagenerator.gui.services.ProgressBarManager;
import de.frosner.datagenerator.gui.services.TextAreaLogManager;
import de.frosner.datagenerator.util.GuiTestUtil;

public final class SwingMenuTestUtil extends GuiTestUtil {

	private SwingMenu _menu;

	SwingMenuTestUtil(SwingMenu swingMenu) {
		super();
		_menu = swingMenu;
		GuiActionRunner.execute(new GuiTask() {
			@Override
			protected void executeInEDT() {
				_menu.setVisible(true);
				_menu.toFront();
			}
		});
	}

	void clickButtonOrItem(final AbstractButton buttonOrItem) {
		GuiActionRunner.execute(new GuiTask() {
			@Override
			protected void executeInEDT() {
				_menu.actionPerformed(new ActionEvent(buttonOrItem, 1, ""));
			}
		});
	}

	void tryToAddEnteredFeatureAndGiveUp() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				delay(DIALOG_OPEN_DELAY);
				pressAndReleaseKey(KeyEvent.VK_ENTER);
				delay(ROBOT_DELAY);
				pressAndReleaseKey(KeyEvent.VK_ESCAPE);
			}
		}).start();
		GuiActionRunner.execute(new GuiTask() {
			@Override
			protected void executeInEDT() {
				_menu.actionPerformed(new ActionEvent(_menu._addFeatureButton, 1, ""));
			}
		});
	}

	void addEnteredFeature(final AbstractButton buttonPerformedWith) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				delay(DIALOG_OPEN_DELAY);
				pressAndReleaseKey(KeyEvent.VK_ENTER);
			}
		}).start();
		GuiActionRunner.execute(new GuiTask() {
			@Override
			protected void executeInEDT() {
				_menu.actionPerformed(new ActionEvent(buttonPerformedWith, 1, ""));
			}
		});
	}

	void updateSelectedFeature() {
		final String featureName = _menu._featureNameField.getText();
		final String gaussianMean = _menu._gaussianMeanField.getText();
		final String gaussianSigma = _menu._gaussianSigmaField.getText();
		final String bernoulliProbability = _menu._bernoulliProbabilityField.getText();
		final String categorialNumberOfStates = _menu._uniformCategorialNumberOfStatesField.getText();
		new Thread(new Runnable() {
			@Override
			public void run() {
				delay(DIALOG_OPEN_DELAY / 2);
				GuiActionRunner.execute(new GuiTask() {
					@Override
					protected void executeInEDT() {
						_menu._featureNameField.setText(featureName);
						_menu._gaussianMeanField.setText(gaussianMean);
						_menu._gaussianSigmaField.setText(gaussianSigma);
						_menu._bernoulliProbabilityField.setText(bernoulliProbability);
						_menu._uniformCategorialNumberOfStatesField.setText(categorialNumberOfStates);
					}
				});
				delay(DIALOG_OPEN_DELAY / 2);
				pressAndReleaseKey(KeyEvent.VK_ENTER);
			}
		}).start();
		GuiActionRunner.execute(new GuiTask() {
			@Override
			protected void executeInEDT() {
				_menu.actionPerformed(new ActionEvent(_menu._editFeatureButton, 1, ""));
			}
		});
	}

	void selectOption(final JComboBox comboBox, final Object option) {
		GuiActionRunner.execute(new GuiTask() {
			@Override
			protected void executeInEDT() {
				comboBox.setSelectedItem(option);
			}
		});
	}

	void selectFeature(final int i) {
		GuiActionRunner.execute(new GuiTask() {
			@Override
			protected void executeInEDT() {
				_menu._featureList.setSelectedIndex(i);
			}
		});
	}

	void selectFile(final File file) {
		GuiActionRunner.execute(new GuiTask() {
			@Override
			protected void executeInEDT() {
				_menu._exportFileDialog.setSelectedFile(file);
			}
		});
	}

	void enterText(final JTextField textField, final String text) {
		GuiActionRunner.execute(new GuiTask() {
			@Override
			protected void executeInEDT() {
				textField.setText(text);
			}
		});
	}

	void setExportFileFilter(final FileFilter filter) {
		GuiActionRunner.execute(new GuiTask() {
			@Override
			protected void executeInEDT() {
				_menu._exportFileDialog.setFileFilter(filter);
			}
		});
	}

	void selectFileUsingFileChooserDialog(final File file) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				delay(DIALOG_OPEN_DELAY);
				GuiActionRunner.execute(new GuiTask() {
					@Override
					protected void executeInEDT() {
						_menu._exportFileDialog.setSelectedFile(file);
						_menu._exportFileDialog.approveSelection();
					}
				});
			}
		}).start();
		clickButtonOrItem(_menu._exportFileButton);
	}

	void escapeEnteredFeature() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				delay(DIALOG_OPEN_DELAY);
				pressAndReleaseKey(KeyEvent.VK_ESCAPE);
			}
		}).start();
		GuiActionRunner.execute(new GuiTask() {
			@Override
			protected void executeInEDT() {
				_menu.actionPerformed(new ActionEvent(_menu._addFeatureButton, 1, ""));
			}
		});
	}

	public static void resetComponentManagers() {
		TextAreaLogManager.unsetLogArea();
		PreviewTableManager.unsetPreviewTable();
		ProgressBarManager.unsetProgressBar();
		GenerationButtonsToggleManager.unsetButtons();
	}

}
