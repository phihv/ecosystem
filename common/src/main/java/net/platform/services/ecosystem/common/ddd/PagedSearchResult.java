package net.platform.services.ecosystem.common.ddd;


import net.platform.services.ecosystem.common.exception.InvalidArgumentException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class PagedSearchResult<T extends Aggregate<?>> {
    private final List<T> content;
    private final int page;
    private final int size;
    private final Optional<Integer> nextPage;

    public PagedSearchResult(List<T> content, int page, int size, Optional<Integer> nextPage) {
        Objects.requireNonNull(content, "Content cannot be null");
        if (page < 0) {
            throw new InvalidArgumentException("Page must be non-negative");
        } else if (size <= 0) {
            throw new InvalidArgumentException("Size must be positive");
        } else {
            this.content = content;
            this.page = page;
            this.size = size;
            this.nextPage = nextPage;
        }
    }

    public List<T> content() {
        return this.content;
    }

    public int page() {
        return this.page;
    }

    public int size() {
        return this.size;
    }

    public Optional<Integer> nextPage() {
        return this.nextPage;
    }
}
