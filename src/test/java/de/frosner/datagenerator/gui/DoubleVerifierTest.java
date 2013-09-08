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

public class DoubleVerifierTest {

	private JTextField _doubleField;

	@BeforeClass
	public static void setUpOnce() {
		FailOnThreadViolationRepaintManager.install();
	}

	@Before
	public void initTextField() {
		_doubleField = execute(new GuiQuery<JTextField>() {
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
				_doubleField.setText("");
				assertThat(DoubleVerifier.verify(_doubleField)).isFalse();
				_doubleField.setText(" ");
				assertThat(DoubleVerifier.verify(_doubleField)).isFalse();
				_doubleField.setText("x");
				assertThat(DoubleVerifier.verify(_doubleField)).isFalse();
				_doubleField.setText("1");
				assertThat(DoubleVerifier.verify(_doubleField)).isTrue();
				_doubleField.setText("1.0");
				assertThat(DoubleVerifier.verify(_doubleField)).isTrue();
				_doubleField.setText("-1.0");
				assertThat(DoubleVerifier.verify(_doubleField)).isTrue();
				_doubleField.setText("1.02E-10");
				assertThat(DoubleVerifier.verify(_doubleField)).isTrue();
			}
		});
	}
}
