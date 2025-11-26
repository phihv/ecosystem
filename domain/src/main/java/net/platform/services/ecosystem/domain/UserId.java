package net.platform.services.ecosystem.domain;

import net.platform.services.ecosystem.common.ddd.Identity;
import lombok.Value;

@Value
public class UserId extends Identity<Long> {
    Long value;

    private UserId(Long value) {
        this.value = value;
    }

    public static UserId from(Long value) {
        return new UserId(value);
    }

    @Override
    public Long value() {
        return null;
    }
}
