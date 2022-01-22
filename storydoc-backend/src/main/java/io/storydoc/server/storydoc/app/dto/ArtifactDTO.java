package io.storydoc.server.storydoc.app.dto;

import io.storydoc.server.storydoc.domain.ArtifactId;
import io.storydoc.server.workspace.domain.ResourceUrn;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArtifactDTO {

    private ArtifactId artifactId;

    private String name;

    private String artifactType;

    private ResourceUrn urn;

    private boolean binary;

    private boolean collection;

    private String binaryType;

    private List<ItemDTO> items;

}

