package io.storydoc.server.document.domain;

import java.util.Objects;

public class SectionId {

    private String id;

    public SectionId(String id) {
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
        SectionId blockId = (SectionId) o;
        return Objects.equals(id, blockId.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public static SectionId fromString(String id) {
        return new SectionId(id);
    }
}
