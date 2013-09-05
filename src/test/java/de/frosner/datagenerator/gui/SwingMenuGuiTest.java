package de.frosner.datagenerator.gui;

import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.fixture.FrameFixture;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class SwingMenuGuiTest {

	private FrameFixture _testFrame;
	private SwingMenu _frame;

	@BeforeClass
	public static void setUpOnce() {
		FailOnThreadViolationRepaintManager.install();
	}

	@Before
	public void setUp() {
		_frame = GuiActionRunner.execute(new GuiQuery<SwingMenu>() {
			@Override
			protected SwingMenu executeInEDT() {
				return new SwingMenu();
			}
		});
		_testFrame = new FrameFixture(_frame);
		_testFrame.show();
	}

	@After
	public void tearDown() {
		_testFrame.cleanUp();
	}

	@Test
	public void test() {

	}
}
