package io.storydoc.server.code.domain;

import io.storydoc.server.storydoc.domain.ArtifactId;

import java.util.Objects;

public class CodeExecutionId {

    public static final String ID_PREFIX = "code";
    private String id;

    private CodeExecutionId(){}

    public CodeExecutionId(String id) {
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
        CodeExecutionId codeExecutionId = (CodeExecutionId) o;
        return Objects.equals(id, codeExecutionId.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public static CodeExecutionId fromString(String id) {
        return new CodeExecutionId(id);
    }


    public ArtifactId asArtifactId() {
        return new ArtifactId(id);
    }
}
