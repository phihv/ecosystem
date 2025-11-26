package net.platform.services.ecosystem.application.usecase;

import lombok.RequiredArgsConstructor;
import net.platform.services.ecosystem.application.dto.CreateUserCommand;
import net.platform.services.ecosystem.application.inbound.UserInboundPort;
import net.platform.services.ecosystem.domain.User;
import net.platform.services.ecosystem.domain.UserInfo;
import net.platform.services.ecosystem.domain.UserRandomOutboundPort;
import net.platform.services.ecosystem.domain.UserRepositoryOutboundPort;

@RequiredArgsConstructor
public class UserUseCase implements UserInboundPort {
    private final UserRepositoryOutboundPort userRepositoryOutboundPort;
    private final UserRandomOutboundPort userRandomOutboundPort;
    @Override
    public void create(CreateUserCommand command) {
        User user = User.create(
                command.getUsername(),
                command.getPassword(),
                command.getFirstName(),
                command.getLastName(),
                command.getCreatedBy()
        );
        userRepositoryOutboundPort.save(user);
    }

    @Override
    public void createRandomUser() {
        UserInfo userInfo = userRandomOutboundPort.getRandom();
        User user = User.create(
                userInfo.getUsername(),
                userInfo.getPassword(),
                userInfo.getFirstName(),
                userInfo.getLastName(),
                null
        );
        userRepositoryOutboundPort.save(user);
    }
}
