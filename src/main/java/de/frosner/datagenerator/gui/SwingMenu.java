package de.frosner.datagenerator.gui;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

import de.frosner.datagenerator.main.ApplicationMetaData;
import de.frosner.datagenerator.util.ExceptionUtil;

public final class SwingMenu extends JFrame {

	private static final long serialVersionUID = ApplicationMetaData.SERIAL_VERSION_UID;

	private static final int BUTTON_HEIGHT = 25;
	private static final int LABEL_HEIGHT = 25;
	private static final int LABEL_WIDTH = 50;
	private static final int TEXT_FIELD_HEIGHT = 25;
	private static final int TEXT_FIELD_WIDTH = 150;
	private static final int ROW_MARGIN = 15;
	private static final int PANEL_HEIGHT = 500;
	private static final int PANEL_WIDTH = 500;

	private final JPanel _panel = new JPanel();

	private final JButton _addFeatureButton = new JButton("Add Feature");
	private final JButton _removeFeatureButton = new JButton("Remove Feature");
	private final JButton _generateDataButton = new JButton("Generate Data");

	private final JLabel _gaussianNameLabel = new JLabel("Name", JLabel.RIGHT);
	private final JTextField _gaussianNameField = new JTextField();
	private final JLabel _gaussianMeanLabel = new JLabel("Mean", JLabel.RIGHT);
	private final JTextField _gaussianMeanField = new JTextField();
	private final JLabel _gaussianSigmaLabel = new JLabel("Sigma", JLabel.RIGHT);
	private final JTextField _gaussianSigmaField = new JTextField();

	public SwingMenu() {
		initUi();
		initPanel();
		initFeatureSection();
		initGenerationSection();

		HorizontalRuler.alignBottomAt(TEXT_FIELD_HEIGHT + ROW_MARGIN, _gaussianNameLabel, _gaussianNameField);
		HorizontalRuler.alignBottomAt(2 * (TEXT_FIELD_HEIGHT + ROW_MARGIN), _gaussianMeanLabel, _gaussianMeanField);
		HorizontalRuler.alignBottomAt(3 * (TEXT_FIELD_HEIGHT + ROW_MARGIN), _gaussianSigmaLabel, _gaussianSigmaField);
		VerticalRuler.alignRightAt(50, _gaussianNameLabel, _gaussianMeanLabel, _gaussianSigmaLabel);

		HorizontalRuler.alignBottomAt(4 * (TEXT_FIELD_HEIGHT + ROW_MARGIN), _addFeatureButton, _removeFeatureButton);
		VerticalRuler.alignRightAt(210, _addFeatureButton, _generateDataButton, _gaussianNameField, _gaussianMeanField,
				_gaussianSigmaField);

		HorizontalRuler.alignBottomAt(300, _generateDataButton);
		VerticalRuler.alignRightAt(450, _removeFeatureButton);
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

	private void initFeatureSection() {
		initGaussianFeatureMask();
		initAddFeatureButton();
		initRemoveFeatureButton();
	}

	private void initGenerationSection() {
		initGenerateDataButton();
	}

	private void initGaussianFeatureMask() {
		_gaussianNameLabel.setBounds(-1, -1, LABEL_WIDTH, LABEL_HEIGHT);
		_gaussianNameField.setBounds(-1, -1, TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT);
		_panel.add(_gaussianNameLabel);
		_panel.add(_gaussianNameField);
		_gaussianMeanLabel.setBounds(-1, -1, LABEL_WIDTH, LABEL_HEIGHT);
		_gaussianMeanField.setBounds(-1, -1, TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT);
		_panel.add(_gaussianMeanLabel);
		_panel.add(_gaussianMeanField);
		_gaussianSigmaLabel.setBounds(-1, -1, LABEL_WIDTH, LABEL_HEIGHT);
		_gaussianSigmaField.setBounds(-1, -1, TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT);
		_panel.add(_gaussianSigmaLabel);
		_panel.add(_gaussianSigmaField);

	}

	private void initAddFeatureButton() {
		int width = 120;
		int height = BUTTON_HEIGHT;
		_addFeatureButton.setBounds(-1, -1, width, height);
		_addFeatureButton.addActionListener(new AddFeatureButtonActionListener());
		_panel.add(_addFeatureButton);
	}

	private void initRemoveFeatureButton() {
		int width = 140;
		int height = BUTTON_HEIGHT;
		_removeFeatureButton.setBounds(-1, -1, width, height);
		_removeFeatureButton.addActionListener(new RemoveFeatureButtonActionListener());
		_panel.add(_removeFeatureButton);
	}

	private void initGenerateDataButton() {
		int width = 140;
		int height = BUTTON_HEIGHT;
		_generateDataButton.setBounds(-1, -1, width, height);
		_generateDataButton.addActionListener(new GenerateDataButtonActionListener());
		_panel.add(_generateDataButton);
	}

}
