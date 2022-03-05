package io.storydoc.server.storydoc.domain;

import io.storydoc.server.workspace.domain.ResourceUrn;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ArtifactMetaData {
    String type;
    String name;
    ArtifactState state;
    ResourceUrn relativeUrn;
    boolean collection;
    boolean binary;
    String binaryType;
}
