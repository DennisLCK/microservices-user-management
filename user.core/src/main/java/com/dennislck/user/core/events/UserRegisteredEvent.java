package com.dennislck.user.core.events;

import com.dennislck.user.core.models.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRegisteredEvent {
	private String id;
	private User user;
}
