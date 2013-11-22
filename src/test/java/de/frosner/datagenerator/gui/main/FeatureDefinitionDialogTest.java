package de.frosner.datagenerator.gui.main;

import static org.fest.assertions.Assertions.assertThat;

import java.awt.Frame;

import org.junit.Test;

public class FeatureDefinitionDialogTest {

	@Test
	public void testEnterAndLeaveEditMode() {
		FeatureDefinitionDialog dialog = new FeatureDefinitionDialog(new Frame(), FeatureDefinitionDialog.TITLE_ADD,
				true);
		dialog.setFeatureToEdit(0);
		assertThat(dialog.getTitle()).isEqualTo(FeatureDefinitionDialog.TITLE_EDIT);
		assertThat(dialog.isInEditMode()).isTrue();
		dialog.leaveEditMode();
		assertThat(dialog.getTitle()).isEqualTo(FeatureDefinitionDialog.TITLE_ADD);
	}
}
