package com.hascode.confluence.stats;

import java.lang.management.ManagementFactory;

import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atlassian.confluence.user.UserAccessor;
import com.hascode.confluence.stats.jmx.BasicConfluenceStats;
import com.hascode.confluence.stats.jmx.BasicConfluenceStatsMBean;

public class SetupBeanImpl implements SetupBean {
	private static final Logger LOG = LoggerFactory.getLogger(SetupBeanImpl.class);

	private final UserAccessor userAccessor;
	private MBeanServer mbs;
	private ObjectName objectName;

	public SetupBeanImpl(UserAccessor userAccessor) throws MalformedObjectNameException {
		this.userAccessor = userAccessor;
		mbs = ManagementFactory.getPlatformMBeanServer();
		objectName = new ObjectName("com.hascode.confluence.stats.jmx:type=BasicConfluenceStats");
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		LOG.info("setting up JMX statistics MBeans..");
		BasicConfluenceStatsMBean mBean = new BasicConfluenceStats(userAccessor);

		mbs.registerMBean(mBean, objectName);
	}

	@Override
	public void destroy() throws Exception {
		LOG.info("shutting down JMX statistics MBeans..");
		if (mbs != null) {
			mbs.unregisterMBean(objectName);
		}
	}

}
