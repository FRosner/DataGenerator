package de.frosner.datagenerator.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import de.frosner.datagenerator.distributions.GaussianDistribution;
import de.frosner.datagenerator.features.FeatureDefinition;
import de.frosner.datagenerator.generator.DataGeneratorService;

public final class AddFeatureButtonActionListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		String name = SwingLauncher.GUI.getAddFeatureName();
		double mean = Double.parseDouble(SwingLauncher.GUI.getAddFeatureMean());
		double sigma = Double.parseDouble(SwingLauncher.GUI.getAddFeatureSigma());
		FeatureDefinition featureDefinition = new FeatureDefinition(name, new GaussianDistribution(mean, sigma));
		DataGeneratorService.INSTANCE.addFeatureDefinition(featureDefinition);
		SwingLauncher.GUI.addFeatureDefinition(featureDefinition);
	}

}
