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

public class DataGenerator {

	private final int _numberOfInstances;
	private final ExportConnection _out;
	private final List<FeatureDefinition> _featureDefinitions;

	public DataGenerator(int numberOfInstances, @Nonnull ExportConnection out,
			@Nonnull FeatureDefinition... featureDefinitions) {
		Check.stateIsTrue(numberOfInstances > 0, "Number of instances to generate must be > 0.");
		Check.notNull(out);
		Check.noNullElements(featureDefinitions);

		_numberOfInstances = numberOfInstances;
		_out = out;
		_featureDefinitions = ImmutableList.copyOf(featureDefinitions);
	}

	public void generate() {
		for (int i = 0; i < _numberOfInstances; i++) {
			List<FeatureValue> featureValues = Lists.newArrayList();
			for (FeatureDefinition featureDefinition : _featureDefinitions) {
				featureValues.add(featureDefinition.getDistribution().sample());
			}
			_out.export(new Instance(i, featureValues.toArray(new FeatureValue[0])));
		}
		try {
			_out.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
