package de.frosner.datagenerator.util;

import org.apache.commons.lang.exception.NestableRuntimeException;

public final class ExceptionUtil {

	private ExceptionUtil() {
	}

	public static RuntimeException uncheckException(Exception e) {
		throw new NestableRuntimeException(e);
	}

	public static RuntimeException uncheckException(Exception e, String message) {
		throw new NestableRuntimeException(message, e);
	}

}
