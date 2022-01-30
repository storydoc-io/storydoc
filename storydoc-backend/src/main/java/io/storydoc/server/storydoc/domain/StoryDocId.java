package io.storydoc.server.storydoc.domain;

import java.util.Objects;

public class StoryDocId {

    private String id;

    private StoryDocId() {};

    public StoryDocId(String id) {
        this.id = id;
    }

    public static StoryDocId fromString(String storyDocId) {
        return new StoryDocId(storyDocId);
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StoryDocId that = (StoryDocId) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void setId(String id) {
        this.id = id;
    }
}
