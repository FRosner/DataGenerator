package de.frosner.datagenerator.gui.main;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;

import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiTask;

public final class SwingMenuTestUtil {

	protected static final int FILE_CHOOSER_OPEN_DELAY = 500;
	private SwingMenu _menu;

	SwingMenuTestUtil(SwingMenu swingMenu) {
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

	public void selectFileUsingFileChooserDialog(final File file) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				Robot robot;
				try {
					robot = new Robot();
					robot.delay(FILE_CHOOSER_OPEN_DELAY);
					GuiActionRunner.execute(new GuiTask() {
						@Override
						protected void executeInEDT() {
							_menu._exportFileDialog.setSelectedFile(file);
							_menu._exportFileDialog.approveSelection();
						}
					});
				} catch (AWTException e) {
					e.printStackTrace();
				}
			}
		}).start();
		clickButton(_menu._exportFileButton);
	}

}
