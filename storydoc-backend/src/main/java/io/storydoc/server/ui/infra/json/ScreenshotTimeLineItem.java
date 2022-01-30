package io.storydoc.server.ui.infra.json;

import io.storydoc.server.ui.domain.ScreenshotCoordinate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScreenshotTimeLineItem {
    private ScreenshotCoordinate screenshotCoordinate;
    private String timeLineItemId;
}
