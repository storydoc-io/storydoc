package io.storydoc.server.code.domain;

import io.storydoc.server.storydoc.domain.ArtifactId;

import java.util.Objects;

public class StitchConfigId {

    public static final String ID_PREFIX = "stitch-config";
    private String id;

    private StitchConfigId(){}

    public StitchConfigId(String id) {
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
        StitchConfigId stitchConfigId = (StitchConfigId) o;
        return Objects.equals(id, stitchConfigId.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public static StitchConfigId fromString(String id) {
        return new StitchConfigId(id);
    }


    public ArtifactId asArtifactId() {
        return new ArtifactId(id);
    }
}
