package io.storydoc.server.ui.domain.screendesign;

import java.util.Objects;

public class SDComponentId {

    private String id;

    private SDComponentId(){}

    public SDComponentId(String id) {
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
        SDComponentId screenDesignId
                = (SDComponentId) o;
        return Objects.equals(id, screenDesignId.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public static SDComponentId fromString(String id) {
        return new SDComponentId(id);
    }

}
