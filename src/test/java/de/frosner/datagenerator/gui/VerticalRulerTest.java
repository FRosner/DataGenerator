package de.frosner.datagenerator.gui;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.edt.GuiActionRunner.execute;

import javax.swing.JButton;

import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.edt.GuiTask;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class VerticalRulerTest {

	private static final int BIG_BUTTON_HEIGHT = 100;
	private static final int BIG_BUTTON_WIDTH = 80;
	private static final int BIG_BUTTON_Y = 0;
	private static final int SMALL_BUTTON_HEIGHT = 20;
	private static final int SMALL_BUTTON_WIDTH = 30;
	private static final int SMALL_BUTTON_Y = 200;

	private JButton _bigButton;
	private JButton _smallButton;

	@BeforeClass
	public static void setUpOnce() {
		FailOnThreadViolationRepaintManager.install();
	}

	@Before
	public void initButtons() {
		_bigButton = execute(new GuiQuery<JButton>() {
			@Override
			public JButton executeInEDT() {
				return new JButton("Big Button");
			}
		});
		execute(new GuiTask() {
			@Override
			public void executeInEDT() {
				_bigButton.setBounds(0, BIG_BUTTON_Y, BIG_BUTTON_WIDTH, BIG_BUTTON_HEIGHT);
			}
		});

		_smallButton = execute(new GuiQuery<JButton>() {
			@Override
			public JButton executeInEDT() {
				return new JButton("Small Button");
			}
		});
		execute(new GuiTask() {
			@Override
			public void executeInEDT() {
				_smallButton.setBounds(30, SMALL_BUTTON_Y, SMALL_BUTTON_WIDTH, SMALL_BUTTON_HEIGHT);
			}
		});
	}

	@Test
	public void testAlignRight() {
		VerticalRuler.alignRightAt(100, _smallButton, _bigButton);
		assertThat(_bigButton.getX()).isEqualTo(20);
		assertThat(_bigButton.getY()).isEqualTo(BIG_BUTTON_Y);
		assertThat(_bigButton.getWidth()).isEqualTo(BIG_BUTTON_WIDTH);
		assertThat(_bigButton.getHeight()).isEqualTo(BIG_BUTTON_HEIGHT);
		assertThat(_smallButton.getX()).isEqualTo(70);
		assertThat(_smallButton.getY()).isEqualTo(SMALL_BUTTON_Y);
		assertThat(_smallButton.getWidth()).isEqualTo(SMALL_BUTTON_WIDTH);
		assertThat(_smallButton.getHeight()).isEqualTo(SMALL_BUTTON_HEIGHT);
	}

	@Test
	public void testAlignLeft() {
		VerticalRuler.alignLeftAt(100, _smallButton, _bigButton);
		assertThat(_bigButton.getX()).isEqualTo(100);
		assertThat(_bigButton.getY()).isEqualTo(BIG_BUTTON_Y);
		assertThat(_bigButton.getWidth()).isEqualTo(BIG_BUTTON_WIDTH);
		assertThat(_bigButton.getHeight()).isEqualTo(BIG_BUTTON_HEIGHT);
		assertThat(_smallButton.getX()).isEqualTo(100);
		assertThat(_smallButton.getY()).isEqualTo(SMALL_BUTTON_Y);
		assertThat(_smallButton.getWidth()).isEqualTo(SMALL_BUTTON_WIDTH);
		assertThat(_smallButton.getHeight()).isEqualTo(SMALL_BUTTON_HEIGHT);
	}

	@Test(expected = RulerOutOfBoundsException.class)
	public void testAlignRight_RulerOutOfBounds() {
		VerticalRuler.alignRightAt(-1, _smallButton, _bigButton);
	}

	@Test(expected = RulerOutOfBoundsException.class)
	public void testAlignBottom_RulerOutOfBounds() {
		VerticalRuler.alignLeftAt(-1, _smallButton, _bigButton);
	}
}
