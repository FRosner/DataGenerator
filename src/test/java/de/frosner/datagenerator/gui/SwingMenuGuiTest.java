package de.frosner.datagenerator.gui;

import static org.fest.assertions.Assertions.assertThat;

import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.fixture.FrameFixture;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

public class SwingMenuGuiTest {

	private FrameFixture _testFrame;
	private SwingMenu _frame;

	@BeforeClass
	public static void setUpOnce() {
		FailOnThreadViolationRepaintManager.install();
	}

	@Before
	public void setUp() {
		_frame = GuiActionRunner.execute(new GuiQuery<SwingMenu>() {
			@Override
			protected SwingMenu executeInEDT() {
				return new SwingMenu();
			}
		});
		_testFrame = new FrameFixture(_frame);
		_testFrame.show();
	}

	@After
	public void tearDown() {
		_testFrame.cleanUp();
	}

	@Test
	@Ignore("#58: Validators not implemented, yet")
	public void testValidateFeatureName() {
		_testFrame.textBox(SwingMenu.FEATURE_NAME_FIELD_NAME).enterText("");
		assertThat(_frame.getGaussianNameField().isValid()).isFalse();
		_testFrame.textBox(SwingMenu.FEATURE_NAME_FIELD_NAME).enterText("Feature 1");
		assertThat(_frame.getGaussianNameField().isValid()).isTrue();
	}

	@Test
	@Ignore("#58: Validators not implemented, yet")
	public void testValidateFeatureMean() {
		_testFrame.textBox(SwingMenu.FEATURE_MEAN_FIELD_NAME).enterText("");
		assertThat(_frame.getGaussianMeanField().isValid()).isFalse();
		_testFrame.textBox(SwingMenu.FEATURE_MEAN_FIELD_NAME).enterText("Test");
		assertThat(_frame.getGaussianMeanField().isValid()).isFalse();
		_testFrame.textBox(SwingMenu.FEATURE_MEAN_FIELD_NAME).enterText("1");
		assertThat(_frame.getGaussianMeanField().isValid()).isTrue();
		_testFrame.textBox(SwingMenu.FEATURE_MEAN_FIELD_NAME).enterText("-1");
		assertThat(_frame.getGaussianMeanField().isValid()).isTrue();
		_testFrame.textBox(SwingMenu.FEATURE_MEAN_FIELD_NAME).enterText("1.0");
		assertThat(_frame.getGaussianMeanField().isValid()).isTrue();
	}

	@Test
	@Ignore("#58: Validators not implemented, yet")
	public void testValidateFeatureSigma() {
		_testFrame.textBox(SwingMenu.FEATURE_SIGMA_FIELD_NAME).enterText("");
		assertThat(_frame.getGaussianSigmaField().isValid()).isFalse();
		_testFrame.textBox(SwingMenu.FEATURE_SIGMA_FIELD_NAME).enterText("Test");
		assertThat(_frame.getGaussianSigmaField().isValid()).isFalse();
		_testFrame.textBox(SwingMenu.FEATURE_SIGMA_FIELD_NAME).enterText("1");
		assertThat(_frame.getGaussianSigmaField().isValid()).isTrue();
		_testFrame.textBox(SwingMenu.FEATURE_SIGMA_FIELD_NAME).enterText("-1");
		assertThat(_frame.getGaussianSigmaField().isValid()).isTrue();
		_testFrame.textBox(SwingMenu.FEATURE_SIGMA_FIELD_NAME).enterText("1.0");
		assertThat(_frame.getGaussianSigmaField().isValid()).isTrue();
	}
}
