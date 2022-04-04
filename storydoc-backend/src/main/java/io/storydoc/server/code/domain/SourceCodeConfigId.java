package io.storydoc.server.code.domain;

import io.storydoc.server.storydoc.domain.ArtifactId;

import java.util.Objects;

public class SourceCodeConfigId {

    public static final String ID_PREFIX = "source-code-config";
    private String id;

    private SourceCodeConfigId(){}

    public SourceCodeConfigId(String id) {
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
        SourceCodeConfigId sourceCodeConfigId = (SourceCodeConfigId) o;
        return Objects.equals(id, sourceCodeConfigId.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public static SourceCodeConfigId fromString(String id) {
        return new SourceCodeConfigId(id);
    }


    public ArtifactId asArtifactId() {
        return new ArtifactId(id);
    }
}
