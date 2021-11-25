package com.dennislck.user.command.api.security;

public interface PasswordEncoder {
	String hashPassword(String password);
}
