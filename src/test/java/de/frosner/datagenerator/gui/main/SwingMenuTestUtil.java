package de.frosner.datagenerator.gui.main;

import static org.fest.assertions.Fail.fail;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;

import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiTask;

public final class SwingMenuTestUtil {

	private static final int DIALOG_OPEN_DELAY = 500;
	private static final int ROBOT_DELAY = 75;
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

	void tryToAddEnteredFeatureAndGiveUp() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				Robot robot;
				try {
					robot = new Robot();
					robot.delay(DIALOG_OPEN_DELAY);
					robot.keyPress(KeyEvent.VK_ENTER);
					robot.delay(ROBOT_DELAY);
					robot.keyRelease(KeyEvent.VK_ENTER);
					robot.delay(ROBOT_DELAY);
					robot.keyPress(KeyEvent.VK_ESCAPE);
					robot.delay(ROBOT_DELAY);
					robot.keyRelease(KeyEvent.VK_ESCAPE);
				} catch (AWTException e) {
				}
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
				Robot robot;
				try {
					robot = new Robot();
					robot.delay(DIALOG_OPEN_DELAY);
					robot.keyPress(KeyEvent.VK_ENTER);
					robot.delay(ROBOT_DELAY);
					robot.keyRelease(KeyEvent.VK_ENTER);
				} catch (AWTException e) {
				}
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
				Robot robot;
				try {
					robot = new Robot();
					robot.delay(DIALOG_OPEN_DELAY);
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

	public void setOptionDialogValueAfterDelay(final JOptionPane optionPane, final int value, final int delay) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				GuiActionRunner.execute(new GuiTask() {
					@Override
					protected void executeInEDT() throws InterruptedException {
						System.err.println("Executing in EDT");
						Thread.sleep(delay);
						System.err.println("Executed in EDT");
						optionPane.setValue(value);
					}
				});
			}
		}).start();
	}

	public void closeDialogAfterDelay(final JDialog dialog, final int delay) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				GuiActionRunner.execute(new GuiTask() {
					@Override
					protected void executeInEDT() throws InterruptedException {
						Thread.sleep(delay);
						dialog.setVisible(false);
					}
				});
			}
		}).start();

	}

	public static void delay(int ms) {
		try {
			new Robot().delay(ms);
		} catch (AWTException e) {
			fail();
			e.printStackTrace();
		}
	}

	public static void delay() {
		delay(ROBOT_DELAY);
	}

}
