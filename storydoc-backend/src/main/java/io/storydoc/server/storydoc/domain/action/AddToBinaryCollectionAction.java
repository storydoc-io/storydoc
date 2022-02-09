package io.storydoc.server.storydoc.domain.action;

import io.storydoc.server.storydoc.domain.BlockCoordinate;
import io.storydoc.server.storydoc.domain.ArtifactId;
import io.storydoc.server.storydoc.domain.ItemId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.io.InputStream;

@Getter
@Builder
@AllArgsConstructor
public class AddToBinaryCollectionAction {

    private BlockCoordinate coordinate;
    private ArtifactId artifactId;
    private String itemName;
    private ItemId itemId;
    private InputStream inputStream;

}
