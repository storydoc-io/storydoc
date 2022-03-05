package io.storydoc.server.storydoc.infra.store.model;

import io.storydoc.server.storydoc.domain.ArtifactState;
import io.storydoc.server.workspace.domain.ResourceUrn;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Artifact {

    private String artifactId;

    private String name;

    private ArtifactState state;

    private String artifactType;

    private ResourceUrn urn;

    private boolean binary;

    private boolean collection;

    private String binaryType;

    private List<ArtifactItem> items;

}
