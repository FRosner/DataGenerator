package de.frosner.datagenerator.generator;

import java.util.List;

import com.google.common.collect.Lists;

import de.frosner.datagenerator.export.ExportConnection;

public class DummyExportConnection extends ExportConnection {

	private FeatureDefinitionGraph _metaData;
	private List<Instance> _instances = Lists.newArrayList();

	@Override
	protected void exportMetaDataStrategy(FeatureDefinitionGraph featureDefinitions) {
		_metaData = featureDefinitions;
	}

	@Override
	protected void exportInstanceStrategy(Instance instance) {
		_instances.add(instance);
	}

	@Override
	public void close() {
	}

	@Override
	public String getExportLocation() {
		return DummyExportConnection.class.getSimpleName();
	}

	public FeatureDefinitionGraph getMetaData() {
		return _metaData;
	}

	public List<Instance> getInstances() {
		return _instances;
	}

}
