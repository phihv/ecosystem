package net.platform.services.ecosystem.adapter.configuration.persistence;

import net.platform.services.ecosystem.adapter.outbound.mapper.UserMapper;
import net.platform.services.ecosystem.application.inbound.UserInboundPort;
import net.platform.services.ecosystem.application.usecase.UserUseCase;
import net.platform.services.ecosystem.domain.UserRandomOutboundPort;
import net.platform.services.ecosystem.domain.UserRepositoryOutboundPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InboundAdapterPersistenceConfig {
    @Bean
    public UserInboundPort userInboundPort(
            UserRepositoryOutboundPort userRepositoryOutboundPort,
            UserRandomOutboundPort userRandomOutboundPort
    ) {
        return new UserUseCase(
                userRepositoryOutboundPort,
                userRandomOutboundPort);
    }
}
