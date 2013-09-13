package de.frosner.datagenerator.gui.main;

import java.awt.event.ActionEvent;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JTextField;

import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiTask;

public final class SwingMenuTestUtil {

	private SwingMenu _menu;

	public SwingMenuTestUtil(SwingMenu swingMenu) {
		_menu = swingMenu;
	}

	JTextField getGaussianNameField() {
		return _menu._gaussianNameField;
	}

	JTextField getGaussianMeanField() {
		return _menu._gaussianMeanField;
	}

	JTextField getGaussianSigmaField() {
		return _menu._gaussianSigmaField;
	}

	JTextField getExportFileField() {
		return _menu._exportFileField;
	}

	JFileChooser getExportFileChooser() {
		return _menu._exportFileDialog;
	}

	private void clickButton(final JButton button) {
		GuiActionRunner.execute(new GuiTask() {
			@Override
			protected void executeInEDT() {
				_menu.actionPerformed(new ActionEvent(button, 1, ""));
			}
		});
	}

	void clickAddFeatureButton() {
		clickButton(_menu._addFeatureButton);
	}

	void clickRemoveFeatureButton() {
		clickButton(_menu._removeFeatureButton);
	}

	void clickExportFileDialogButton() {
		clickButton(_menu._exportFileButton);
	}

	void selectFeature(final int i) {
		GuiActionRunner.execute(new GuiTask() {
			@Override
			protected void executeInEDT() {
				_menu._featureList.setSelectedIndex(i);
			}
		});

	}

	DefaultListModel getFeatureDefinitionListModel() {
		return _menu._featureListModel;
	}

	JList getFeatureDefinitionList() {
		return _menu._featureList;
	}

	String getLog() {
		return _menu._logAreaTextArea.getText();
	}

}