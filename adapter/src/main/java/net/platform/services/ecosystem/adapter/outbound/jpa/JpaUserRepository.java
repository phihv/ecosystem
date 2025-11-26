package net.platform.services.ecosystem.adapter.outbound.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface JpaUserRepository extends JpaRepository<JpaUser, Long>, JpaSpecificationExecutor<JpaUser> {
}
