package de.frosner.datagenerator.gui;

import static org.fest.assertions.Assertions.assertThat;

import javax.swing.JButton;

import org.junit.Before;
import org.junit.Test;

public class VerticalRulerTest {

	private static final int BIG_BUTTON_HEIGHT = 100;
	private static final int BIG_BUTTON_WIDTH = 80;
	private static final int BIG_BUTTON_Y = 0;
	private static final int SMALL_BUTTON_HEIGHT = 20;
	private static final int SMALL_BUTTON_WIDTH = 30;
	private static final int SMALL_BUTTON_Y = 200;

	private VerticalRuler _ruler = new VerticalRuler(100);
	private JButton _bigButton = new JButton("Big Button");
	private JButton _smallButton = new JButton("Small Button");

	@Before
	public void initButtons() {
		_bigButton.setBounds(0, BIG_BUTTON_Y, BIG_BUTTON_WIDTH, BIG_BUTTON_HEIGHT);
		_smallButton.setBounds(30, SMALL_BUTTON_Y, SMALL_BUTTON_WIDTH, SMALL_BUTTON_HEIGHT);
	}

	@Test
	public void testAlignRight() {
		_ruler.alignRight(_smallButton, _bigButton);
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
		_ruler.alignLeft(_smallButton, _bigButton);
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
	public void testRulerOutOfBounds() {
		new VerticalRuler(-1);
	}
}
