package io.storydoc.server.storydoc.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class BlockRef {

    String id;

    String type;


}
