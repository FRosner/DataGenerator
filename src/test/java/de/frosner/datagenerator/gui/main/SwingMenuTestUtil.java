package de.frosner.datagenerator.gui.main;

import static org.fest.assertions.Fail.fail;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;

import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiTask;

public final class SwingMenuTestUtil {

	private static final int DIALOG_OPEN_DELAY = 500;
	private static final int ROBOT_DELAY = 75;
	private SwingMenu _menu;
	private Robot _robot;

	SwingMenuTestUtil(SwingMenu swingMenu) {
		_menu = swingMenu;
		GuiActionRunner.execute(new GuiTask() {
			@Override
			protected void executeInEDT() {
				_menu.setVisible(true);
				_menu.toFront();
			}
		});
		try {
			_robot = new Robot();
		} catch (AWTException e) {
			fail(e.getMessage());
		}

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
				_robot.delay(DIALOG_OPEN_DELAY);
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

	public void pressAndReleaseKey(int key) {
		try {
			_robot = new Robot();
			_robot.delay(ROBOT_DELAY);
			_robot.keyPress(key);
			_robot.delay(ROBOT_DELAY);
			_robot.keyRelease(key);
			_robot.delay(ROBOT_DELAY);
		} catch (AWTException e) {
			fail();
			e.printStackTrace();
		}
	}

	public void delay(int ms) {
		_robot.delay(ms);
	}

	public void delay() {
		delay(ROBOT_DELAY);
	}

	public static void delayOnce(int ms) {
		try {
			new Robot().delay(ms);
		} catch (AWTException e) {
			fail(e.getMessage());
		}
	}

	public static void delayOnce() {
		delayOnce(ROBOT_DELAY);
	}

}
