package io.storydoc.server.storydoc.domain.action;

import io.storydoc.server.storydoc.domain.ArtifactBlockCoordinate;
import io.storydoc.server.storydoc.domain.ArtifactId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CreateBinaryCollectionArtifactAction {

    private ArtifactBlockCoordinate coordinate;
    private ArtifactId artifactId;
    private String artifactName;
    private String artifactType;
    private String binaryType;

}
