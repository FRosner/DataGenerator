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

public class IntegerVerifierTest {

	private JTextField _integerField;

	@BeforeClass
	public static void setUpOnce() {
		FailOnThreadViolationRepaintManager.install();
	}

	@Before
	public void initTextField() {
		_integerField = execute(new GuiQuery<JTextField>() {
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
				_integerField.setText("");
				assertThat(IntegerVerifier.verify(_integerField)).isFalse();
				_integerField.setText(" ");
				assertThat(IntegerVerifier.verify(_integerField)).isFalse();
				_integerField.setText("x");
				assertThat(IntegerVerifier.verify(_integerField)).isFalse();
				_integerField.setText("-1.0");
				assertThat(IntegerVerifier.verify(_integerField)).isFalse();
				_integerField.setText("1.02E-10");
				assertThat(IntegerVerifier.verify(_integerField)).isFalse();
				_integerField.setText("1.02E10");
				assertThat(IntegerVerifier.verify(_integerField)).isFalse();
				_integerField.setText("1");
				assertThat(IntegerVerifier.verify(_integerField)).isTrue();
				_integerField.setText("-11");
				assertThat(IntegerVerifier.verify(_integerField)).isTrue();
			}
		});
	}
}
