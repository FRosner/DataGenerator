package de.frosner.datagenerator.util;

import static org.fest.assertions.Assertions.assertThat;

import java.util.Collection;
import java.util.List;

import net.sf.qualitycheck.exception.IllegalNullArgumentException;

import org.junit.Test;

import com.google.common.collect.Lists;

import de.frosner.datagenerator.exceptions.IllegalProbabilityArgumentException;
import de.frosner.datagenerator.util.StatisticsUtil.UnsupportedNumberTypeException;

public class StatisticsUtilTest {

	@Test
	public void testCalculateCumulativeProbabilities() {
		List<Double> probabilities = Lists.newArrayList(0.3, 0.5, 0.2);
		assertThat(StatisticsUtil.cumulateProbabilities(probabilities)).containsExactly(0.3, 0.8, 1.0);
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void testCalculateCumulativeProbabilities_nullArgument() {
		StatisticsUtil.cumulateProbabilities(null);
	}

	@Test(expected = IllegalProbabilityArgumentException.class)
	public void testCalculateCumulativeProbabilities_probabilitiesNotSummingToOne() {
		List<Double> probabilities = Lists.newArrayList(0.3, 0.3, 0.3);
		StatisticsUtil.cumulateProbabilities(probabilities);
	}

	@Test
	public void testCompareDoubles() {
		assertThat(StatisticsUtil.compareDoubles(0.1, 0.1)).isEqualTo(0);
		assertThat(StatisticsUtil.compareDoubles(0.1, 0.2)).isEqualTo(-1);
		assertThat(StatisticsUtil.compareDoubles(0.1, 0.0)).isEqualTo(1);

		assertThat(StatisticsUtil.compareDoubles(0.1, 0.101)).isEqualTo(-1);
		assertThat(StatisticsUtil.compareDoubles(0.1, 0.099)).isEqualTo(1);
		assertThat(StatisticsUtil.compareDoubles(0.1, 0.10001)).isEqualTo(0);
		assertThat(StatisticsUtil.compareDoubles(0.1, 0.09999)).isEqualTo(0);
	}

	@Test
	public void testSum_double() {
		Collection<Double> collection = Lists.newArrayList(1d);
		assertThat(StatisticsUtil.sum(collection)).isEqualTo(1d);
		collection.add(2d);
		assertThat(StatisticsUtil.sum(collection)).isEqualTo(3d);
	}

	@Test
	public void testSum_float() {
		Collection<Float> collection = Lists.newArrayList(1f);
		assertThat(StatisticsUtil.sum(collection)).isEqualTo(1f);
		collection.add(2f);
		assertThat(StatisticsUtil.sum(collection)).isEqualTo(3f);
	}

	@Test
	public void testSum_integer() {
		Collection<Integer> collection = Lists.newArrayList(1);
		assertThat(StatisticsUtil.sum(collection)).isEqualTo(1);
		collection.add(2);
		assertThat(StatisticsUtil.sum(collection)).isEqualTo(3);
	}

	@Test
	public void testSum_long() {
		Collection<Long> collection = Lists.newArrayList(new Long(1));
		assertThat(StatisticsUtil.sum(collection)).isEqualTo(new Long(1));
		collection.add(new Long(2));
		assertThat(StatisticsUtil.sum(collection)).isEqualTo(new Long(3));
	}


	@Test(expected = UnsupportedNumberTypeException.class)
	public void testSumWithUnsupportedNumberType() {
		Collection<Byte> collection = Lists.newArrayList(new Byte("0"));
		StatisticsUtil.sum(collection);
	}

}
