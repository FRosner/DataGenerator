package de.frosner.datagenerator.gui;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import de.frosner.datagenerator.main.ApplicationMetaData;

public final class SwingMenu extends JFrame {

	private static final long serialVersionUID = ApplicationMetaData.SERIAL_VERSION_UID;

	private static final int BUTTON_HEIGHT = 25;
	private static final int LABEL_HEIGHT = 25;
	private static final int LABEL_WIDTH = 100;
	private static final int TEXT_FIELD_HEIGHT = 25;
	private static final int TEXT_FIELD_WIDTH = 180;
	private static final int ROW_MARGIN = 15;
	private static final int SECTION_MARGIN = 25;
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

	private final JLabel _numberOfInstancesLabel = new JLabel("#Instances", JLabel.RIGHT);
	private final JTextField _numberOfInstancesField = new JTextField();
	private final JLabel _exportFileLabel = new JLabel("Export File", JLabel.RIGHT);
	private final JFileChooser _exportFileDialog = new JFileChooser();
	private final JButton _exportFileButton = new JButton(".");
	private final JTextField _exportFileField = new JTextField();

	private final JList _featureList = new JList(new Object[] { "Feature 1", "Feature 2", "Feature 3" });

	private final JProgressBar _progressBar = new JProgressBar(0, 100);

	private final JTextArea _logArea = new JTextArea();

	public SwingMenu() {
		initUi();
		initPanel();
		initFeatureSection();
		initGenerationSection();
		initLogArea();

		HorizontalRuler.alignBottomAt(TEXT_FIELD_HEIGHT + ROW_MARGIN, _gaussianNameLabel, _gaussianNameField);
		HorizontalRuler.alignBottomAt(2 * (TEXT_FIELD_HEIGHT + ROW_MARGIN), _gaussianMeanLabel, _gaussianMeanField);
		HorizontalRuler.alignBottomAt(3 * (TEXT_FIELD_HEIGHT + ROW_MARGIN), _gaussianSigmaLabel, _gaussianSigmaField,
				_featureList);
		HorizontalRuler.alignBottomAt(4 * (TEXT_FIELD_HEIGHT + ROW_MARGIN), _addFeatureButton, _removeFeatureButton);
		HorizontalRuler.alignBottomAt(5 * (TEXT_FIELD_HEIGHT + ROW_MARGIN) + SECTION_MARGIN, _numberOfInstancesField,
				_numberOfInstancesLabel);
		HorizontalRuler.alignBottomAt(6 * (TEXT_FIELD_HEIGHT + ROW_MARGIN) + SECTION_MARGIN, _exportFileLabel,
				_exportFileButton, _exportFileField);
		HorizontalRuler.alignBottomAt(7 * (TEXT_FIELD_HEIGHT + ROW_MARGIN) + SECTION_MARGIN, _generateDataButton,
				_progressBar);
		HorizontalRuler.alignBottomAt(11 * (TEXT_FIELD_HEIGHT + ROW_MARGIN) + SECTION_MARGIN / 2, _logArea);

		VerticalRuler.alignRightAt(260, _addFeatureButton, _generateDataButton, _gaussianNameField, _gaussianMeanField,
				_gaussianSigmaField, _exportFileButton, _numberOfInstancesField);
		VerticalRuler.alignRightAt(_exportFileButton.getX(), _exportFileField);
		VerticalRuler.alignRightAt(75, _gaussianNameLabel, _gaussianMeanLabel, _gaussianSigmaLabel,
				_numberOfInstancesLabel, _exportFileLabel);
		VerticalRuler.alignRightAt(PANEL_WIDTH - 40, _removeFeatureButton, _featureList, _progressBar, _logArea);
	}

	private void initUi() {
		setTitle(ApplicationMetaData.APPLICATION_NAME);
		setSize(PANEL_WIDTH, PANEL_HEIGHT);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
	}

	private void initPanel() {
		getContentPane().add(_panel);
		_panel.setLayout(null);
	}

	private void initFeatureSection() {
		initGaussianFeatureMask();
		initFeatureList();
		initAddFeatureButton();
		initRemoveFeatureButton();
	}

	private void initGenerationSection() {
		initGenerationMask();
		initGenerateDataButton();
		initGeneratorProgressBar();
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

	private void initFeatureList() {
		_featureList.setBounds(-1, -1, TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT * 3 + ROW_MARGIN * 2);
		_featureList.setBorder(new LineBorder(Color.BLACK, 1));
		_panel.add(_featureList);
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

	private void initGenerationMask() {
		_numberOfInstancesLabel.setBounds(-1, -1, LABEL_WIDTH, LABEL_HEIGHT);
		_numberOfInstancesField.setBounds(-1, -1, TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT);
		_exportFileLabel.setBounds(-1, -1, LABEL_WIDTH, LABEL_HEIGHT);
		_exportFileButton.setBounds(-1, -1, TEXT_FIELD_HEIGHT, TEXT_FIELD_HEIGHT);
		_exportFileButton.addActionListener(new ExportFileButtonActionListener());
		_exportFileField.setBounds(-1, -1, TEXT_FIELD_WIDTH - TEXT_FIELD_HEIGHT, TEXT_FIELD_HEIGHT);
		_panel.add(_numberOfInstancesLabel);
		_panel.add(_numberOfInstancesField);
		_panel.add(_exportFileLabel);
		_panel.add(_exportFileButton);
		_panel.add(_exportFileField);
	}

	private void initGenerateDataButton() {
		int width = 140;
		int height = BUTTON_HEIGHT;
		_generateDataButton.setBounds(-1, -1, width, height);
		_generateDataButton.addActionListener(new GenerateDataButtonActionListener());
		_panel.add(_generateDataButton);
	}

	private void initGeneratorProgressBar() {
		_progressBar.setBounds(-1, -1, TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT);
		_progressBar.setValue(33);
		_panel.add(_progressBar);
	}

	private void initLogArea() {
		_logArea.setBounds(-1, -1, PANEL_WIDTH - 75, 125);
		_logArea.setBorder(new LineBorder(Color.BLACK, 1));
		_panel.add(_logArea);
	}

	public void openExportFileChooserDialog() {
		if (_exportFileDialog.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			_exportFileField.setText(_exportFileDialog.getSelectedFile().getName());
		}
	}

}
