package io.storydoc.server.workspace.app.dto;

import io.storydoc.server.workspace.domain.ResourceUrn;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResourceDTO {
    private String name;
    private ResourceUrn urn;
}
