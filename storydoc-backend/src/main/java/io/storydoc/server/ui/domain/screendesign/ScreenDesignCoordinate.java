package io.storydoc.server.ui.domain.screendesign;

import io.storydoc.server.storydoc.domain.ArtifactCoordinate;
import io.storydoc.server.storydoc.domain.BlockCoordinate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScreenDesignCoordinate {
    private BlockCoordinate blockCoordinate;
    private ScreenDesignId screenDesignId;
    public static ScreenDesignCoordinate of(BlockCoordinate blockCoordinate, ScreenDesignId screenDesignId) {
        return new ScreenDesignCoordinate(blockCoordinate, screenDesignId);
    }

    public ArtifactCoordinate asArtifactCoordinate() {
        return ArtifactCoordinate.of(blockCoordinate, screenDesignId.asArtifactId());
    }
}
