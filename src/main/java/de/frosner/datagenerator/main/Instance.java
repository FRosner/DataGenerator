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

	private final int _id;
	private final List<FeatureValue> _values;

	public Instance(int id, @Nonnull FeatureValue... values) {
		Check.noNullElements(values, "All feature values must not be null.");
		_values = ImmutableList.copyOf(values);
		_id = id;
	}

	public FeatureValue getFeatureValue(int index) {
		return _values.get(index);
	}

	public int getId() {
		return _id;
	}

	@Override
	public Iterator<FeatureValue> iterator() {
		return _values.listIterator();
	}

	@Override
	public String toString() {
		return _values.toString();
	}

	@Override
	public int hashCode() {
		return _id;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Instance) {
			Instance i = (Instance) o;
			return (i._id == _id) && (i._values.equals(_values));
		} else {
			return false;
		}
	}

}
