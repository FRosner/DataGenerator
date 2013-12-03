package de.frosner.datagenerator.util;

import static org.fest.assertions.Assertions.assertThat;

import java.util.List;

import net.sf.qualitycheck.exception.IllegalNullArgumentException;

import org.junit.Test;

import com.google.common.collect.Lists;

import de.frosner.datagenerator.exceptions.IllegalProbabilityArgumentException;

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

}
