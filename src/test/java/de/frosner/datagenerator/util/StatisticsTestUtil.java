package de.frosner.datagenerator.util;

import java.util.ArrayList;
import java.util.List;

public final class StatisticsTestUtil {

	private StatisticsTestUtil() {
		throw new UnsupportedOperationException();
	}

	public static double sampleMeanFromDoubleList(List<Double> samples) {
		double sum = 0;
		for (Double sample : samples) {
			sum += sample;
		}
		return sum / samples.size();
	}

	public static double sampleMeanFromIntegerList(List<Integer> samples) {
		List<Double> doubleSamples = new ArrayList<Double>();
		for (Integer sample : samples) {
			doubleSamples.add(sample.doubleValue());
		}
		return sampleMeanFromDoubleList(doubleSamples);
	}

	public static double sampleSigma(List<Double> samples, double sampleMean) {
		double sumOfSquares = sumOfSquares(samples);
		return Math.sqrt((sumOfSquares - samples.size() * sampleMean * sampleMean) / (samples.size() - 1));
	}

	private static double sumOfSquares(List<Double> samples) {
		double sumOfSquares = 0;
		for (double sample : samples) {
			sumOfSquares += sample * sample;
		}
		return sumOfSquares;
	}
}
