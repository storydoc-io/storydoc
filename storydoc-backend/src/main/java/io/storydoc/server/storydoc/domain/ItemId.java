package io.storydoc.server.storydoc.domain;

import java.util.Objects;

public class ItemId {

    private String id;

    public ItemId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemId itemId = (ItemId) o;
        return Objects.equals(id, itemId.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public static ItemId fromString(String id) {
        return new ItemId(id);
    }
}
