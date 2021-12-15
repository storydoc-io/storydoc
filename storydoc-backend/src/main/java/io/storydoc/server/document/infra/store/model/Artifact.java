package io.storydoc.server.document.infra.store.model;

import io.storydoc.server.document.domain.ArtifactId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Artifact {

    private String artifactId;

    private String name;

    private String artifactType;

    private String urn;
}
