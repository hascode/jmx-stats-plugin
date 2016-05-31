package com.hascode.confluence.stats.plugin;

import com.atlassian.plugin.descriptors.AbstractModuleDescriptor;
import com.atlassian.plugin.module.ModuleFactory;

public class StatsPluginModuleDescriptor extends AbstractModuleDescriptor<StatsPlugin> {

	public StatsPluginModuleDescriptor(ModuleFactory moduleFactory) {
		super(moduleFactory);
	}

	@Override
	public StatsPlugin getModule() {
		return moduleFactory.createModule(moduleClassName, this);
	}

}
