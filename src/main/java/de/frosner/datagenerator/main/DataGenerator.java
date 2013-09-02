package de.frosner.datagenerator.main;

import java.io.IOException;
import java.util.List;

import javax.annotation.Nonnull;

import net.sf.qualitycheck.Check;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import de.frosner.datagenerator.export.ExportConnection;
import de.frosner.datagenerator.features.FeatureDefinition;
import de.frosner.datagenerator.features.FeatureValue;

/**
 * Class for sampling a sequence of {@link Instance}s having the specified {@link FeatureDefinition}s. Sampled instances
 * will be exported to the specified {@link ExportConnection}.
 */
public class DataGenerator {

	private final int _numberOfInstances;
	private final ExportConnection _out;
	private final List<FeatureDefinition> _featureDefinitions;

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
		Check.stateIsTrue(numberOfInstances > 0, "Number of instances to generate must be > 0.");
		Check.notNull(exportConnection);
		Check.noNullElements(featureDefinitions);

		_numberOfInstances = numberOfInstances;
		_out = exportConnection;
		_featureDefinitions = ImmutableList.copyOf(featureDefinitions);
	}

	/**
	 * Sample and export instances to the corresponding {@link ExportConnection}.
	 */
	public void generate() {
		try {
			for (int i = 0; i < _numberOfInstances; i++) {
				List<FeatureValue> featureValues = Lists.newArrayList();
				for (FeatureDefinition featureDefinition : _featureDefinitions) {
					featureValues.add(featureDefinition.getDistribution().sample());
				}
				_out.export(new Instance(i, featureValues.toArray(new FeatureValue[0])));
			}
			_out.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
