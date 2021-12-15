package io.storydoc.server.storydoc.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class TextBlock {

    String id;
    String name;
    String text;

}
