package de.frosner.datagenerator.gui.main;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;

import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiTask;

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

	void clickButton(final JButton button) {
		GuiActionRunner.execute(new GuiTask() {
			@Override
			protected void executeInEDT() {
				_menu.actionPerformed(new ActionEvent(button, 1, ""));
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

	void addEnteredFeature() {
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
				_menu.actionPerformed(new ActionEvent(_menu._addFeatureButton, 1, ""));
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
		clickButton(_menu._exportFileButton);
	}

	public static void resetComponentManagers() {
		TextAreaLogManager.unsetLogArea();
		PreviewTableManager.unsetPreviewTable();
		ProgressBarManager.unsetProgressBar();
	}

}
