package de.frosner.datagenerator.generator;

import java.util.List;

import javax.annotation.Nonnull;

import net.sf.qualitycheck.Check;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import de.frosner.datagenerator.export.ExportConnection;
import de.frosner.datagenerator.features.FeatureDefinition;
import de.frosner.datagenerator.generator.Instance.InstanceBuilder;

/**
 * Class for sampling a sequence of {@link Instance}s having the specified {@link FeatureDefinition}s. Sampled instances
 * will be exported to the specified {@link ExportConnection}.
 */
public final class DataGenerator {

	private final int _numberOfInstances;
	private final ExportConnection _out;
	private final List<FeatureDefinition> _featureDefinitions;
	private boolean _metaDataExported = false;

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
		Check.notEmpty(featureDefinitions, "There must at least be one feature to generate data from.");

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

	public void generate() {
		generate(0, _numberOfInstances);
	}

	public void generate(int offset, int range) {
		if (!_metaDataExported) {
			_metaDataExported = true;
			_out.exportMetaData(_featureDefinitions);
		}

		for (int i = offset; i < Math.min(offset + range, _numberOfInstances); i++) {
			InstanceBuilder instanceBuilder = Instance.builder(i);
			for (FeatureDefinition featureDefinition : _featureDefinitions) {
				instanceBuilder.addFeatureValue(featureDefinition.getDistribution().sample());
			}
			_out.exportInstance(instanceBuilder.build());
		}
	}

}
