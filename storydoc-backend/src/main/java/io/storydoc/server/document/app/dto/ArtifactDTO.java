package io.storydoc.server.document.app.dto;

import io.storydoc.server.document.domain.ArtifactId;
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

}

