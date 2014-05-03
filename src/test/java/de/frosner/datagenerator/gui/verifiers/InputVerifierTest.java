package de.frosner.datagenerator.gui.verifiers;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import javax.swing.JComponent;
import javax.swing.JTextField;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mock;

import de.frosner.datagenerator.testutils.SwingTests;

@Category(SwingTests.class)
public class InputVerifierTest {

	private JComponent _component;

	@Mock
	private InputVerifier _verifier;

	@Before
	public void initTextField() {
		_component = new JTextField();
	}

	@Before
	public void initMockedVerifier() {
		initMocks(this);
	}

	@Test
	public void testVerifyComponent_boolean() {
		assertThat(_component.getBackground()).isEqualTo(InputVerifier.VALID_INPUT_WHITE);
		InputVerifier.verifyComponent(_component, false);
		assertThat(_component.getBackground()).isEqualTo(InputVerifier.INVALID_INPUT_RED);
		InputVerifier.verifyComponent(_component, true);
		assertThat(_component.getBackground()).isEqualTo(InputVerifier.VALID_INPUT_WHITE);
	}

	@Test
	public void testVerifyComponent_verifier() {
		assertThat(_component.getBackground()).isEqualTo(InputVerifier.VALID_INPUT_WHITE);
		when(_verifier.verify()).thenReturn(false);
		InputVerifier.verifyComponent(_component, _verifier);
		assertThat(_component.getBackground()).isEqualTo(InputVerifier.INVALID_INPUT_RED);
		when(_verifier.verify()).thenReturn(true);
		InputVerifier.verifyComponent(_component, _verifier);
		assertThat(_component.getBackground()).isEqualTo(InputVerifier.VALID_INPUT_WHITE);
	}

}
