package com.dennislck.user.query.api.handlers;

import com.dennislck.user.core.events.UserRegisteredEvent;
import com.dennislck.user.core.events.UserRemovedEvent;
import com.dennislck.user.core.events.UserUpdatedEvent;

public interface UserEventHandler {
	void on(UserRegisteredEvent event);

	void on(UserUpdatedEvent event);

	void on(UserRemovedEvent event);
}
