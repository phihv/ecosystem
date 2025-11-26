package net.platform.services.ecosystem.adapter.outbound.http.feign;

import net.platform.services.ecosystem.adapter.outbound.dto.RandomUserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "randomUserClient", url = "https://randomuser.me")
public interface RandomUserClient {
    @GetMapping("/api/")
    RandomUserResponse getRandomUser();
}
