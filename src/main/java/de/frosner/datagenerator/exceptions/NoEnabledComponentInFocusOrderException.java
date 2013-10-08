package de.frosner.datagenerator.exceptions;

import java.awt.Component;
import java.util.List;

import de.frosner.datagenerator.gui.main.OrderedFocusTraversalPolicy;
import de.frosner.datagenerator.util.ApplicationMetaData;

/**
 * Exception indicating that an {@linkplain OrderedFocusTraversalPolicy} contains no enabled components to focus.
 */
public class NoEnabledComponentInFocusOrderException extends RuntimeException {

	private static final long serialVersionUID = ApplicationMetaData.SERIAL_VERSION_UID;

	/**
	 * Creates a new {@linkplain NoEnabledComponentInFocusOrderException}. You should pass the order that caused the
	 * exception to be thrown.
	 * 
	 * @param order
	 */
	public NoEnabledComponentInFocusOrderException(List<Component> order) {
		super("At least one component must be enabled: " + order.toString());
	}

}