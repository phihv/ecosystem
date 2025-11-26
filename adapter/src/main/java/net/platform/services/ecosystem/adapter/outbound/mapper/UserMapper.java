package net.platform.services.ecosystem.adapter.outbound.mapper;

import net.platform.services.ecosystem.adapter.outbound.jpa.JpaUser;
import net.platform.services.ecosystem.domain.EntityStatus;
import net.platform.services.ecosystem.domain.User;
import net.platform.services.ecosystem.domain.UserId;
import org.springframework.stereotype.Component;
import net.platform.services.ecosystem.common.utils.TimeUtils;

@Component
public class UserMapper {
    public User toDomain(JpaUser jpa) {
        return User.reconstruct(
                UserId.from(jpa.getId()),
                jpa.getUsername(),
                jpa.getPassword(),
                jpa.getFirstName(),
                jpa.getLastName(),
                EntityStatus.fromCode(jpa.getStatus()),
                TimeUtils.localDateTimeToInstant(jpa.getUpdatedAt()),
                TimeUtils.localDateTimeToInstant(jpa.getCreatedAt())
        );
    }

    public JpaUser toJpa(User domain) {
        return JpaUser.builder()
                .id(domain.id().getValue())
                .username(domain.getUsername())
                .password(domain.getPassword())
                .firstName(domain.getFirstName())
                .lastName(domain.getLastName())
                .status(domain.getStatus().getCode())
                .createdAt(TimeUtils.instantToLocalDateTime(domain.getCreatedAt()))
                .updatedAt(TimeUtils.instantToLocalDateTime(domain.getUpdatedAt()))
                .build();
    }
}
