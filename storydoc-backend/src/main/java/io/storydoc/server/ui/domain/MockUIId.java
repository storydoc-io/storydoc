package io.storydoc.server.ui.domain;

import io.storydoc.server.storydoc.domain.ArtifactId;

import java.util.Objects;

public class MockUIId {

    private String id;

    public MockUIId(String id) {
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
        MockUIId mockUIId = (MockUIId) o;
        return Objects.equals(id, mockUIId.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public static MockUIId fromString(String id) {
        return new MockUIId(id);
    }

    public ArtifactId asArtifactId() {
        return new ArtifactId(id);
    }
}
