package io.storydoc.server.ui.domain;

import io.storydoc.server.storydoc.domain.ArtifactId;
import io.storydoc.server.storydoc.domain.ItemId;

import java.util.Objects;

public class ScreenShotId {

    private String id;

    public ScreenShotId(String id) {
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
        ScreenShotId screenshotId = (ScreenShotId) o;
        return Objects.equals(id, screenshotId.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public static ScreenShotId fromString(String id) {
        return new ScreenShotId(id);
    }

    @Deprecated
    public ArtifactId asArtifactId() {
        return new ArtifactId(id);
    }

    public ItemId asItemId() {
        return new ItemId(id);
    }

}
