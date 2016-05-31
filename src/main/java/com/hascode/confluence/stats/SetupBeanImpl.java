package com.hascode.confluence.stats;

import java.lang.management.ManagementFactory;
import java.util.List;

import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;

import com.atlassian.confluence.event.events.plugin.PluginInstallEvent;
import com.atlassian.event.api.EventListener;
import com.atlassian.event.api.EventPublisher;
import com.atlassian.plugin.PluginAccessor;
import com.hascode.confluence.stats.plugin.StatsPlugin;
import com.hascode.confluence.stats.plugin.StatsPluginModuleDescriptor;

public class SetupBeanImpl implements SetupBean {
	private final EventPublisher eventPublisher;
	private final PluginAccessor pluginAccessor;

	public SetupBeanImpl(EventPublisher eventPublisher, PluginAccessor pluginAccessor) {
		this.eventPublisher = eventPublisher;
		this.pluginAccessor = pluginAccessor;
		eventPublisher.register(this);
	}

	@EventListener
	public void on(PluginInstallEvent evt) {
		System.err.println("XXXXXXXXXXXXXXXXXXX Plugin event caught: " + evt.getClass());
		try {
			registerMBeans();
		} catch (MalformedObjectNameException | InstanceAlreadyExistsException | MBeanRegistrationException
				| NotCompliantMBeanException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		System.err.println("XXXXXXXXXXXXXXXXXXXXXXXXXX SETUP");
	}

	private void registerMBeans() throws MalformedObjectNameException, InstanceAlreadyExistsException,
			MBeanRegistrationException, NotCompliantMBeanException {
		// Get the MBean server
		MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
		List<StatsPluginModuleDescriptor> descriptors = pluginAccessor
				.getEnabledModuleDescriptorsByClass(StatsPluginModuleDescriptor.class);
		System.err.println("XXXXXXXXXXXXXXXXXXXXXXXXX " + descriptors.size() + " descriptors found");
		for (StatsPluginModuleDescriptor descriptor : descriptors) {
			StatsPlugin module = descriptor.getModule();
			ObjectName name = createObjectName(module);
			mbs.registerMBean(module, name);
		}
	}

	private ObjectName createObjectName(StatsPlugin module) throws MalformedObjectNameException {
		ObjectName name = new ObjectName(
				String.format("%s:type=%s", module.getClass().getPackage().getName(), module.title()));
		return name;
	}

	@Override
	public void destroy() throws Exception {
		System.err.println("XXXXXXXXXXXXXXXXXXXXXXXXXX SHUTDOWN");
		// MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
		// List<StatsPluginModuleDescriptor> descriptors = pluginAccessor
		// .getEnabledModuleDescriptorsByClass(StatsPluginModuleDescriptor.class);
		// System.err.println("XXXXXXXXXXXXXXXXXXXXXXXXX " + descriptors.size()
		// + " descriptors found");
		// for (StatsPluginModuleDescriptor descriptor : descriptors) {
		// StatsPlugin module = descriptor.getModule();
		// ObjectName name = createObjectName(module);
		// mbs.unregisterMBean(name);
		// }
	}

}
