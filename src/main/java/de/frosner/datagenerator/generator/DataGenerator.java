package de.frosner.datagenerator.generator;

import java.util.List;

import javax.annotation.Nonnull;

import net.sf.qualitycheck.Check;
import net.sf.qualitycheck.exception.IllegalEmptyArgumentException;
import de.frosner.datagenerator.distributions.VariableParameter;
import de.frosner.datagenerator.export.ExportConnection;
import de.frosner.datagenerator.features.FeatureDefinition;
import de.frosner.datagenerator.features.FeatureValue;
import de.frosner.datagenerator.generator.Instance.InstanceBuilder;

/**
 * Class for sampling a sequence of {@linkplain Instance}s having the specified {@linkplain FeatureDefinition}s. Sampled
 * instances will be exported to the specified {@linkplain ExportConnection}.
 */
public final class DataGenerator {

	private final int _numberOfInstances;
	private final ExportConnection _out;
	private final FeatureDefinitionGraph _featureDefinitionGraph;
	private boolean _metaDataExported = false;

	/**
	 * Constructs a new {@linkplain DataGenerator}. When {@linkplain DataGenerator#generate()} is invoked it will sample
	 * the specified number of instances with the specified feature definitions to the specified export connection.
	 * 
	 * @param numberOfInstances
	 *            to be generated
	 * @param exportConnection
	 *            to export the generated instances to
	 * @param featureDefinitionGraph
	 *            that contain the information for sampling the feature values of the instances
	 */
	public DataGenerator(int numberOfInstances, @Nonnull ExportConnection exportConnection,
			@Nonnull FeatureDefinitionGraph featureDefinitionGraph) {
		Check.stateIsTrue(numberOfInstances > 0, "Number of instances to generate must be > 0.");
		Check.notNull(exportConnection);
		if (featureDefinitionGraph.isEmpty()) {
			throw new IllegalEmptyArgumentException("featureDefinitionGraph");
		}

		_numberOfInstances = numberOfInstances;
		_out = exportConnection;
		_featureDefinitionGraph = featureDefinitionGraph;
	}

	/**
	 * Sample and export all instances to the registered {@linkplain ExportConnection}.
	 */
	public void generate() {
		generate(0, _numberOfInstances);
	}

	/**
	 * Sample and export a batch of instances instances to the registered {@linkplain ExportConnection}. Sampling starts
	 * with the instance ID of the specified offset and generates until IDs reach (offset + range).
	 * <p>
	 * This method should be used if data generation should be done in batches or in parallel. Otherwise use
	 * {@linkplain DataGenerator#generate()} to generate all instances in one batch.
	 * 
	 * @param offset
	 *            of the instance ID to start generation
	 * @param range
	 *            of the instance IDs from the specified offset
	 */
	public void generate(int offset, int range) {
		if (!_metaDataExported) {
			_metaDataExported = true;
			_out.exportMetaData(_featureDefinitionGraph);
		}

		for (int i = offset; i < Math.min(offset + range, _numberOfInstances); i++) {
			InstanceBuilder instanceBuilder = Instance.builder(i);
			for (FeatureDefinition featureDefinition : _featureDefinitionGraph) {
				FeatureValue sample = featureDefinition.getDistribution().sample();
				updateDependentParameters(featureDefinition, sample);
				instanceBuilder.addFeatureValue(sample);
			}
			_out.exportInstance(instanceBuilder.build());
		}
	}

	private void updateDependentParameters(FeatureDefinition featureDefinition, FeatureValue value) {
		List<VariableParameter<?>> dependentParameters = _featureDefinitionGraph
				.getDependentParameters(featureDefinition);
		for (VariableParameter<?> parameter : dependentParameters) {
			parameter.updateParameter(value);
		}
	}

}
