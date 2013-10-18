package de.frosner.datagenerator.exceptions;

import de.frosner.datagenerator.util.ApplicationMetaData;

/**
 * {@linkplain RuntimeException} indicating that the given arguments are not satisfying the conditions of a probability
 * distribution.
 */
public class IllegalProbabilityArgumentException extends RuntimeException {

	private static final long serialVersionUID = ApplicationMetaData.SERIAL_VERSION_UID;

	public IllegalProbabilityArgumentException() {
		super("Supplied argument(s) do(es) not satisfy the conditions of a probability distribution.");
	}

}
