package io.storydoc.server.ui.domain.screendesign;

import io.storydoc.server.storydoc.domain.ArtifactId;

import java.util.Objects;

public class ScreenDesignId {

    public static final String ID_PREFIX = "screen-design";
    private String id;

    private ScreenDesignId(){}

    public ScreenDesignId(String id) {
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
        ScreenDesignId screenDesignId
                = (ScreenDesignId) o;
        return Objects.equals(id, screenDesignId.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public static ScreenDesignId fromString(String id) {
        return new ScreenDesignId(id);
    }


    public ArtifactId asArtifactId() {
        return new ArtifactId(id);
    }
}
