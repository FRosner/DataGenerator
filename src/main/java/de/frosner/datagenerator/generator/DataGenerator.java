package de.frosner.datagenerator.generator;

import java.io.IOException;
import java.util.List;

import javax.annotation.Nonnull;

import net.sf.qualitycheck.Check;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import de.frosner.datagenerator.export.ExportConnection;
import de.frosner.datagenerator.features.FeatureDefinition;
import de.frosner.datagenerator.generator.Instance.InstanceBuilder;
import de.frosner.datagenerator.gui.main.ProgressBarManager;

/**
 * Class for sampling a sequence of {@link Instance}s having the specified {@link FeatureDefinition}s. Sampled instances
 * will be exported to the specified {@link ExportConnection}.
 */
public final class DataGenerator {

	private final int _numberOfInstances;
	private final ExportConnection _out;
	private final List<FeatureDefinition> _featureDefinitions;

	public static final class DataGeneratorBuilder {

		private final int _numberOfInstances;
		private final ExportConnection _exportConnection;
		private final List<FeatureDefinition> _featureDefinitions;

		public DataGeneratorBuilder(int numberOfInstances, ExportConnection exportConnection) {
			_numberOfInstances = numberOfInstances;
			_exportConnection = exportConnection;
			_featureDefinitions = Lists.newArrayList();
		}

		public DataGeneratorBuilder addFeatureDefinition(FeatureDefinition featureDefinition) {
			_featureDefinitions.add(featureDefinition);
			return this;
		}

		public DataGenerator build() {
			return new DataGenerator(_numberOfInstances, _exportConnection, _featureDefinitions);
		}

	}

	public static DataGeneratorBuilder builder(int numberOfInstances, ExportConnection out) {
		return new DataGeneratorBuilder(numberOfInstances, out);
	}

	/**
	 * Constructs a new {@link DataGenerator} object. When {@link DataGenerator#generate()} is invoked it will sample
	 * the specified number of instances with the specified feature definitions to the specified export connection.
	 * 
	 * @param numberOfInstances
	 *            to be generated
	 * @param exportConnection
	 *            to export the generated instances to
	 * @param featureDefinitions
	 *            that contain the information for sampling the feature values of the instances
	 */
	public DataGenerator(int numberOfInstances, @Nonnull ExportConnection exportConnection,
			@Nonnull List<FeatureDefinition> featureDefinitions) {
		Check.stateIsTrue(numberOfInstances > 0, "Number of instances to generate must be > 0.");
		Check.notNull(exportConnection);
		Check.noNullElements(featureDefinitions);
		Check.stateIsTrue(featureDefinitions.size() > 0, "There must at least be one feature to generate data from.");

		_numberOfInstances = numberOfInstances;
		_out = exportConnection;
		_featureDefinitions = ImmutableList.copyOf(featureDefinitions);
	}

	/**
	 * Constructs a new {@link DataGenerator} object. When {@link DataGenerator#generate()} is invoked it will sample
	 * the specified number of instances with the specified feature definitions to the specified export connection.
	 * 
	 * @param numberOfInstances
	 *            to be generated
	 * @param exportConnection
	 *            to export the generated instances to
	 * @param featureDefinitions
	 *            that contain the information for sampling the feature values of the instances
	 */
	public DataGenerator(int numberOfInstances, @Nonnull ExportConnection exportConnection,
			@Nonnull FeatureDefinition... featureDefinitions) {
		this(numberOfInstances, exportConnection, Lists.newArrayList(featureDefinitions));
	}

	/**
	 * Sample and export instances to the corresponding {@link ExportConnection}.
	 * 
	 * @throws IOException
	 */
	public boolean generate() throws IOException {
		boolean success = true;

		ProgressBarManager.resetProgress();
		int progressNotificationBreak = _numberOfInstances / 100;
		if (progressNotificationBreak == 0) {
			ProgressBarManager.setProgressToMaximum();
		}

		for (int i = 0; i < _numberOfInstances; i++) {
			if (Thread.interrupted()) {
				success = false;
				break;
			}

			if (progressNotificationBreak > 0 && i % progressNotificationBreak == 0) {
				ProgressBarManager.increaseProgress();
			}

			InstanceBuilder instanceBuilder = Instance.builder(i);
			for (FeatureDefinition featureDefinition : _featureDefinitions) {
				instanceBuilder.addFeatureValue(featureDefinition.getDistribution().sample());
			}
			_out.export(instanceBuilder.build());
		}
		_out.close();
		return success;
	}
}
