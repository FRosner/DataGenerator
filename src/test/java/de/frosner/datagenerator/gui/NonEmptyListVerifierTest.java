package de.frosner.datagenerator.gui;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.edt.GuiActionRunner.execute;

import javax.swing.DefaultListModel;
import javax.swing.JList;

import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.edt.GuiTask;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class NonEmptyListVerifierTest {

	private JList _list;

	@BeforeClass
	public static void setUpOnce() {
		FailOnThreadViolationRepaintManager.install();
	}

	@Before
	public void initTextField() {
		_list = execute(new GuiQuery<JList>() {
			@Override
			public JList executeInEDT() {
				return new JList(new DefaultListModel());
			}
		});
	}

	@Test
	public void testVerify() {
		execute(new GuiTask() {
			@Override
			public void executeInEDT() {
				assertThat(NonEmptyListVerifier.verify(_list)).isFalse();
				((DefaultListModel) _list.getModel()).addElement("Test");
				assertThat(NonEmptyListVerifier.verify(_list)).isTrue();
			}
		});
	}
}
