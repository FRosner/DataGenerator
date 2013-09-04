package de.frosner.datagenerator.gui;

import static org.fest.assertions.Assertions.assertThat;

import javax.swing.JButton;

import org.junit.Before;
import org.junit.Test;

public class HorizontalRulerTest {

	private static final int BIG_BUTTON_HEIGHT = 80;
	private static final int BIG_BUTTON_WIDTH = 100;
	private static final int BIG_BUTTON_X = 0;
	private static final int SMALL_BUTTON_HEIGHT = 30;
	private static final int SMALL_BUTTON_WIDTH = 20;
	private static final int SMALL_BUTTON_X = 200;

	private JButton _bigButton = new JButton("Big Button");
	private JButton _smallButton = new JButton("Small Button");

	@Before
	public void initButtons() {
		_bigButton.setBounds(BIG_BUTTON_X, 0, BIG_BUTTON_WIDTH, BIG_BUTTON_HEIGHT);
		_smallButton.setBounds(SMALL_BUTTON_X, 30, SMALL_BUTTON_WIDTH, SMALL_BUTTON_HEIGHT);
	}

	@Test
	public void testAlignBottom() {
		HorizontalRuler.alignBottomAt(100, _smallButton, _bigButton);
		assertThat(_bigButton.getX()).isEqualTo(BIG_BUTTON_X);
		assertThat(_bigButton.getY()).isEqualTo(20);
		assertThat(_bigButton.getWidth()).isEqualTo(BIG_BUTTON_WIDTH);
		assertThat(_bigButton.getHeight()).isEqualTo(BIG_BUTTON_HEIGHT);
		assertThat(_smallButton.getX()).isEqualTo(SMALL_BUTTON_X);
		assertThat(_smallButton.getY()).isEqualTo(70);
		assertThat(_smallButton.getWidth()).isEqualTo(SMALL_BUTTON_WIDTH);
		assertThat(_smallButton.getHeight()).isEqualTo(SMALL_BUTTON_HEIGHT);
	}

	@Test
	public void testAlignTop() {
		HorizontalRuler.alignTopAt(100, _smallButton, _bigButton);
		assertThat(_bigButton.getX()).isEqualTo(BIG_BUTTON_X);
		assertThat(_bigButton.getY()).isEqualTo(100);
		assertThat(_bigButton.getWidth()).isEqualTo(BIG_BUTTON_WIDTH);
		assertThat(_bigButton.getHeight()).isEqualTo(BIG_BUTTON_HEIGHT);
		assertThat(_smallButton.getX()).isEqualTo(SMALL_BUTTON_X);
		assertThat(_smallButton.getY()).isEqualTo(100);
		assertThat(_smallButton.getWidth()).isEqualTo(SMALL_BUTTON_WIDTH);
		assertThat(_smallButton.getHeight()).isEqualTo(SMALL_BUTTON_HEIGHT);
	}

	@Test(expected = RulerOutOfBoundsException.class)
	public void testAlignTop_RulerOutOfBounds() {
		HorizontalRuler.alignTopAt(-1, _smallButton, _bigButton);
	}

	@Test(expected = RulerOutOfBoundsException.class)
	public void testAlignBottom_RulerOutOfBounds() {
		HorizontalRuler.alignBottomAt(-1, _smallButton, _bigButton);
	}
}
