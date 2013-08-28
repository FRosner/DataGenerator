package de.frosner.datagenerator.main;

import java.util.Iterator;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

import net.sf.qualitycheck.Check;

import com.google.common.collect.ImmutableList;

import de.frosner.datagenerator.features.FeatureValue;

@Immutable
public class Instance implements Iterable<FeatureValue> {

	private final List<FeatureValue> _values;

	public Instance(@Nonnull FeatureValue... values) {
		Check.noNullElements(values, "All feature values must not be null.");
		_values = ImmutableList.copyOf(values);
	}

	public FeatureValue getFeatureValue(int index) {
		return _values.get(index);
	}

	@Override
	public Iterator<FeatureValue> iterator() {
		return _values.listIterator();
	}

	@Override
	public String toString() {
		return _values.toString();
	}

}
