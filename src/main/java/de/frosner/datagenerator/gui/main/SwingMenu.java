package de.frosner.datagenerator.gui.main;

import static de.frosner.datagenerator.gui.verifiers.InputVerifier.isDouble;
import static de.frosner.datagenerator.gui.verifiers.InputVerifier.isInteger;
import static de.frosner.datagenerator.gui.verifiers.InputVerifier.isName;
import static de.frosner.datagenerator.gui.verifiers.InputVerifier.verifyComponent;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpringLayout;
import javax.swing.border.LineBorder;

import org.uncommons.swing.SpringUtilities;

import de.frosner.datagenerator.distributions.GaussianDistribution;
import de.frosner.datagenerator.features.FeatureDefinition;
import de.frosner.datagenerator.generator.DataGeneratorService;
import de.frosner.datagenerator.util.ApplicationMetaData;
import de.frosner.datagenerator.util.VisibleForTesting;

public final class SwingMenu extends JFrame implements ActionListener {

	private static final long serialVersionUID = ApplicationMetaData.SERIAL_VERSION_UID;

	private static final int LINE_HEIGHT = 30;
	private static final int BUTTON_HEIGHT = LINE_HEIGHT - 3;
	private static final int LINE_WIDTH = 180;
	private static final int PADDING = 5;
	private static final int SECTION_MARGIN = 25;
	private static final int PANEL_HEIGHT = 500;
	private static final int PANEL_WIDTH = 500;

	@VisibleForTesting
	final JButton _addFeatureButton;
	@VisibleForTesting
	final JButton _removeFeatureButton;
	@VisibleForTesting
	final JButton _generateDataButton;

	private final JLabel _gaussianNameLabel;
	@VisibleForTesting
	final JTextField _gaussianNameField;
	private final JLabel _gaussianMeanLabel;
	@VisibleForTesting
	final JTextField _gaussianMeanField;
	private final JLabel _gaussianSigmaLabel;
	@VisibleForTesting
	final JTextField _gaussianSigmaField;

	private final JLabel _numberOfInstancesLabel;
	@VisibleForTesting
	final JTextField _numberOfInstancesField;
	private final JLabel _exportFileLabel;
	@VisibleForTesting
	final JFileChooser _exportFileDialog;
	@VisibleForTesting
	final JButton _exportFileButton;
	@VisibleForTesting
	final JTextField _exportFileField;

	@VisibleForTesting
	final DefaultListModel _featureListModel;
	@VisibleForTesting
	final JList _featureList;

	@VisibleForTesting
	final JProgressBar _progressBar;

	@VisibleForTesting
	final JTextArea _logAreaTextArea;
	private final JScrollPane _logArea;

	public SwingMenu() {
		setTitle(ApplicationMetaData.APPLICATION_NAME);
		// setSize(PANEL_WIDTH, PANEL_HEIGHT);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);

		Container contentPane = getContentPane();
		contentPane.setLayout(new SpringLayout());

		_gaussianNameLabel = new JLabel("Name", JLabel.RIGHT);
		_gaussianNameField = new JTextField();
		_gaussianNameField.setMaximumSize(new Dimension(LINE_WIDTH, LINE_HEIGHT));
		_gaussianNameField.setPreferredSize(new Dimension(LINE_WIDTH, LINE_HEIGHT));
		_gaussianMeanLabel = new JLabel("Mean", JLabel.RIGHT);
		_gaussianMeanField = new JTextField();
		_gaussianMeanField.setMaximumSize(new Dimension(LINE_WIDTH, LINE_HEIGHT));
		_gaussianMeanField.setPreferredSize(new Dimension(LINE_WIDTH, LINE_HEIGHT));
		_gaussianSigmaLabel = new JLabel("Sigma", JLabel.RIGHT);
		_gaussianSigmaField = new JTextField();
		_gaussianSigmaField.setMaximumSize(new Dimension(LINE_WIDTH, LINE_HEIGHT));
		_gaussianSigmaField.setPreferredSize(new Dimension(LINE_WIDTH, LINE_HEIGHT));
		_addFeatureButton = new JButton("Add Feature");
		_addFeatureButton.addActionListener(this);
		_addFeatureButton.setMaximumSize(new Dimension(LINE_WIDTH, BUTTON_HEIGHT));
		_addFeatureButton.setPreferredSize(new Dimension(LINE_WIDTH, BUTTON_HEIGHT));
		_featureListModel = new DefaultListModel();
		_featureList = new JList(_featureListModel);
		_featureList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		_featureList.setBorder(new LineBorder(Color.gray, 1));
		// _featureList.setSize(LINE_WIDTH - 2, LINE_HEIGHT * 3 + 2 * PADDING - 2);
		_removeFeatureButton = new JButton("Remove Feature");
		_removeFeatureButton.addActionListener(this);
		_removeFeatureButton.setMaximumSize(new Dimension(LINE_WIDTH, BUTTON_HEIGHT));
		_removeFeatureButton.setPreferredSize(new Dimension(LINE_WIDTH, BUTTON_HEIGHT));
		_numberOfInstancesLabel = new JLabel("#Instances", JLabel.RIGHT);
		_numberOfInstancesField = new JTextField();
		_exportFileLabel = new JLabel("Export File", JLabel.RIGHT);
		_exportFileDialog = new JFileChooser();
		_exportFileButton = new JButton("...");
		_exportFileButton.addActionListener(this);
		_exportFileField = new JTextField();
		_exportFileField.setEditable(false);
		_exportFileField.setMaximumSize(new Dimension(LINE_WIDTH - 25, LINE_HEIGHT));
		_exportFileField.setPreferredSize(new Dimension(LINE_WIDTH - 25, LINE_HEIGHT));
		_progressBar = new JProgressBar(0, 100);
		_progressBar.setMaximumSize(new Dimension(LINE_WIDTH, LINE_HEIGHT));
		_progressBar.setPreferredSize(new Dimension(LINE_WIDTH, LINE_HEIGHT));
		_generateDataButton = new JButton("Generate Data");
		_generateDataButton.setMaximumSize(new Dimension(LINE_WIDTH, BUTTON_HEIGHT));
		_generateDataButton.setPreferredSize(new Dimension(LINE_WIDTH, BUTTON_HEIGHT));
		_generateDataButton.addActionListener(this);
		_logAreaTextArea = new JTextArea(5, 25);
		_logArea = new JScrollPane(_logAreaTextArea);
		TextAreaLogger.setLogArea(_logAreaTextArea);
		_logAreaTextArea.setBorder(new LineBorder(Color.gray, 1));
		_logAreaTextArea.setEditable(false);
		_logAreaTextArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
		_logAreaTextArea.setAutoscrolls(true);
		_logArea.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		_logArea.setBounds(-1, -1, PANEL_WIDTH - 75, 125);

		JPanel featureSection = new JPanel();
		featureSection.setLayout(new SpringLayout());
		contentPane.add(featureSection);

		JPanel featureDefinitionPanel = new JPanel();
		featureSection.add(featureDefinitionPanel);
		featureDefinitionPanel.setLayout(new SpringLayout());
		featureDefinitionPanel.add(_gaussianNameLabel);
		featureDefinitionPanel.add(_gaussianNameField);
		featureDefinitionPanel.add(_gaussianMeanLabel);
		featureDefinitionPanel.add(_gaussianMeanField);
		featureDefinitionPanel.add(_gaussianSigmaLabel);
		featureDefinitionPanel.add(_gaussianSigmaField);
		featureDefinitionPanel.add(new JLabel());
		featureDefinitionPanel.add(_addFeatureButton, BorderLayout.EAST);
		SpringUtilities.makeCompactGrid(featureDefinitionPanel, 4, 2, 0, 0, PADDING, PADDING);

		JPanel featureListPanelWrapper = new JPanel();
		featureListPanelWrapper.setLayout(new SpringLayout());
		featureSection.add(featureListPanelWrapper);
		JPanel featureListPanel = new JPanel();
		featureListPanelWrapper.add(featureListPanel);
		featureListPanel.setLayout(new BorderLayout());
		featureListPanel.add(_featureList, BorderLayout.CENTER);
		featureListPanelWrapper.add(_removeFeatureButton, BorderLayout.EAST);
		SpringUtilities.makeCompactGrid(featureListPanelWrapper, 2, 1, 0, 0, PADDING, PADDING);

		SpringUtilities.makeCompactGrid(featureSection, 1, 2, 0, 0, PADDING, PADDING);

		contentPane.add(new JSeparator(JSeparator.HORIZONTAL));

		JPanel exportSection = new JPanel();
		exportSection.setLayout(new SpringLayout());
		contentPane.add(exportSection);

		JPanel exportDefinitionPanel = new JPanel();
		exportSection.add(exportDefinitionPanel);
		exportDefinitionPanel.setLayout(new SpringLayout());
		exportDefinitionPanel.add(_numberOfInstancesLabel);
		exportDefinitionPanel.add(_numberOfInstancesField);
		_numberOfInstancesField.setMaximumSize(new Dimension(LINE_WIDTH, LINE_HEIGHT));
		_numberOfInstancesField.setPreferredSize(new Dimension(LINE_WIDTH, LINE_HEIGHT));
		exportDefinitionPanel.add(_exportFileLabel);
		JPanel exportFileSubPanel = new JPanel();
		exportDefinitionPanel.add(exportFileSubPanel);
		exportFileSubPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		exportFileSubPanel.add(_exportFileField);
		exportFileSubPanel.add(_exportFileButton);
		SpringUtilities.makeCompactGrid(exportDefinitionPanel, 2, 2, 0, 0, PADDING, PADDING);

		JPanel generateDataPanel = new JPanel();
		exportSection.add(generateDataPanel);
		generateDataPanel.setLayout(new SpringLayout());
		generateDataPanel.add(new JLabel());
		generateDataPanel.add(_progressBar);
		generateDataPanel.add(new JLabel());
		generateDataPanel.add(_generateDataButton);
		SpringUtilities.makeCompactGrid(generateDataPanel, 2, 2, 0, 0, PADDING, PADDING);

		SpringUtilities.makeCompactGrid(exportSection, 1, 2, 0, 0, PADDING, PADDING);

		contentPane.add(new JSeparator(JSeparator.HORIZONTAL));

		JPanel logPanel = new JPanel();
		contentPane.add(logPanel);
		logPanel.setLayout(new SpringLayout());
		logPanel.add(_logArea);
		SpringUtilities.makeCompactGrid(logPanel, 1, 1, PADDING, PADDING, PADDING, PADDING);

		SpringUtilities.makeCompactGrid(contentPane, 5, 1, 15, 15, 15, 15);

		pack();
		setVisible(true);
	}

	public void enableGenerateDataButton(boolean enabled) {
		_generateDataButton.setEnabled(enabled);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(_addFeatureButton)) {
			if (verifyComponent(_gaussianNameField, isName(_gaussianNameField.getText()).isNotLongerThan(30).verify())
					& verifyComponent(_gaussianMeanField, isDouble(_gaussianMeanField.getText()).verify())
					& verifyComponent(_gaussianSigmaField, isDouble(_gaussianSigmaField.getText()).isPositive()
							.verify())) {
				String name = _gaussianNameField.getText();
				double mean = Double.parseDouble(_gaussianMeanField.getText());
				double sigma = Double.parseDouble(_gaussianSigmaField.getText());
				final FeatureDefinition featureDefinition = new FeatureDefinition(name, new GaussianDistribution(mean,
						sigma));
				new Thread(new Runnable() {
					@Override
					public void run() {
						DataGeneratorService.INSTANCE.addFeatureDefinition(featureDefinition);
					}
				}).start();
				_featureListModel.addElement(featureDefinition.getName());
				verifyComponent(_featureList, _featureListModel.getSize() > 0);
			}
		} else if (e.getSource().equals(_removeFeatureButton)) {
			final int selected = _featureList.getSelectedIndex();
			if (selected > -1) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						DataGeneratorService.INSTANCE.removeFeatureDefinition(selected);
					}
				}).start();
				_featureListModel.remove(selected);
			}
		} else if (e.getSource().equals(_exportFileButton)) {
			if (_exportFileDialog.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
				_exportFileField.setText(_exportFileDialog.getSelectedFile().getName());
				verifyComponent(_exportFileField, isName(_exportFileField.getText()).isFileName().verify());
			}
		} else if (e.getSource().equals(_generateDataButton)) {
			if (verifyComponent(_numberOfInstancesField, isInteger(_numberOfInstancesField.getText()).isPositive()
					.verify())
					& verifyComponent(_featureList, _featureListModel.getSize() > 0)
					& verifyComponent(_exportFileField, isName(_exportFileField.getText()).isFileName().verify())) {
				final int numberOfInstances = Integer.parseInt(_numberOfInstancesField.getText());
				final File exportFile = _exportFileDialog.getSelectedFile();
				new GenerateDataButtonWorker(numberOfInstances, exportFile).execute();
			}
		}
	}

}
