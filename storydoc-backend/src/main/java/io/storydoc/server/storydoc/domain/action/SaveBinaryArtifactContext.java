package io.storydoc.server.storydoc.domain.action;

import io.storydoc.server.storydoc.domain.ArtifactBlockCoordinate;
import io.storydoc.server.storydoc.domain.ArtifactId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.io.InputStream;


@Getter
@Builder
@AllArgsConstructor
public class SaveBinaryArtifactContext {

    private ArtifactBlockCoordinate coordinate;
    private ArtifactId artifactId;
    private InputStream inputStream;

}
