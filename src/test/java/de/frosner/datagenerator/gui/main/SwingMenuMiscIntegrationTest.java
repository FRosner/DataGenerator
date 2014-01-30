package de.frosner.datagenerator.gui.main;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.Fail.fail;
import static org.fest.swing.edt.GuiActionRunner.execute;

import java.awt.AWTException;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.swing.ImageIcon;

import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.edt.GuiTask;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import de.frosner.datagenerator.gui.services.DataGeneratorService;
import de.frosner.datagenerator.gui.services.TextAreaLogManager;
import de.frosner.datagenerator.testutils.LongRunningSwingTests;

@Category(LongRunningSwingTests.class)
public class SwingMenuMiscIntegrationTest {

	private SwingMenu _frame;
	private SwingMenuTestUtil _frameTestUtil;
	private File _testFile = new File("src/test/resources/" + this.getClass().getSimpleName() + ".tmp");

	@BeforeClass
	public static void setUpOnce() {
		FailOnThreadViolationRepaintManager.install();
	}

	@Before
	public void setUp() throws AWTException {
		DataGeneratorService.INSTANCE.reset();
		_frame = GuiActionRunner.execute(new GuiQuery<SwingMenu>() {
			@Override
			protected SwingMenu executeInEDT() {
				return new SwingMenu();
			}
		});
		_frameTestUtil = new SwingMenuTestUtil(_frame);
		SwingLauncher.GUI = _frame;
		_frameTestUtil.setExportFileFilter(SwingMenu.ALL_FILE_FILTER);
		if (_testFile.exists()) {
			_testFile.delete();
		}
	}

	@After
	public void destroyGUI() {
		execute(new GuiTask() {
			@Override
			public void executeInEDT() {
				_frame.dispose();
			}
		});
		SwingMenuTestUtil.resetComponentManagers();
	}

	@Test
	public void testLogging() {
		TextAreaLogManager.info("Test");
		_frameTestUtil.delay(250);
		assertThat(_frame._logArea.getText()).contains("Test");
	}

	@Ignore("See #172")
	@Test
	public void testExitMenuItem() {
	}

	@Test
	public void testWhetherExitItemIsInFileMenu() {
		assertThat(_frame._menuBar.getMenu(0).getItem(1)).isEqualTo(_frame._closeMenuItem);
	}

	@Test
	public void testThatIconImageIsSet() {
		assertThat(_frame.getIconImages().get(0)).isEqualTo(
				new ImageIcon(getClass().getClassLoader().getResource("frame_icon.png")).getImage());
	}

	@Test(timeout = 6000)
	public void testChangeToEditModeWhenEditButtonWasClicked() {
		assertThat(_frame._featureDefinitionDialog.isInEditMode()).isFalse();

		// dummy feature entry, as at least one feature is needed for editing
		_frameTestUtil.enterText(_frame._featureNameField, "FeatureToEdit");
		_frameTestUtil.enterText(_frame._uniformCategorialNumberOfStatesField, "1");
		_frameTestUtil.selectOption(_frame._addFeatureDistributionSelection, UniformCategorialFeatureEntry.KEY);
		_frameTestUtil.tryToAddEnteredFeature(_frame._addFeatureButton);
		_frameTestUtil.delay(500);
		_frameTestUtil.selectFeatureDefinitionEntryByName("FeatureToEdit");

		assertThat(GuiActionRunner.execute(new GuiQuery<Boolean>() {
			@Override
			protected Boolean executeInEDT() {
				Boolean isInEditMode = null;

				ExecutorService executor = Executors.newFixedThreadPool(1);
				Future<Boolean> future = executor.submit(new Callable<Boolean>() {
					@Override
					public Boolean call() {
						_frameTestUtil.delay(500);
						boolean isInEditMode = _frame._featureDefinitionDialog.isInEditMode();
						_frameTestUtil.pressAndReleaseKey(KeyEvent.VK_ESCAPE);
						return isInEditMode;
					}
				});

				_frame.actionPerformed(new ActionEvent(_frame._editFeatureButton, 1, ""));

				try {
					isInEditMode = future.get();
				} catch (InterruptedException e1) {
					fail(e1.getMessage());
				} catch (ExecutionException e2) {
					fail(e2.getMessage());
				}
				return isInEditMode;
			}
		})).isTrue();

		assertThat(_frame._featureDefinitionDialog.isInEditMode()).isFalse();
	}

}
