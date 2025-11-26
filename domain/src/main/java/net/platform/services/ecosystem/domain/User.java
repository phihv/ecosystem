package net.platform.services.ecosystem.domain;

import net.platform.services.ecosystem.common.ddd.Aggregate;
import net.platform.services.ecosystem.common.ddd.SnowflakeIdGenerator;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Getter
public class User extends Aggregate<UserId> {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private EntityStatus status;
    private Instant createdAt;
    private Instant updatedAt;
    private Long createdBy;
    private Long updatedBy;

    @Builder(toBuilder = true, access = AccessLevel.PRIVATE)
    private User(
            UserId id,
            String username,
            String password,
            String firstName,
            String lastName,
            EntityStatus status,
            Instant createdAt,
            Instant updatedAt,
            Long createdBy,
            Long updatedBy
    ) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.status = status == null ? EntityStatus.ACTIVE : status;
        this.createdAt = createdAt == null ? Instant.now() : createdAt;
        this.updatedAt = updatedAt == null ? Instant.now() : updatedAt;
        this.createdBy = createdBy == null ? -1L : createdBy;
        this.updatedBy = updatedBy == null ? -1L : updatedBy;
    }

    public static User create(
            String username,
            String password,
            String firstName,
            String lastName,
            Long createdBy
    ) {
        Long id = SnowflakeIdGenerator.createInstance().createValue();
        return User.builder()
                .id(UserId.from(id))
                .username(username)
                .password(password)
                .firstName(firstName)
                .lastName(lastName)
                .createdBy(createdBy)
                .build();
    }

    public static User reconstruct(
            UserId id,
            String username,
            String password,
            String firstName,
            String lastName,
            EntityStatus status,
            Instant createdAt,
            Instant updatedAt
    ) {
        return User.builder()
                .id(id)
                .username(username)
                .password(password)
                .firstName(firstName)
                .lastName(lastName)
                .status(status)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
    }
}
