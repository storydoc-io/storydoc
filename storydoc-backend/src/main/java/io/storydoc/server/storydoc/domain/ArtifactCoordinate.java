package io.storydoc.server.storydoc.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArtifactCoordinate {
    private ArtifactId artifactId;
    private BlockCoordinate blockCoordinate;

    static public ArtifactCoordinate of(BlockCoordinate blockCoordinate, ArtifactId artifactId) {
        return new ArtifactCoordinate(artifactId, blockCoordinate);
    }
}
