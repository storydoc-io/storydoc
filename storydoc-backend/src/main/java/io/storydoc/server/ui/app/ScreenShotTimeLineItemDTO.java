package io.storydoc.server.ui.app;

import io.storydoc.server.timeline.domain.TimeLineItemId;
import io.storydoc.server.ui.domain.ScreenshotCoordinate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScreenShotTimeLineItemDTO {

    private ScreenshotCoordinate screenshotCoordinate;

    private TimeLineItemId timeLineItemId;
}
