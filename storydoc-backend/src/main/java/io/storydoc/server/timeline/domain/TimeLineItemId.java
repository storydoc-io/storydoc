package io.storydoc.server.timeline.domain;

import java.util.Objects;

public class TimeLineItemId {

    private String id;

    public TimeLineItemId(String id) {
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
        TimeLineItemId timeLineItemId = (TimeLineItemId) o;
        return Objects.equals(id, timeLineItemId.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public static TimeLineItemId fromString(String id) {
        return new TimeLineItemId(id);
    }

}
