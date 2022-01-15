package io.storydoc.server.storydoc.domain;

import java.util.Objects;

public class ArtifactId {

    private String id;

    public ArtifactId(String id) {
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
        ArtifactId blockId = (ArtifactId) o;
        return Objects.equals(id, blockId.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public static ArtifactId fromString(String id) {
        return new ArtifactId(id);
    }
}
