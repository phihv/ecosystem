package net.platform.services.ecosystem.adapter.outbound.adapter;

import lombok.RequiredArgsConstructor;
import net.platform.services.ecosystem.adapter.outbound.dto.RandomUserResponse;
import net.platform.services.ecosystem.adapter.outbound.http.feign.RandomUserClient;
import net.platform.services.ecosystem.domain.UserInfo;
import net.platform.services.ecosystem.domain.UserRandomOutboundPort;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserRandomAdapter implements UserRandomOutboundPort {
    private final RandomUserClient randomUserClient;
    @Override
    public UserInfo getRandom() {
        RandomUserResponse randomUserResponse = randomUserClient.getRandomUser();
        RandomUserResponse.Result result = randomUserResponse.getResults().getFirst();
        return UserInfo.create(result.getLogin().getUsername(), result.getLogin().getPassword(), result.getName().getFirst(), result.getName().getLast());
    }
}
