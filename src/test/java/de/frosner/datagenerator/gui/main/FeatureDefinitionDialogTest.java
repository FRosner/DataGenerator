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

import de.frosner.datagenerator.distributions.DummyDistribution;
import de.frosner.datagenerator.features.FeatureDefinition;
import de.frosner.datagenerator.testutils.SwingTests;

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
				return new FeatureDefinitionDialog(new Frame(), FeatureDefinitionDialog.TITLE_ADD);
			}
		});
	}

	@Test
	public void testEnterAndLeaveEditMode() {
		DummyFeatureDefinitionEntry featureToEdit = new DummyFeatureDefinitionEntry(new FeatureDefinition("test",
				new DummyDistribution()));
		_dialog.setFeatureToEdit(featureToEdit);
		assertThat(_dialog.getTitle()).isEqualTo(FeatureDefinitionDialog.TITLE_EDIT);
		assertThat(_dialog.isInEditMode()).isTrue();
		_dialog.leaveEditMode();
		assertThat(_dialog.getTitle()).isEqualTo(FeatureDefinitionDialog.TITLE_ADD);
	}
}
