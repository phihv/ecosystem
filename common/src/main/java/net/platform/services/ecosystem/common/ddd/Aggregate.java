package net.platform.services.ecosystem.common.ddd;

public abstract class Aggregate<T extends Identity<?>> {
    protected T id;
    private Long _id;

    public T id() {
        return this.id;
    }

    public int hashCode() {
        return this.id() != null ? this.id().hashCode() : 0;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            Aggregate<?> that = (Aggregate)o;
            return this.id() != null && this.id().equals(that.id());
        } else {
            return false;
        }
    }

    public void set_id(Long _id) {
        this._id = _id;
    }

    public Long _id() {
        return this._id;
    }
}
