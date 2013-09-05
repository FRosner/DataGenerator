package de.frosner.datagenerator.gui;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import net.sf.qualitycheck.Check;
import net.sf.qualitycheck.exception.IllegalEmptyArgumentException;

import com.google.common.collect.Lists;

import de.frosner.datagenerator.distributions.GaussianDistribution;
import de.frosner.datagenerator.export.CsvExportConnection;
import de.frosner.datagenerator.features.FeatureDefinition;
import de.frosner.datagenerator.generator.ApplicationMetaData;
import de.frosner.datagenerator.generator.DataGenerator;
import de.frosner.datagenerator.generator.MenuState;

public final class ConsoleLauncher {

	private static final ConsoleMenu _menu = ConsoleMenu.INSTANCE;

	private static List<FeatureDefinition> _featureDefinitions = Lists.newArrayList();

	public static void main(String[] args) {

		_menu.addInfo("Welcome to DataGenerator (" + ApplicationMetaData.VERSION + ")");

		Scanner userIn = new Scanner(System.in);
		while (true) {
			_menu.print();
			switch (_menu.getState()) {
			case MAIN_MENU:
				try {
					Integer selection = userIn.nextInt();
					switch (selection) {
					case 1:
						_menu.setState(MenuState.ADD_GAUSSIAN_FEATURE);
						break;
					case 2:
						Check.notEmpty(_featureDefinitions);
						_menu.setState(MenuState.SAMPLE);
						break;
					default:
						_menu.addError("Invalid selection!");
						break;
					}
				} catch (InputMismatchException e) {
					userIn.next();
					_menu.addError("Invalid selection!");
				} catch (IllegalEmptyArgumentException e) {
					_menu.addError("No features specified!");
				}
				break;
			case ADD_GAUSSIAN_FEATURE:
				try {
					System.out.print("Name:  ");
					String name = userIn.next();
					System.out.print("Mean:  ");
					double mean = userIn.nextDouble();
					System.out.print("Sigma: ");
					double sigma = userIn.nextDouble();
					FeatureDefinition newFeature = new FeatureDefinition(name, new GaussianDistribution(mean, sigma));
					_featureDefinitions.add(newFeature);
					_menu.addInfo("Successfully added feature " + newFeature);
					_menu.setState(MenuState.MAIN_MENU);
				} catch (InputMismatchException e) {
					userIn.next();
					_menu.addError("Invalid parameter!");
				}
				break;
			case SAMPLE:
				try {
					System.out.print("Filename:  ");
					String fileName = userIn.next();
					System.out.print("Number of instances: ");
					int numberOfInstances = userIn.nextInt();
					DataGenerator generator = new DataGenerator(numberOfInstances, new CsvExportConnection(
							new FileOutputStream(fileName)), Check.notEmpty(_featureDefinitions).toArray(
							new FeatureDefinition[0]));
					generator.generate();
					System.out.println("Bye!");
					System.exit(0);
				} catch (InputMismatchException e) {
					userIn.next();
					_menu.addError("Invalid parameter!");
				} catch (FileNotFoundException e) {
					_menu.addError("Could not find file " + e.getMessage());
				} catch (IOException e) {
					_menu.addError("An IOException occured: " + e.getMessage());
				}
				break;
			default:
				throw new IllegalStateException();
			}

		}

	}
}
