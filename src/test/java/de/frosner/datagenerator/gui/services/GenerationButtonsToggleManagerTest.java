package de.frosner.datagenerator.gui.services;

import static org.fest.assertions.Assertions.assertThat;

import java.awt.AWTException;
import java.util.ArrayList;

import javax.swing.AbstractButton;
import javax.swing.JButton;

import net.sf.qualitycheck.exception.IllegalEmptyArgumentException;
import net.sf.qualitycheck.exception.IllegalNullArgumentException;
import net.sf.qualitycheck.exception.IllegalNullElementsException;
import net.sf.qualitycheck.exception.IllegalStateOfArgumentException;

import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.google.common.collect.Lists;

import de.frosner.datagenerator.testutils.GuiTestUtil;
import de.frosner.datagenerator.testutils.SwingTests;

@Category(SwingTests.class)
public class GenerationButtonsToggleManagerTest {

	private GuiTestUtil _testUtil;

	@BeforeClass
	public static void setUpOnce() {
		FailOnThreadViolationRepaintManager.install();
	}

	@Before
	public void initGUI() throws AWTException {
		_testUtil = new GuiTestUtil();
	}

	@After
	public void unsetPreviewTable() {
		GenerationButtonsToggleManager.stopManaging();
	}

	@Test
	public void testToggle_sameNumberOfButtonsPerGroup() {
		JButton button1 = GuiTestUtil.createNewJButton("Button1");
		GuiTestUtil.enableButton(button1, true);
		JButton button2 = GuiTestUtil.createNewJButton("Button2");
		GuiTestUtil.enableButton(button2, false);
		GenerationButtonsToggleManager.manageButtons(Lists.newArrayList((AbstractButton) button1), Lists
				.newArrayList((AbstractButton) button2));

		GenerationButtonsToggleManager.toggle();
		_testUtil.delay();
		assertThat(button1.isEnabled()).isFalse();
		assertThat(button2.isEnabled()).isTrue();

		GenerationButtonsToggleManager.toggle();
		_testUtil.delay();
		assertThat(button1.isEnabled()).isTrue();
		assertThat(button2.isEnabled()).isFalse();
	}

	@Test
	public void testToggle_differingNumberOfButtonsPerGroup() {
		JButton button1_1 = GuiTestUtil.createNewJButton("Button1");
		GuiTestUtil.enableButton(button1_1, true);
		JButton button2_1 = GuiTestUtil.createNewJButton("Button2_1");
		GuiTestUtil.enableButton(button2_1, false);
		JButton button2_2 = GuiTestUtil.createNewJButton("Button2_2");
		GuiTestUtil.enableButton(button2_2, false);
		GenerationButtonsToggleManager.manageButtons(Lists.newArrayList((AbstractButton) button1_1), Lists
				.newArrayList((AbstractButton) button2_1, (AbstractButton) button2_2));

		GenerationButtonsToggleManager.toggle();
		_testUtil.delay();
		assertThat(button1_1.isEnabled()).isFalse();
		assertThat(button2_1.isEnabled()).isTrue();
		assertThat(button2_2.isEnabled()).isTrue();

		GenerationButtonsToggleManager.toggle();
		_testUtil.delay();
		assertThat(button1_1.isEnabled()).isTrue();
		assertThat(button2_1.isEnabled()).isFalse();
		assertThat(button2_2.isEnabled()).isFalse();
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void testManageButtonsThrowsException_nullArgument() {
		GenerationButtonsToggleManager.manageButtons(null, null);
	}

	@Test(expected = IllegalNullElementsException.class)
	public void testManageButtonsThrowsException_nullElements() {
		GenerationButtonsToggleManager.manageButtons(Lists.newArrayList((AbstractButton) null), Lists
				.newArrayList((AbstractButton) null));
	}

	@Test(expected = IllegalEmptyArgumentException.class)
	public void testManageButtonsThrowsException_zeroElements() {
		GenerationButtonsToggleManager.manageButtons(new ArrayList<AbstractButton>(), new ArrayList<AbstractButton>());
	}

	@Test(expected = IllegalStateOfArgumentException.class)
	public void testManageButtonsThrowsException_notAllButtonsOfFirstGroupEnabled() {
		JButton button1_1 = GuiTestUtil.createNewJButton("Button1_1");
		GuiTestUtil.enableButton(button1_1, true);
		JButton button1_2 = GuiTestUtil.createNewJButton("Button1_2");
		GuiTestUtil.enableButton(button1_2, false);
		JButton button2 = GuiTestUtil.createNewJButton("Button2");
		GuiTestUtil.enableButton(button2, false);
		GenerationButtonsToggleManager.manageButtons(Lists.newArrayList((AbstractButton) button1_1,
				(AbstractButton) button1_2), Lists.newArrayList((AbstractButton) button2));
	}

	@Test(expected = IllegalStateOfArgumentException.class)
	public void testManageButtonsThrowsException_notAllButtonsOfSecondGroupDisabled() {
		JButton button1 = GuiTestUtil.createNewJButton("Button1");
		GuiTestUtil.enableButton(button1, true);
		JButton button2_1 = GuiTestUtil.createNewJButton("Button2_1");
		GuiTestUtil.enableButton(button2_1, true);
		JButton button2_2 = GuiTestUtil.createNewJButton("Button2_2");
		GuiTestUtil.enableButton(button2_2, false);
		GenerationButtonsToggleManager.manageButtons(Lists.newArrayList((AbstractButton) button1), Lists.newArrayList(
				(AbstractButton) button2_1, (AbstractButton) button2_2));
	}

}
