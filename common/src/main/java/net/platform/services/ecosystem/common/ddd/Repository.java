package net.platform.services.ecosystem.common.ddd;


import java.util.List;
import java.util.Optional;

public interface Repository<T extends Aggregate<?>> {
    void save(T var1);

    <U extends Identity<?>> Optional<T> findById(U var1);

    <U extends Identity<?>> List<T> findAllByIds(List<U> var1);

    List<T> findBy(Criteria var1);

    PagedSearchResult<T> findBy(Criteria var1, Pagination var2);

    <U extends Identity<?>> void delete(U var1);
}

