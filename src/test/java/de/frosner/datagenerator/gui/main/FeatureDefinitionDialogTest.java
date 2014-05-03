package de.frosner.datagenerator.gui.main;

import static org.fest.assertions.Assertions.assertThat;

import java.awt.Frame;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import de.frosner.datagenerator.distributions.DummyDistribution;
import de.frosner.datagenerator.features.FeatureDefinition;
import de.frosner.datagenerator.testutils.SwingTests;

@Category(SwingTests.class)
public class FeatureDefinitionDialogTest {

	private FeatureDefinitionDialog _dialog;

	@Before
	public void initGUI() {
		_dialog = new FeatureDefinitionDialog(new Frame(), FeatureDefinitionDialog.TITLE_ADD);
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
