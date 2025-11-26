package net.platform.services.ecosystem.adapter.inbound.facade;

import lombok.RequiredArgsConstructor;
import net.platform.services.ecosystem.application.inbound.UserInboundPort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserFacade {
    private final UserInboundPort userInboundPort;

    public void createRandomUser() {
        userInboundPort.createRandomUser();
    }
}
