package net.platform.services.ecosystem.adapter.inbound.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
public class CreateUserRequest {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private Long createdBy;
}
