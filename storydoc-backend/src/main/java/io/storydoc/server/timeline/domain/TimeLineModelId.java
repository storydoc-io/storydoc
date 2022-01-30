package io.storydoc.server.timeline.domain;

import io.storydoc.server.storydoc.domain.ArtifactId;

import java.util.Objects;

public class TimeLineModelId {

    private String id;

    private TimeLineModelId() {}

    public TimeLineModelId(String id) {
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
        TimeLineModelId timeLineModelId = (TimeLineModelId) o;
        return Objects.equals(id, timeLineModelId.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public static TimeLineModelId fromString(String id) {
        return new TimeLineModelId(id);
    }


    public ArtifactId asArtifactId() {
        return new ArtifactId(id);
    }
}
