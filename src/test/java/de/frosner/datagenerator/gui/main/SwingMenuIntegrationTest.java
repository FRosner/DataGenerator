package de.frosner.datagenerator.gui.main;

import java.awt.AWTException;
import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.experimental.categories.Category;

import de.frosner.datagenerator.gui.services.DataGeneratorService;
import de.frosner.datagenerator.testutils.LongRunningSwingTests;

@Category(LongRunningSwingTests.class)
public abstract class SwingMenuIntegrationTest {

	protected SwingMenu _frame;
	protected SwingMenuTestUtil _frameTestUtil;
	protected File _testFile = new File("src/test/resources/" + this.getClass().getSimpleName() + ".tmp");

	@Before
	public void setUp() throws AWTException {
		DataGeneratorService.INSTANCE.reset();
		_frame = new SwingMenu();
		_frameTestUtil = new SwingMenuTestUtil(_frame);
		SwingLauncher.GUI = _frame;
		_frameTestUtil.setExportFileFilter(SwingMenu.ALL_FILE_FILTER);
		if (_testFile.exists()) {
			_testFile.delete();
		}
	}

	@After
	public void destroyGUI() {
		_frame.dispose();
		SwingMenuTestUtil.resetComponentManagers();
	}

}
