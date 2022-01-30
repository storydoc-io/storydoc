package io.storydoc.server.ui.app;

import io.storydoc.server.ui.domain.ScreenshotCollectionCoordinate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScreenshotCollectionSummaryDTO {
    private String name;
    private ScreenshotCollectionCoordinate collectionCoordinate;
}
