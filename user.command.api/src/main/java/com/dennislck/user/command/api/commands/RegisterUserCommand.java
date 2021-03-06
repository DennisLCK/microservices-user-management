package com.dennislck.user.command.api.commands;

import com.dennislck.user.core.models.User;
import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@Builder
public class RegisterUserCommand {
	@TargetAggregateIdentifier
	private String id;
	private User user;
}
