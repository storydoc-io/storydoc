package io.storydoc.server.ui.app;

import java.util.Objects;
import java.util.UUID;

public class UIBlockId {

    private UUID id;

    public UIBlockId(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UIBlockId blockId = (UIBlockId) o;
        return Objects.equals(id, blockId.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public static UIBlockId fromString(String id) {
        return new UIBlockId(UUID.fromString(id));
    }
}
