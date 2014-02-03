package de.frosner.datagenerator.gui.main;

import java.awt.Frame;

import javax.swing.JDialog;

import de.frosner.datagenerator.util.ApplicationMetaData;

public class FeatureDefinitionDialog extends JDialog {

	public static final String TITLE_ADD = "Add Feature";
	public static final String TITLE_EDIT = "Edit Feature";
	public static final int GAUSSIAN_PANEL_ROWS = 3;
	public static final int GAUSSIAN_PANEL_COLUMNS = 2;

	private static final long serialVersionUID = ApplicationMetaData.SERIAL_VERSION_UID;

	private static final boolean FORCED_FOCUS = true;

	private FeatureDefinitionEntry _featureToEdit;

	public FeatureDefinitionDialog(Frame parent, String title) {
		super(parent, title, FORCED_FOCUS);
	}

	public void setFeatureToEdit(FeatureDefinitionEntry featureIndexToEdit) {
		_featureToEdit = featureIndexToEdit;
		setTitle(TITLE_EDIT);
	}

	public FeatureDefinitionEntry getFeatureToEdit() {
		return _featureToEdit;
	}

	public boolean isInEditMode() {
		return _featureToEdit != null;
	}

	public void leaveEditMode() {
		_featureToEdit = null;
		setTitle(TITLE_ADD);
	}

}
