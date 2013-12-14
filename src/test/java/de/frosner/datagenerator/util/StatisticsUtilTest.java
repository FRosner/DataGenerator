package de.frosner.datagenerator.util;

import static org.fest.assertions.Assertions.assertThat;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

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

	@Test
	public void testCalculateCumulativeProbabilities_shouldSumToOne() {
		List<Double> probabilities = Lists.newArrayList(0.3, 0.5, 0.1999999);
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

	@Test
	public void testSumWithOverflow_double() {
		assertThat(StatisticsUtil.sum(Lists.newArrayList(Double.MAX_VALUE, 1d))).isEqualTo(Double.MAX_VALUE);
	}

	@Test
	public void testSumWithOverflow_float() {
		assertThat(StatisticsUtil.sum(Lists.newArrayList(Float.MAX_VALUE, 1f))).isEqualTo(Float.MAX_VALUE);
	}

	@Test
	public void testSumWithOverflow_integer() {
		assertThat(StatisticsUtil.sum(Lists.newArrayList(Integer.MAX_VALUE, 1))).isEqualTo(Integer.MIN_VALUE);
	}

	@Test
	public void testSumWithOverflow_long() {
		assertThat(StatisticsUtil.sum(Lists.newArrayList(Long.MAX_VALUE, new Long(1)))).isEqualTo(Long.MIN_VALUE);
	}

	@Test(expected = UnsupportedNumberTypeException.class)
	public void testSumWithUnsupportedNumberType_atomicInteger() {
		StatisticsUtil.sum(Lists.newArrayList(new AtomicInteger()));
	}

	@Test(expected = UnsupportedNumberTypeException.class)
	public void testSumWithUnsupportedNumberType_atomicLong() {
		StatisticsUtil.sum(Lists.newArrayList(new AtomicLong()));
	}

	@Test(expected = UnsupportedNumberTypeException.class)
	public void testSumWithUnsupportedNumberType_bigDecimal() {
		StatisticsUtil.sum(Lists.newArrayList(new BigDecimal(0)));
	}

	@Test(expected = UnsupportedNumberTypeException.class)
	public void testSumWithUnsupportedNumberType_bigInteger() {
		StatisticsUtil.sum(Lists.newArrayList(new BigInteger("0")));
	}

	@Test(expected = UnsupportedNumberTypeException.class)
	public void testSumWithUnsupportedNumberType_byte() {
		StatisticsUtil.sum(Lists.newArrayList(new Byte("0")));
	}

	@Test(expected = UnsupportedNumberTypeException.class)
	public void testSumWithUnsupportedNumberType_short() {
		StatisticsUtil.sum(Lists.newArrayList(new Short("0")));
	}

}
