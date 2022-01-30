package io.storydoc.server.storydoc.domain;

import java.util.Objects;

public class BlockId {

    private String id;

    private BlockId() {}

    public BlockId(String id) {
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
        BlockId blockId = (BlockId) o;
        return Objects.equals(id, blockId.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public static BlockId fromString(String id) {
        return new BlockId(id);
    }
}
