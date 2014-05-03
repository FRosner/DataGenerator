package de.frosner.datagenerator.gui.main;

import static org.fest.assertions.Assertions.assertThat;

import java.awt.AWTException;
import java.awt.CardLayout;
import java.awt.Dimension;

import javax.swing.JPanel;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import de.frosner.datagenerator.testutils.SwingTests;

@Category(SwingTests.class)
public class MinimalCardLayoutTest {

	private JPanel _cardHolder;
	private CardLayout _cardLayout;
	private JPanel _smallPanel;
	private JPanel _mediumPanel;
	private JPanel _bigPanel;

	@Before
	public void initGUI() throws AWTException {
		_cardLayout = new MinimalCardLayout();
		_cardHolder = new JPanel(_cardLayout);
		_smallPanel = new JPanel();
		_smallPanel.setPreferredSize(new Dimension(50, 50));
		_mediumPanel = new JPanel();
		_mediumPanel.setPreferredSize(new Dimension(100, 100));
		_bigPanel = new JPanel();
		_bigPanel.setPreferredSize(new Dimension(150, 150));
	}

	@Test
	public void testPreferredLayoutSize_next() {
		assertThat(_cardLayout.preferredLayoutSize(_cardHolder)).isEqualTo(new Dimension(0, 0));
		_cardHolder.add("small", _smallPanel);
		_cardHolder.add("medium", _mediumPanel);
		_cardHolder.add("big", _bigPanel);
		assertThat(_cardLayout.preferredLayoutSize(_cardHolder)).isEqualTo(new Dimension(50, 50));

		_cardLayout.next(_cardHolder);
		assertThat(_cardLayout.preferredLayoutSize(_cardHolder)).isEqualTo(new Dimension(100, 100));

		_cardLayout.next(_cardHolder);
		assertThat(_cardLayout.preferredLayoutSize(_cardHolder)).isEqualTo(new Dimension(150, 150));
	}

	@Test
	public void testPreferredLayoutSize_show() {
		assertThat(_cardLayout.preferredLayoutSize(_cardHolder)).isEqualTo(new Dimension(0, 0));
		_cardHolder.add("small", _smallPanel);
		_cardHolder.add("medium", _mediumPanel);
		_cardHolder.add("big", _bigPanel);
		assertThat(_cardLayout.preferredLayoutSize(_cardHolder)).isEqualTo(new Dimension(50, 50));

		_cardLayout.show(_cardHolder, "big");
		assertThat(_cardLayout.preferredLayoutSize(_cardHolder)).isEqualTo(new Dimension(150, 150));

		_cardLayout.show(_cardHolder, "medium");
		assertThat(_cardLayout.preferredLayoutSize(_cardHolder)).isEqualTo(new Dimension(100, 100));
	}

}
