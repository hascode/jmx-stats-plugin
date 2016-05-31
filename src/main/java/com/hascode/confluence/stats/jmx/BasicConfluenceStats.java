package com.hascode.confluence.stats.jmx;

import com.atlassian.confluence.user.UserAccessor;

public class BasicConfluenceStats implements BasicConfluenceStatsMBean {
	private final UserAccessor userAccessor;

	public BasicConfluenceStats(UserAccessor userAccessor) {
		this.userAccessor = userAccessor;
	}

	@Override
	public int getLicenseConsumingUsersCount() {
		return userAccessor.countLicenseConsumingUsers();
	}

}
