package io.storydoc.server.ui.domain.action;

import io.storydoc.server.storydoc.domain.BlockCoordinate;
import io.storydoc.server.ui.domain.ScreenShotCollectionId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateScreenShotCollectionArtifactAction {
    private BlockCoordinate coordinate;
    private ScreenShotCollectionId collectionId;
    private String name;
}
