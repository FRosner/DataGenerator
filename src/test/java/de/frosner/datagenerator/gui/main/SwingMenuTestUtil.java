package de.frosner.datagenerator.gui.main;

import static org.fest.assertions.Fail.fail;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.swing.AbstractButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;

import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.edt.GuiTask;
import org.jgraph.graph.DefaultGraphCell;

import de.frosner.datagenerator.gui.services.FeatureDefinitionGraphVisualizationManager;
import de.frosner.datagenerator.gui.services.FeatureParameterDependencySelectorManager;
import de.frosner.datagenerator.gui.services.GenerationButtonsToggleManager;
import de.frosner.datagenerator.gui.services.GraphVisualizationTestUtil;
import de.frosner.datagenerator.gui.services.PreviewTableManager;
import de.frosner.datagenerator.gui.services.ProgressBarManager;
import de.frosner.datagenerator.gui.services.TextAreaLogManager;
import de.frosner.datagenerator.gui.verifiers.InputVerifier;
import de.frosner.datagenerator.testutils.GuiTestUtil;

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

	void clickButtonOrItem(final AbstractButton button) {
		GuiActionRunner.execute(new GuiTask() {
			@Override
			protected void executeInEDT() {
				_menu.actionPerformed(new ActionEvent(button, 1, ""));
			}
		});
	}

	void tryToAddEnteredFeature(final AbstractButton button) {
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
				_menu.actionPerformed(new ActionEvent(button, 1, ""));
			}
		});
	}

	void openAndCancelAddFeatureDialog(final AbstractButton button) {
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
				_menu.actionPerformed(new ActionEvent(button, 1, ""));
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

	void openAndCancelEditFeatureDialog() {
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
				pressAndReleaseKey(KeyEvent.VK_ESCAPE);
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

	void selectFeatureDefinitionEntryByName(String name) {
		final DefaultGraphCell cell = GraphVisualizationTestUtil.getCellByFeatureName(name);
		if (cell == null) {
			fail("No feature with the given name exists: " + name);
		}
		GuiActionRunner.execute(new GuiTask() {
			@Override
			protected void executeInEDT() {
				_menu._featureGraph.setSelectionCell(cell);
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

	Boolean clickAddButtonAndCheckComponentVerification(final AbstractButton button, final JTextField component) {
		return GuiActionRunner.execute(new GuiQuery<Boolean>() {
			@Override
			protected Boolean executeInEDT() {
				Boolean inputIsValid = null;

				ExecutorService executor = Executors.newFixedThreadPool(1);
				Future<Boolean> future = executor.submit(new Callable<Boolean>() {
					@Override
					public Boolean call() {
						delay(DIALOG_OPEN_DELAY);
						pressAndReleaseKey(KeyEvent.VK_ENTER);
						delay(DIALOG_OPEN_DELAY);
						boolean inputIsValid = component.getBackground().equals(InputVerifier.VALID_INPUT_WHITE);
						pressAndReleaseKey(KeyEvent.VK_ESCAPE);
						delay(DIALOG_OPEN_DELAY);
						return inputIsValid;
					}
				});

				_menu.actionPerformed(new ActionEvent(button, 1, ""));

				try {
					inputIsValid = future.get();
				} catch (InterruptedException e1) {
					fail(e1.getMessage());
				} catch (ExecutionException e2) {
					fail(e2.getMessage());
				}
				return inputIsValid;
			}
		});
	}

	Boolean clickEditButtonAndCheckComponentVerification(final JTextField component) {
		final String featureName = _menu._featureNameField.getText();
		final String gaussianMean = _menu._gaussianMeanField.getText();
		final String gaussianSigma = _menu._gaussianSigmaField.getText();
		final String bernoulliProbability = _menu._bernoulliProbabilityField.getText();
		final String categorialNumberOfStates = _menu._uniformCategorialNumberOfStatesField.getText();

		return GuiActionRunner.execute(new GuiQuery<Boolean>() {
			@Override
			protected Boolean executeInEDT() {
				Boolean inputIsValid = null;

				ExecutorService executor = Executors.newFixedThreadPool(1);
				Future<Boolean> future = executor.submit(new Callable<Boolean>() {
					@Override
					public Boolean call() {
						delay(DIALOG_OPEN_DELAY);
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
						delay(DIALOG_OPEN_DELAY);
						pressAndReleaseKey(KeyEvent.VK_ENTER);
						delay(DIALOG_OPEN_DELAY);
						boolean inputIsValid = component.getBackground().equals(InputVerifier.VALID_INPUT_WHITE);

						pressAndReleaseKey(KeyEvent.VK_ESCAPE);
						return inputIsValid;
					}
				});

				_menu.actionPerformed(new ActionEvent(_menu._editFeatureButton, 1, ""));

				try {
					inputIsValid = future.get();
				} catch (InterruptedException e1) {
					fail(e1.getMessage());
				} catch (ExecutionException e2) {
					fail(e2.getMessage());
				}
				return inputIsValid;
			}
		});
	}

	public static void resetComponentManagers() {
		TextAreaLogManager.stopManaging();
		PreviewTableManager.stopManaging();
		ProgressBarManager.stopManaging();
		GenerationButtonsToggleManager.stopManaging();
		FeatureDefinitionGraphVisualizationManager.stopManaging();
		FeatureParameterDependencySelectorManager.stopManaging();
	}
}
