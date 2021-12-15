package io.storydoc.server.storydoc.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class StoryDoc {

    String id;

    String name;

    BlockRef[] blocks;
}
