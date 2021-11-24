package com.dennislck.user.core.models;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
	READ_PRIVILEGES, WRITE_PRIVILEGES;

	@Override
	public String getAuthority() {
		return name();
	}
}
