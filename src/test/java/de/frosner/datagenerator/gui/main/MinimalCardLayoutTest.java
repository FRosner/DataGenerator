package de.frosner.datagenerator.gui.main;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.edt.GuiActionRunner.execute;

import java.awt.AWTException;
import java.awt.CardLayout;
import java.awt.Dimension;

import javax.swing.JPanel;

import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.edt.GuiTask;
import org.junit.Before;
import org.junit.BeforeClass;
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

	@BeforeClass
	public static void setUpOnce() {
		FailOnThreadViolationRepaintManager.install();
	}

	@Before
	public void initGUI() throws AWTException {
		_cardLayout = execute(new GuiQuery<CardLayout>() {
			@Override
			public CardLayout executeInEDT() {
				return new MinimalCardLayout();
			}
		});
		_cardHolder = execute(new GuiQuery<JPanel>() {
			@Override
			public JPanel executeInEDT() {
				return new JPanel(_cardLayout);
			}
		});
		_smallPanel = execute(new GuiQuery<JPanel>() {
			@Override
			public JPanel executeInEDT() {
				JPanel panel = new JPanel();
				panel.setPreferredSize(new Dimension(50, 50));
				return panel;
			}
		});
		_mediumPanel = execute(new GuiQuery<JPanel>() {
			@Override
			public JPanel executeInEDT() {
				JPanel panel = new JPanel();
				panel.setPreferredSize(new Dimension(100, 100));
				return panel;
			}
		});
		_bigPanel = execute(new GuiQuery<JPanel>() {
			@Override
			public JPanel executeInEDT() {
				JPanel panel = new JPanel();
				panel.setPreferredSize(new Dimension(150, 150));
				return panel;
			}
		});
	}

	@Test
	public void testPreferredLayoutSize_next() {
		assertThat(_cardLayout.preferredLayoutSize(_cardHolder)).isEqualTo(new Dimension(0, 0));
		execute(new GuiTask() {
			@Override
			protected void executeInEDT() throws Throwable {
				_cardHolder.add("small", _smallPanel);
				_cardHolder.add("medium", _mediumPanel);
				_cardHolder.add("big", _bigPanel);
			}
		});
		assertThat(_cardLayout.preferredLayoutSize(_cardHolder)).isEqualTo(new Dimension(50, 50));

		execute(new GuiTask() {
			@Override
			protected void executeInEDT() throws Throwable {
				_cardLayout.next(_cardHolder);
			}
		});
		assertThat(_cardLayout.preferredLayoutSize(_cardHolder)).isEqualTo(new Dimension(100, 100));

		execute(new GuiTask() {
			@Override
			protected void executeInEDT() throws Throwable {
				_cardLayout.next(_cardHolder);
			}
		});
		assertThat(_cardLayout.preferredLayoutSize(_cardHolder)).isEqualTo(new Dimension(150, 150));
	}

	@Test
	public void testPreferredLayoutSize_show() {
		assertThat(_cardLayout.preferredLayoutSize(_cardHolder)).isEqualTo(new Dimension(0, 0));
		execute(new GuiTask() {
			@Override
			protected void executeInEDT() throws Throwable {
				_cardHolder.add("small", _smallPanel);
				_cardHolder.add("medium", _mediumPanel);
				_cardHolder.add("big", _bigPanel);
			}
		});
		assertThat(_cardLayout.preferredLayoutSize(_cardHolder)).isEqualTo(new Dimension(50, 50));

		execute(new GuiTask() {
			@Override
			protected void executeInEDT() throws Throwable {
				_cardLayout.show(_cardHolder, "big");
			}
		});
		assertThat(_cardLayout.preferredLayoutSize(_cardHolder)).isEqualTo(new Dimension(150, 150));

		execute(new GuiTask() {
			@Override
			protected void executeInEDT() throws Throwable {
				_cardLayout.show(_cardHolder, "medium");
			}
		});
		assertThat(_cardLayout.preferredLayoutSize(_cardHolder)).isEqualTo(new Dimension(100, 100));
	}

}
