package de.frosner.datagenerator.gui;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.edt.GuiActionRunner.execute;

import javax.swing.JTextField;

import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.edt.GuiTask;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class NameVerifierTest {

	private JTextField _nameField;

	@BeforeClass
	public static void setUpOnce() {
		FailOnThreadViolationRepaintManager.install();
	}

	@Before
	public void initTextField() {
		_nameField = execute(new GuiQuery<JTextField>() {
			@Override
			public JTextField executeInEDT() {
				return new JTextField();
			}
		});
	}

	@Test
	public void testVerify() {
		execute(new GuiTask() {
			@Override
			public void executeInEDT() {
				_nameField.setText("");
				assertThat(NameVerifier.verify(_nameField)).isFalse();
				_nameField.setText(" ");
				assertThat(NameVerifier.verify(_nameField)).isFalse();
				_nameField.setText("Feature");
				assertThat(NameVerifier.verify(_nameField)).isTrue();
			}
		});
	}
}
