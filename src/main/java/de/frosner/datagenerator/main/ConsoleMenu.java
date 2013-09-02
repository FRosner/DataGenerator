package de.frosner.datagenerator.main;

import java.util.List;

import com.google.common.collect.Lists;

public final class ConsoleMenu {

	private final List<String> _errors = Lists.newArrayList();
	private final List<String> _warnings = Lists.newArrayList();
	private final List<String> _infos = Lists.newArrayList();

	public static final ConsoleMenu INSTANCE = new ConsoleMenu();

	private ConsoleMenu() {
	}

	private MenuState _state = MenuState.MAIN_MENU;

	public void print() {
		switch (_state) {
		case MAIN_MENU:
			printMainMenu();
			break;
		case ADD_GAUSSIAN_FEATURE:
			printAddGaussianFeatureMenu();
			break;
		case SAMPLE:
			printSampleMenu();
			break;
		default:
			throw new IllegalStateException();
		}
	}

	private void printMainMenu() {
		printHeader();
		System.out.println("1) Add gaussian feature");
		System.out.println("2) Sample data");
		printSelection();
		resetMessages();
	}

	private void printAddGaussianFeatureMenu() {
		printHeader();
		System.out.println("Please specify a feature name, a mean and standard deviation.");
		resetMessages();
	}

	private void printSampleMenu() {
		printHeader();
		System.out.println("Please specify the number of instances and the file name to export to.");
		resetMessages();
	}

	public MenuState getState() {
		return _state;
	}

	private void printHeader() {
		clearScreen();
		displayErrors();
		displayWarnings();
		displayInfos();
		System.out.println();
	}

	private void printSelection() {
		System.out.print("Your selection: ");
	}

	public void setState(MenuState state) {
		_state = state;
	}

	private void resetMessages() {
		_errors.clear();
		_warnings.clear();
		_infos.clear();
	}

	private void displayInfos() {
		if (_infos.size() > 0) {
			for (String info : _infos) {
				System.out.println("Info: " + info);
			}
		}
	}

	private void displayWarnings() {
		if (_warnings.size() > 0) {
			for (String warning : _warnings) {
				System.out.println("Warning: " + warning);
			}
		}
	}

	private void displayErrors() {
		if (_errors.size() > 0) {
			for (String error : _errors) {
				System.out.println("Error: " + error);
			}
		}
	}

	public void clearScreen() {
		for (int i = 0; i < 40; i++) {
			System.out.println();
		}
	}

	public void addError(String error) {
		_errors.add(error);
	}

	public void addWarning(String warning) {
		_warnings.add(warning);
	}

	public void addInfo(String info) {
		_infos.add(info);
	}

}