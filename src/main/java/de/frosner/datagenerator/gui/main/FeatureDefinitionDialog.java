package de.frosner.datagenerator.gui.main;

import java.awt.Frame;

import javax.swing.JDialog;

import de.frosner.datagenerator.util.ApplicationMetaData;

public class FeatureDefinitionDialog extends JDialog {

	public static final String TITLE_ADD = "Add Feature";
	public static final String TITLE_EDIT = "Edit Feature";

	private static final long serialVersionUID = ApplicationMetaData.SERIAL_VERSION_UID;

	private int _featureIndexToEdit = -1;

	public FeatureDefinitionDialog(Frame parent, String title, boolean b) {
		super(parent, title, b);
	}

	public void setFeatureToEdit(int featureIndexToEdit) {
		_featureIndexToEdit = featureIndexToEdit;
		setTitle(TITLE_EDIT);
	}

	public int getFeatureToEdit() {
		return _featureIndexToEdit;
	}

	public boolean isInEditMode() {
		return _featureIndexToEdit >= 0;
	}

	public void leaveEditMode() {
		_featureIndexToEdit = -1;
		setTitle(TITLE_ADD);
	}

}
