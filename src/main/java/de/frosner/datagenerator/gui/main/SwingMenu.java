package de.frosner.datagenerator.gui.main;

import static de.frosner.datagenerator.gui.verifiers.InputVerifier.isDouble;
import static de.frosner.datagenerator.gui.verifiers.InputVerifier.isInteger;
import static de.frosner.datagenerator.gui.verifiers.InputVerifier.isName;
import static de.frosner.datagenerator.gui.verifiers.InputVerifier.verifyComponent;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpringLayout;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import org.jfree.ui.ExtensionFileFilter;
import org.uncommons.swing.SpringUtilities;

import com.google.common.collect.Lists;

import de.frosner.datagenerator.distributions.GaussianDistribution;
import de.frosner.datagenerator.export.CsvExportConfiguration;
import de.frosner.datagenerator.features.FeatureDefinition;
import de.frosner.datagenerator.generator.DataGeneratorService;
import de.frosner.datagenerator.util.ApplicationMetaData;
import de.frosner.datagenerator.util.VisibleForTesting;

public final class SwingMenu extends JFrame implements ActionListener {

	private static final long serialVersionUID = ApplicationMetaData.SERIAL_VERSION_UID;

	private static final int LINE_HEIGHT = 30;
	private static final int LINE_WIDTH = 180;
	private static final int PADDING = 5;

	@VisibleForTesting
	final JButton _addFeatureButton;
	@VisibleForTesting
	final JButton _removeFeatureButton;
	@VisibleForTesting
	final JButton _generateDataButton;
	@VisibleForTesting
	final JButton _abortDataGenerationButton;

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
	final JCheckBox _exportInstanceIdsBox;
	@VisibleForTesting
	final JCheckBox _exportFeatureNamesBox;

	@VisibleForTesting
	static final FileFilter CSV_FILE_FILTER = new ExtensionFileFilter("Comma Separated Values (.csv)", "csv");
	@VisibleForTesting
	static final FileFilter ALL_FILE_FILTER = new AllFileFilter();

	@VisibleForTesting
	final DefaultListModel _featureListModel;
	@VisibleForTesting
	final JList _featureList;
	private final JScrollPane _featureListScroller;

	@VisibleForTesting
	final TableModel _previewTableModel;
	@VisibleForTesting
	final JTable _previewTable;

	@VisibleForTesting
	final JProgressBar _progressBar;

	@VisibleForTesting
	final JTextArea _logArea;
	private final JScrollPane _logAreaScroller;

	private final JMenuBar _menuBar;
	private final JMenu _helpMenu;
	private final JMenuItem _aboutMenuItem;

	private GenerateDataButtonWorker _generateDataButtonWorker;

	public SwingMenu() {
		// BEGIN frame initialization
		setTitle(ApplicationMetaData.getName());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(true);
		// END frame initialization

		// BEGIN menu bar initialization
		_menuBar = new JMenuBar();
		setJMenuBar(_menuBar);
		_helpMenu = new JMenu("Help");
		_helpMenu.setMnemonic(KeyEvent.VK_O);
		_aboutMenuItem = new JMenuItem("About");
		_aboutMenuItem.addActionListener(this);
		_helpMenu.add(_aboutMenuItem);
		_menuBar.add(_helpMenu);
		// END menu bar initialization

		// BEGIN component definition
		_gaussianNameLabel = new JLabel("Name", JLabel.RIGHT);
		_gaussianNameField = new JTextField();
		_gaussianNameField.setPreferredSize(new Dimension(LINE_WIDTH, LINE_HEIGHT));
		_gaussianMeanLabel = new JLabel("Mean", JLabel.RIGHT);
		_gaussianMeanField = new JTextField();
		_gaussianMeanField.setPreferredSize(new Dimension(LINE_WIDTH, LINE_HEIGHT));
		_gaussianSigmaLabel = new JLabel("Sigma", JLabel.RIGHT);
		_gaussianSigmaField = new JTextField();
		_gaussianSigmaField.setPreferredSize(new Dimension(LINE_WIDTH, LINE_HEIGHT));
		_addFeatureButton = new JButton("Add Feature");
		_addFeatureButton.addActionListener(this);
		_featureListModel = new DefaultListModel();
		_featureList = new JList(_featureListModel);
		_featureList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		_featureList.setVisibleRowCount(4);
		_featureList.setFixedCellWidth(10);
		_featureList.setFocusable(true);
		_featureListScroller = new JScrollPane(_featureList);
		_featureListScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		_featureListScroller.setFocusable(false);
		_removeFeatureButton = new JButton("Remove Feature");
		_removeFeatureButton.addActionListener(this);
		_numberOfInstancesLabel = new JLabel("#Instances", JLabel.RIGHT);
		_numberOfInstancesField = new JTextField();
		_numberOfInstancesField.setPreferredSize(new Dimension(LINE_WIDTH, LINE_HEIGHT));
		_exportFileLabel = new JLabel("Export File", JLabel.RIGHT);
		_exportFileDialog = new ExportFileChooser();
		_exportFileDialog.setAcceptAllFileFilterUsed(false);
		_exportFileDialog.addChoosableFileFilter(ALL_FILE_FILTER);
		_exportFileDialog.addChoosableFileFilter(CSV_FILE_FILTER);
		_exportFileButton = new JButton("...");
		_exportFileButton.addActionListener(this);
		_exportFileField = new JTextField();
		_exportFileField.setEditable(false);
		_exportFileField.setPreferredSize(new Dimension(LINE_WIDTH - 25, LINE_HEIGHT));
		_exportInstanceIdsBox = new JCheckBox("Instance IDs");
		_exportFeatureNamesBox = new JCheckBox("Feature Names");
		_progressBar = new JProgressBar(0, 100);
		ProgressBarManager.setProgressBar(_progressBar);
		_generateDataButton = new JButton("Generate Data");
		_generateDataButton.addActionListener(this);
		_abortDataGenerationButton = new JButton("Abort Generation");
		_abortDataGenerationButton.addActionListener(this);
		_abortDataGenerationButton.setEnabled(false);
		_previewTableModel = new DefaultTableModel(8, 4);
		_previewTable = new JTable(_previewTableModel);
		_previewTable.setEnabled(false);
		PreviewTableManager.setPreviewTable(_previewTableModel);
		_logArea = new JTextArea(5, 25);
		_logAreaScroller = new JScrollPane(_logArea);
		TextAreaLogger.setLogArea(_logArea);
		_logArea.setBorder(new LineBorder(Color.gray, 1));
		_logArea.setEditable(false);
		_logArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
		_logArea.setAutoscrolls(true);
		_logAreaScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		// END component definition

		// BEGIN layout
		Container contentPane = getContentPane();
		contentPane.setLayout(new SpringLayout());

		JPanel topPanel = new JPanel();
		topPanel.setLayout(new SpringLayout());
		contentPane.add(topPanel);

		JPanel addFeaturePanel = new JPanel();
		topPanel.add(addFeaturePanel);
		addFeaturePanel.setLayout(new SpringLayout());
		addFeaturePanel.add(_gaussianNameLabel);
		addFeaturePanel.add(_gaussianNameField);
		addFeaturePanel.add(_gaussianMeanLabel);
		addFeaturePanel.add(_gaussianMeanField);
		addFeaturePanel.add(_gaussianSigmaLabel);
		addFeaturePanel.add(_gaussianSigmaField);
		addFeaturePanel.add(new JLabel());
		SpringUtilities.makeCompactGrid(addFeaturePanel, 3, 2, 0, 0, PADDING, PADDING);

		JPanel removeFeaturePanel = new JPanel();
		removeFeaturePanel.setLayout(new SpringLayout());
		topPanel.add(removeFeaturePanel);
		JPanel featureListPanel = new JPanel();
		removeFeaturePanel.add(featureListPanel);
		featureListPanel.setLayout(new BorderLayout());
		featureListPanel.add(_featureListScroller, BorderLayout.CENTER);
		featureListPanel.setPreferredSize(new Dimension(LINE_WIDTH, 5));
		SpringUtilities.makeCompactGrid(removeFeaturePanel, 1, 1, 0, 0, PADDING, PADDING);

		JPanel generateDataPanel = new JPanel();
		topPanel.add(generateDataPanel);
		generateDataPanel.setLayout(new SpringLayout());
		generateDataPanel.add(_numberOfInstancesLabel);
		generateDataPanel.add(_numberOfInstancesField);
		generateDataPanel.add(_exportFileLabel);
		JPanel exportFileSubPanel = new JPanel();
		generateDataPanel.add(exportFileSubPanel);
		exportFileSubPanel.setLayout(new SpringLayout());
		exportFileSubPanel.add(_exportFileField);
		exportFileSubPanel.add(_exportFileButton);
		JPanel exportCheckBoxPanel = new JPanel();
		generateDataPanel.add(new JLabel());
		generateDataPanel.add(exportCheckBoxPanel);
		exportCheckBoxPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		exportCheckBoxPanel.add(_exportInstanceIdsBox);
		exportCheckBoxPanel.add(_exportFeatureNamesBox);
		SpringUtilities.makeCompactGrid(exportFileSubPanel, 1, 2, 0, 0, 0, 0);
		SpringUtilities.makeCompactGrid(generateDataPanel, 3, 2, 0, 0, PADDING, PADDING);

		JPanel addFeatureButtonPanel = new JPanel();
		addFeatureButtonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		topPanel.add(addFeatureButtonPanel);
		addFeatureButtonPanel.add(_addFeatureButton, BorderLayout.EAST);

		JPanel removeFeatureButtonPanel = new JPanel();
		removeFeatureButtonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		topPanel.add(removeFeatureButtonPanel);
		removeFeatureButtonPanel.add(_removeFeatureButton, BorderLayout.EAST);

		JPanel generateDataButtonPanel = new JPanel();
		generateDataButtonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		topPanel.add(generateDataButtonPanel);
		generateDataButtonPanel.add(_generateDataButton, BorderLayout.EAST);

		SpringUtilities.makeCompactGrid(topPanel, 2, 3, 0, 0, 0, 0);

		contentPane.add(new JSeparator(JSeparator.HORIZONTAL));

		contentPane.add(_previewTable);

		contentPane.add(new JSeparator(JSeparator.HORIZONTAL));

		JPanel progressPanel = new JPanel();
		contentPane.add(progressPanel);
		progressPanel.setLayout(new SpringLayout());
		progressPanel.add(_progressBar);
		progressPanel.add(_abortDataGenerationButton);
		SpringUtilities.makeCompactGrid(progressPanel, 1, 2, 0, 0, 0, 0);

		contentPane.add(_logAreaScroller);

		SpringUtilities.makeCompactGrid(contentPane, 6, 1, 15, 15, 15, 15);
		// END layout

		// BEGIN define custom focus traversal
		List<Component> focusOrder = Lists.newArrayList();
		focusOrder.add(_gaussianNameField);
		focusOrder.add(_gaussianMeanField);
		focusOrder.add(_gaussianSigmaField);
		focusOrder.add(_addFeatureButton);
		focusOrder.add(_featureList);
		focusOrder.add(_removeFeatureButton);
		focusOrder.add(_numberOfInstancesField);
		focusOrder.add(_exportFileButton);
		focusOrder.add(_exportInstanceIdsBox);
		focusOrder.add(_exportFeatureNamesBox);
		focusOrder.add(_generateDataButton);
		focusOrder.add(_abortDataGenerationButton);
		setFocusTraversalPolicy(new OrderedFocusTraversalPolicy(focusOrder));
		// END define custom focus traversal

		// BEGIN finalize
		pack();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Point middle = new Point(screenSize.width / 2, screenSize.height / 2);
		Point newLocation = new Point(middle.x - (getWidth() / 2), middle.y - (getHeight() / 2));
		setLocation(newLocation);
		setMinimumSize(getSize());
		setVisible(true);
		// END finalize
	}

	public void enableGenerateDataButton(final boolean enabled) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				_generateDataButton.setEnabled(enabled);

			}
		});
	}

	public void enableAbortDataGenerationButton(final boolean enabled) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				_abortDataGenerationButton.setEnabled(enabled);

			}
		});
	}

	public void detachGenerateDataButtonWorker() {
		_generateDataButtonWorker = null;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source.equals(_addFeatureButton)) {
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

		} else if (source.equals(_removeFeatureButton)) {
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

		} else if (source.equals(_exportFileButton)) {
			if (_exportFileDialog.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
				_exportFileField.setText(_exportFileDialog.getSelectedFile().getPath());
				verifyComponent(_exportFileField, isName(_exportFileField.getText()).isFileName().verify());
			}

		} else if (e.getSource().equals(_generateDataButton)) {
			if (verifyComponent(_numberOfInstancesField, isInteger(_numberOfInstancesField.getText()).isPositive()
					.verify())
					& verifyComponent(_featureList, _featureListModel.getSize() > 0)
					& verifyComponent(_exportFileField, isName(_exportFileField.getText()).isFileName().verify())) {
				final int numberOfInstances = Integer.parseInt(_numberOfInstancesField.getText());
				final File exportFile = _exportFileDialog.getSelectedFile();
				final boolean exportInstanceIds = _exportInstanceIdsBox.isSelected();
				final boolean exportFeatureNames = _exportFeatureNamesBox.isSelected();
				_generateDataButtonWorker = new GenerateDataButtonWorker(numberOfInstances, new CsvExportConfiguration(
						exportFile, exportInstanceIds, exportFeatureNames));
				_generateDataButtonWorker.execute();
			}

		} else if (source.equals(_abortDataGenerationButton)) {
			if (_generateDataButtonWorker != null) {
				_generateDataButtonWorker.cancel(true);
			}

		} else if (source.equals(_aboutMenuItem)) {
			JTextArea applicationMetaData = new JTextArea(ApplicationMetaData.getName() + "\nVersion: "
					+ ApplicationMetaData.getVersion() + "\nRevision: " + ApplicationMetaData.getRevision()
					+ "\nBuilt on: " + ApplicationMetaData.getTimestamp());
			applicationMetaData.setEditable(false);
			JOptionPane dialog = new JOptionPane(applicationMetaData, JOptionPane.INFORMATION_MESSAGE,
					JOptionPane.DEFAULT_OPTION);
			dialog.createDialog("About").setVisible(true);

		} else {
			throw new UnsupportedOperationException("Unknown action event source: " + e.getSource().toString());
		}
	}

}
