package com.hascode.confluence.stats;

import com.atlassian.confluence.user.UserAccessor;
import com.hascode.confluence.stats.plugin.StatsPlugin;

public class UserStatsPlugin implements StatsPlugin {
	private final UserAccessor userAccessor;

	public UserStatsPlugin(UserAccessor userAccessor) {
		this.userAccessor = userAccessor;
	}

	@Override
	public String title() {
		return "userstats";
	}

	@Override
	public String key() {
		return "userLicenseCount";
	}

	@Override
	public Object value() {
		return userAccessor.countLicenseConsumingUsers();
	}

}
