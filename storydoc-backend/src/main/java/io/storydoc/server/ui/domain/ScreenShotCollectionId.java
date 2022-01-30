package io.storydoc.server.ui.domain;

import io.storydoc.server.storydoc.domain.ArtifactId;

import java.util.Objects;

public class ScreenShotCollectionId {

    private String id;

    private ScreenShotCollectionId() {}

    public ScreenShotCollectionId(String id) {
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
        ScreenShotCollectionId screenshotsId = (ScreenShotCollectionId) o;
        return Objects.equals(id, screenshotsId.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public static ScreenShotCollectionId fromString(String id) {
        return new ScreenShotCollectionId(id);
    }

    public ArtifactId asArtifactId() {
        return new ArtifactId(id);
    }
}
