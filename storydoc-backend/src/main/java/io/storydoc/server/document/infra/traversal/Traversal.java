package io.storydoc.server.document.infra.traversal;

import io.storydoc.server.document.infra.store.model.Block;
import io.storydoc.server.document.infra.store.model.StoryDoc;

abstract public class Traversal {

    protected Block current;

    protected final StoryDoc storyDoc;

    public Traversal(StoryDoc storyDoc) {
        this.storyDoc = storyDoc;
    }

    abstract public Block next();

}

