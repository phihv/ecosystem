package net.platform.services.ecosystem.application.inbound;

import net.platform.services.ecosystem.application.dto.CreateUserCommand;

public interface UserInboundPort {
    void create(CreateUserCommand command);
    void createRandomUser();
}
