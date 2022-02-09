package io.storydoc.server.storydoc.domain.action;

import io.storydoc.server.storydoc.domain.BlockCoordinate;
import io.storydoc.server.storydoc.domain.ArtifactId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CreateBinaryCollectionArtifactAction {

    private BlockCoordinate coordinate;
    private ArtifactId artifactId;
    private String artifactName;
    private String artifactType;
    private String binaryType;

}
