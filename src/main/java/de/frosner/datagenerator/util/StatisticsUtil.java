package de.frosner.datagenerator.util;

import java.util.Collection;
import java.util.List;

import javax.annotation.Nonnull;

import net.sf.qualitycheck.Check;

import org.apache.commons.math3.util.Precision;

import com.google.common.collect.Lists;

import de.frosner.datagenerator.exceptions.IllegalProbabilityArgumentException;

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

	public static List<Double> cumulateProbabilities(@Nonnull List<Double> probabilities) {
		Check.notNull(probabilities, "probabilities");
		Check.stateIsTrue(compareDoubles(StatisticsUtil.sum(probabilities), 1.0D) == 0,
				IllegalProbabilityArgumentException.class);

		List<Double> cumulativeProbabilities = Lists.newArrayList();
		for (double probability : probabilities) {
			if (cumulativeProbabilities.isEmpty()) {
				cumulativeProbabilities.add(probability);
			} else {
				cumulativeProbabilities.add(cumulativeProbabilities.get(cumulativeProbabilities.size() - 1)
						+ probability);
			}
		}
		return cumulativeProbabilities;
	}

	public static int compareDoubles(double a, double b) {
		return Precision.compareTo(a, b, 0.000001);
	}

}
