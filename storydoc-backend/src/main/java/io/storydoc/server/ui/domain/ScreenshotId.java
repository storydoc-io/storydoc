package io.storydoc.server.ui.domain;

import io.storydoc.server.storydoc.domain.ArtifactId;

import java.util.Objects;

public class ScreenshotId {

    private String id;

    public ScreenshotId(String id) {
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
        ScreenshotId screenshotId = (ScreenshotId) o;
        return Objects.equals(id, screenshotId.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public static ScreenshotId fromString(String id) {
        return new ScreenshotId(id);
    }

    public ArtifactId asArtifactId() {
        return new ArtifactId(id);
    }
}
