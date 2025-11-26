package net.platform.services.ecosystem.adapter.inbound.schedule;

import lombok.RequiredArgsConstructor;
import net.platform.services.ecosystem.adapter.inbound.facade.UserFacade;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RandomUserJob {
    private final UserFacade facade;
    @Scheduled(fixedRate = 5000)
    public void runJob() {
        facade.createRandomUser();
    }
}
