package net.platform.services.ecosystem.adapter.outbound.jpa;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JpaUser {
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "username", length = 50, nullable = false)
    private String username;

    @Column(name = "fullname", length = 100, nullable = false)
    private String fullname;

    @Column(name = "status", nullable = false)
    private Short status = 1;

    @Column(name = "created_at", insertable = false, nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", insertable = false, nullable = false, updatable = false)
    private LocalDateTime updatedAt;
}
