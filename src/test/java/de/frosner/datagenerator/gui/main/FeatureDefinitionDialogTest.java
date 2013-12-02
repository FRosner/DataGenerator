package de.frosner.datagenerator.gui.main;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.edt.GuiActionRunner.execute;

import java.awt.AWTException;
import java.awt.Frame;

import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.edt.GuiQuery;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import de.frosner.datagenerator.util.SwingTests;

@Category(SwingTests.class)
public class FeatureDefinitionDialogTest {

	private FeatureDefinitionDialog _dialog;

	@BeforeClass
	public static void setUpOnce() {
		FailOnThreadViolationRepaintManager.install();
	}

	@Before
	public void initGUI() throws AWTException {
		_dialog = execute(new GuiQuery<FeatureDefinitionDialog>() {
			@Override
			public FeatureDefinitionDialog executeInEDT() {
				return new FeatureDefinitionDialog(new Frame(), FeatureDefinitionDialog.TITLE_ADD, true);
			}
		});
	}

	@Test
	public void testEnterAndLeaveEditMode() {
		_dialog.setFeatureToEdit(0);
		assertThat(_dialog.getTitle()).isEqualTo(FeatureDefinitionDialog.TITLE_EDIT);
		assertThat(_dialog.isInEditMode()).isTrue();
		_dialog.leaveEditMode();
		assertThat(_dialog.getTitle()).isEqualTo(FeatureDefinitionDialog.TITLE_ADD);
	}
}
