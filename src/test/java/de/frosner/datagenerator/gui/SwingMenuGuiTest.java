package de.frosner.datagenerator.gui;

import static org.fest.assertions.Assertions.assertThat;

import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.edt.GuiTask;
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
		_testFrame.target.toFront();
	}

	@After
	public void tearDown() {
		_testFrame.cleanUp();
	}

	@Test
	@Ignore("#58: Validators not implemented, yet")
	public void testValidateFeatureName() {
		_testFrame.textBox(SwingMenu.TestUtils.FEATURE_NAME_FIELD_NAME).enterText("");
		assertThat(_frame.testUtils().getGaussianNameField().isValid()).isFalse();
		_testFrame.textBox(SwingMenu.TestUtils.FEATURE_NAME_FIELD_NAME).enterText("Feature 1");
		assertThat(_frame.testUtils().getGaussianNameField().isValid()).isTrue();
	}

	@Test
	@Ignore("#58: Validators not implemented, yet")
	public void testValidateFeatureMean() {
		_testFrame.textBox(SwingMenu.TestUtils.FEATURE_MEAN_FIELD_NAME).enterText("");
		assertThat(_frame.testUtils().getGaussianMeanField().isValid()).isFalse();
		_testFrame.textBox(SwingMenu.TestUtils.FEATURE_MEAN_FIELD_NAME).enterText("Test");
		assertThat(_frame.testUtils().getGaussianMeanField().isValid()).isFalse();
		_testFrame.textBox(SwingMenu.TestUtils.FEATURE_MEAN_FIELD_NAME).enterText("1");
		assertThat(_frame.testUtils().getGaussianMeanField().isValid()).isTrue();
		_testFrame.textBox(SwingMenu.TestUtils.FEATURE_MEAN_FIELD_NAME).enterText("-1");
		assertThat(_frame.testUtils().getGaussianMeanField().isValid()).isTrue();
		_testFrame.textBox(SwingMenu.TestUtils.FEATURE_MEAN_FIELD_NAME).enterText("1.0");
		assertThat(_frame.testUtils().getGaussianMeanField().isValid()).isTrue();
	}

	@Test
	@Ignore("#58: Validators not implemented, yet")
	public void testValidateFeatureSigma() {
		_testFrame.textBox(SwingMenu.TestUtils.FEATURE_SIGMA_FIELD_NAME).enterText("");
		assertThat(_frame.testUtils().getGaussianSigmaField().isValid()).isFalse();
		_testFrame.textBox(SwingMenu.TestUtils.FEATURE_SIGMA_FIELD_NAME).enterText("Test");
		assertThat(_frame.testUtils().getGaussianSigmaField().isValid()).isFalse();
		_testFrame.textBox(SwingMenu.TestUtils.FEATURE_SIGMA_FIELD_NAME).enterText("1");
		assertThat(_frame.testUtils().getGaussianSigmaField().isValid()).isTrue();
		_testFrame.textBox(SwingMenu.TestUtils.FEATURE_SIGMA_FIELD_NAME).enterText("-1");
		assertThat(_frame.testUtils().getGaussianSigmaField().isValid()).isTrue();
		_testFrame.textBox(SwingMenu.TestUtils.FEATURE_SIGMA_FIELD_NAME).enterText("1.0");
		assertThat(_frame.testUtils().getGaussianSigmaField().isValid()).isTrue();
	}

	@Test
	public void testAddFeature() throws InterruptedException {
		assertThat(_frame.testUtils().getFeatureDefinitionListModel().size()).isEqualTo(0);
		_testFrame.textBox(SwingMenu.TestUtils.FEATURE_NAME_FIELD_NAME).enterText("Feature 1");
		_testFrame.textBox(SwingMenu.TestUtils.FEATURE_MEAN_FIELD_NAME).enterText("0");
		_testFrame.textBox(SwingMenu.TestUtils.FEATURE_SIGMA_FIELD_NAME).enterText("1.0");
		GuiActionRunner.execute(new GuiTask() {
			@Override
			protected void executeInEDT() {
				_frame.testUtils().clickAddFeatureButton();
			}
		});
		assertThat(_frame.testUtils().getFeatureDefinitionListModel().get(0)).isEqualTo("Feature 1");
	}
}
