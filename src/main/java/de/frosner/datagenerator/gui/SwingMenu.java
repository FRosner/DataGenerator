package de.frosner.datagenerator.gui;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;

import de.frosner.datagenerator.main.ApplicationMetaData;
import de.frosner.datagenerator.util.ExceptionUtil;

public final class SwingMenu extends JFrame {

	private static final long serialVersionUID = ApplicationMetaData.SERIAL_VERSION_UID;

	private static final int DEFAULT_BUTTON_HEIGHT = 25;
	private static final int PANEL_HEIGHT = 500;
	private static final int PANEL_WIDTH = 500;

	private final JPanel _panel = new JPanel();

	private final JButton _addFeatureButton = new JButton("Add Feature");
	private final JButton _removeFeatureButton = new JButton("Remove Feature");
	private final JButton _generateDataButton = new JButton("Generate Data");

	public SwingMenu() {
		initUi();
		initPanel();
		initButtons();
	}

	private void initUi() {
		setTitle(ApplicationMetaData.APPLICATION_NAME);
		setSize(PANEL_WIDTH, PANEL_HEIGHT);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		try {
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		} catch (Exception e) {
			ExceptionUtil.uncheckException(e);
		}
	}

	private void initPanel() {
		getContentPane().add(_panel);
		_panel.setLayout(null);
	}

	private void initButtons() {
		initAddFeatureButton();
		initRemoveFeatureButton();
		initGenerateDataButton();
	}

	private void initAddFeatureButton() {
		int x = 100;
		int y = 100;
		int width = 120;
		int height = DEFAULT_BUTTON_HEIGHT;
		_addFeatureButton.setBounds(x, y, width, height);
		_addFeatureButton.addActionListener(new AddFeatureButtonActionListener());
		_panel.add(_addFeatureButton);
	}

	private void initRemoveFeatureButton() {
		int x = 300;
		int y = 100;
		int width = 140;
		int height = DEFAULT_BUTTON_HEIGHT;
		_removeFeatureButton.setBounds(x, y, width, height);
		_removeFeatureButton.addActionListener(new RemoveFeatureButtonActionListener());
		_panel.add(_removeFeatureButton);
	}

	private void initGenerateDataButton() {
		int x = 80;
		int y = 300;
		int width = 140;
		int height = DEFAULT_BUTTON_HEIGHT;
		_generateDataButton.setBounds(x, y, width, height);
		_generateDataButton.addActionListener(new GenerateDataButtonActionListener());
		_panel.add(_generateDataButton);
	}

}
