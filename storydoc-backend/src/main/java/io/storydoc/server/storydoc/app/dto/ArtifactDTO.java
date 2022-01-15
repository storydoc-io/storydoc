package io.storydoc.server.storydoc.app.dto;

import io.storydoc.server.storydoc.domain.ArtifactId;
import io.storydoc.server.workspace.domain.ResourceUrn;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArtifactDTO {

    private ArtifactId artifactId;

    private String name;

    private String artifactType;

    private ResourceUrn urn;

}

