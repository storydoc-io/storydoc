package io.storydoc.server.timeline.domain;

import io.storydoc.server.storydoc.domain.ArtifactId;

import java.util.Objects;

public class TimeLineId {

    private String id;

    private TimeLineId() {}

    public TimeLineId(String id) {
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
        TimeLineId timeLineModelId = (TimeLineId) o;
        return Objects.equals(id, timeLineModelId.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public static TimeLineId fromString(String id) {
        return new TimeLineId(id);
    }


    public ArtifactId asArtifactId() {
        return new ArtifactId(id);
    }
}
