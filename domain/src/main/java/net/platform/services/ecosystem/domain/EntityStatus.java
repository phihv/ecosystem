package net.platform.services.ecosystem.domain;

import lombok.Getter;

public enum EntityStatus {
    ACTIVE(1, "Hoạt động"),
    INACTIVE(0, "Không hoạt động"),
    PENDING(2, "Đang chờ");

    @Getter
    private final Integer code;
    private final String description;

    EntityStatus(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

    public boolean isActive() {
        return this == ACTIVE;
    }

    public static EntityStatus fromCode(Integer code) {
        for (EntityStatus status : values()) {
            if (status.code.equals(code)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid status code: " + code);
    }
}

