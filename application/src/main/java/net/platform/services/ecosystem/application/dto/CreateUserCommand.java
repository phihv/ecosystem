package net.platform.services.ecosystem.application.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CreateUserCommand {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private Long createdBy;
}
