package de.frosner.datagenerator.util;

import java.util.Collection;

import net.sf.qualitycheck.Check;

public final class StatisticsUtil {

	public static class UnsupportedNumberTypeException extends RuntimeException {

		private static final long serialVersionUID = ApplicationMetaData.SERIAL_VERSION_UID;

		public UnsupportedNumberTypeException() {
			super("Specified number is not a Double, Float, Integer or Long.");
		}

	}

	/**
	 * Returns the sum of all collection members. Supported number types are {@linkplain Double}, {@linkplain Float},
	 * {@linkplain Integer} and {@linkplain Long}.
	 * 
	 * @param collection
	 *            to sum up
	 * @return sum of the collection members
	 * @throws UnsupportedNumberTypeException
	 *             if the number type is not supported
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Number> T sum(Collection<T> collection) {
		Check.notEmpty(collection);
		Number firstNumber = collection.iterator().next();
		if (firstNumber instanceof Double) {
			double sum = 0.0D;
			for (Number number : collection) {
				sum += number.doubleValue();
			}
			return (T) new Double(sum);
		} else if (firstNumber instanceof Float) {
			float sum = 0.0F;
			for (Number number : collection) {
				sum += number.floatValue();
			}
			return (T) new Float(sum);
		} else if (firstNumber instanceof Integer) {
			int sum = 0;
			for (Number number : collection) {
				sum += number.intValue();
			}
			return (T) new Integer(sum);
		} else if (firstNumber instanceof Long) {
			long sum = 0;
			for (Number number : collection) {
				sum += number.longValue();
			}
			return (T) new Long(sum);
		} else {
			throw new UnsupportedNumberTypeException();
		}

	}

}
