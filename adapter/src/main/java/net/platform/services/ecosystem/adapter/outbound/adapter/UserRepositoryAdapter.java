package net.platform.services.ecosystem.adapter.outbound.adapter;

import net.platform.services.ecosystem.common.ddd.Criteria;
import net.platform.services.ecosystem.common.ddd.Identity;
import net.platform.services.ecosystem.common.ddd.PagedSearchResult;
import net.platform.services.ecosystem.common.ddd.Pagination;
import lombok.RequiredArgsConstructor;
import net.platform.services.ecosystem.adapter.outbound.jpa.JpaUserRepository;
import net.platform.services.ecosystem.adapter.outbound.mapper.UserMapper;
import net.platform.services.ecosystem.domain.User;
import net.platform.services.ecosystem.domain.UserRepositoryOutboundPort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserRepositoryAdapter implements UserRepositoryOutboundPort {
    private final JpaUserRepository jpaUserRepository;
    private final UserMapper userMapper;

    @Override
    public void save(User var1) {
        jpaUserRepository.save(userMapper.toJpa(var1));
    }

    @Override
    public <U extends Identity<?>> Optional<User> findById(U var1) {
        return Optional.empty();
    }

    @Override
    public <U extends Identity<?>> List<User> findAllByIds(List<U> var1) {
        return List.of();
    }

    @Override
    public List<User> findBy(Criteria var1) {
        return List.of();
    }

    @Override
    public PagedSearchResult<User> findBy(Criteria var1, Pagination var2) {
        return null;
    }

    @Override
    public <U extends Identity<?>> void delete(U var1) {

    }
}
