package de.frosner.datagenerator.distributions;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

import net.sf.qualitycheck.Check;
import de.frosner.datagenerator.features.ContinuousFeatureValue;

/**
 * Class representing a range or an interval by storing the lower and the upper bound.
 */
@Immutable
public class Interval {

	/**
	 * Unbounded interval with lower bound {@linkplain Double#NEGATIVE_INFINITY} and upper bound
	 * {@linkplain Double#POSITIVE_INFINITY}, respectively.
	 */
	public static Interval UNBOUNDED = new Interval(new ContinuousFeatureValue(Double.NEGATIVE_INFINITY),
			new ContinuousFeatureValue(Double.POSITIVE_INFINITY));

	private final ContinuousFeatureValue _lowerBound;
	private final ContinuousFeatureValue _upperBound;

	/**
	 * Creates a new {@linkplain Interval} defined by the specified lower and upper bound.
	 * 
	 * @param lowerBound
	 *            of the interval
	 * @param upperBound
	 *            of the interval
	 */
	public Interval(@Nonnull ContinuousFeatureValue lowerBound, @Nonnull ContinuousFeatureValue upperBound) {
		_lowerBound = Check.notNull(lowerBound, "lowerBound");
		_upperBound = Check.notNull(upperBound, "upperBound");
		Check.stateIsTrue(_lowerBound.compareTo(_upperBound) < 0, "The lower bound " + lowerBound
				+ " must be less than the upper bound " + upperBound + ".");
	}

	/**
	 * @return lower bound of the interval
	 */
	public ContinuousFeatureValue getLowerBound() {
		return _lowerBound;
	}

	/**
	 * @return upper bound of the interval
	 */
	public ContinuousFeatureValue getUpperBound() {
		return _upperBound;
	}

	@Override
	public String toString() {
		return "[" + _lowerBound + ", " + _upperBound + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + _lowerBound.hashCode();
		result = prime * result + _upperBound.hashCode();
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (obj instanceof Interval) {
			Interval other = (Interval) obj;
			return other._lowerBound.equals(_lowerBound) && other._upperBound.equals(_upperBound);
		} else {
			return false;
		}
	}

}
