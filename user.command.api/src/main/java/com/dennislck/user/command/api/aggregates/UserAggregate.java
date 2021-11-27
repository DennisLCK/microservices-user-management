package com.dennislck.user.command.api.aggregates;

import com.dennislck.user.command.api.commands.RegisterUserCommand;
import com.dennislck.user.command.api.commands.RemoveUserCommand;
import com.dennislck.user.command.api.commands.UpdateUserCommand;
import com.dennislck.user.command.api.security.PasswordEncoder;
import com.dennislck.user.command.api.security.PasswordEncoderImpl;
import com.dennislck.user.core.events.UserRegisteredEvent;
import com.dennislck.user.core.events.UserRemovedEvent;
import com.dennislck.user.core.events.UserUpdatedEvent;
import com.dennislck.user.core.models.User;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.UUID;

@Aggregate
public class UserAggregate {
	@AggregateIdentifier
	private String id;
	private User user;

	private final PasswordEncoder passwordEncoder;

	public UserAggregate() {
		passwordEncoder = new PasswordEncoderImpl();
	}

	@CommandHandler
	public UserAggregate(RegisterUserCommand command) {
		User newUser = command.getUser();
		newUser.setId(command.getId());
		String password = newUser.getAccount().getPassword();
		passwordEncoder = new PasswordEncoderImpl();
		newUser.getAccount().setPassword(passwordEncoder.hashPassword(password));

		UserRegisteredEvent event = UserRegisteredEvent.builder()
				.id(command.getId())
				.user(newUser)
				.build();

		AggregateLifecycle.apply(event);
	}

	@CommandHandler
	public void handle(UpdateUserCommand command) {
		User updateUser = command.getUser();
		updateUser.setId(command.getId());
		String password = updateUser.getAccount().getPassword();
		updateUser.getAccount().setPassword(passwordEncoder.hashPassword(password));

		UserUpdatedEvent event = UserUpdatedEvent.builder()
				.id(UUID.randomUUID().toString())
				.user(updateUser)
				.build();

		AggregateLifecycle.apply(event);
	}

	@CommandHandler
	public void handle(RemoveUserCommand command) {
		UserRemovedEvent event = new UserRemovedEvent();
		event.setId(command.getId());

		AggregateLifecycle.apply(event);
	}

	@EventSourcingHandler
	private void on(UserRegisteredEvent event) {
		this.id = event.getId();
		this.user = event.getUser();
	}

	@EventSourcingHandler
	private void on(UserUpdatedEvent event) {
		this.user = event.getUser();
	}

	@EventSourcingHandler
	private void on(UserRemovedEvent event) {
		AggregateLifecycle.markDeleted();
	}
}
