package io.storydoc.server.ui.domain;

import io.storydoc.server.storydoc.domain.ArtifactId;

import java.util.Objects;

public class UIScenarioId {

    public static final String ID_PREFIX = "ui-scenario";
    private String id;

    public UIScenarioId(String id) {
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
        UIScenarioId UIScenarioId = (UIScenarioId) o;
        return Objects.equals(id, UIScenarioId.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public static UIScenarioId fromString(String id) {
        return new UIScenarioId(id);
    }

    public ArtifactId asArtifactId() {
        return new ArtifactId(id);
    }
}
