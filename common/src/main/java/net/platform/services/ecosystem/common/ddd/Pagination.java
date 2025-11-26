package net.platform.services.ecosystem.common.ddd;

import net.platform.services.ecosystem.common.exception.InvalidArgumentException;

public record Pagination(int page, int size) {
    public Pagination(int page, int size) {
        if (page < 0) {
            throw new InvalidArgumentException("Page must be non-negative");
        } else if (size > 0 && size <= 100) {
            this.page = page;
            this.size = size;
        } else {
            throw new InvalidArgumentException("Size must be between 1 and 100");
        }
    }

    public int offset() {
        return this.page * this.size;
    }

    public static Pagination of(int page, int size) {
        return new Pagination(page, size);
    }
}
