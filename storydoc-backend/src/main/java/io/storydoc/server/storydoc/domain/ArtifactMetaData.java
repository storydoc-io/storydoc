package io.storydoc.server.storydoc.domain;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ArtifactMetaData {
    private String type;
    private String name;
}
