package io.storydoc.server.infra.canon;

import io.storydoc.canon.Canon;
import io.storydoc.server.infra.canon.steps.AddBlockToStoryDoc;
import io.storydoc.server.infra.canon.steps.CreateStoryDoc;

public class StoryDocCanonBuilder {

    Canon canon = new Canon();

    public StoryDocCanonBuilder createStoryDoc(String name) {
        canon.add(CreateStoryDoc.builder()
            .storyDocVariableName(name)
            .build());
        return this;
    }

    public StoryDocCanonBuilder createBlock(String storyDocName, String blockName) {
        canon.add(AddBlockToStoryDoc.builder()
                .storyDocVariableName(storyDocName)
                .blockVariableName(blockName)
                .build());
        return this;
    }

    static public StoryDocCanonBuilder canonBuilder() {
        return new StoryDocCanonBuilder();
    }

    public Canon build() {
        return canon;
    }
}
