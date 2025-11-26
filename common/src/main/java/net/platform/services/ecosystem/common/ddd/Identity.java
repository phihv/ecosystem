package net.platform.services.ecosystem.common.ddd;

public abstract class Identity<T> {
    public abstract T value();

    public abstract int hashCode();

    public abstract boolean equals(Object var1);
}