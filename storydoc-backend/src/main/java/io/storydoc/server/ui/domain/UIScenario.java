package io.storydoc.server.ui.domain;

import io.storydoc.server.timeline.domain.TimeLineCoordinate;
import io.storydoc.server.timeline.domain.TimeLineItemId;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
public class UIScenario {

    static public String ARTIFACT_TYPE = UIScenario.class.getName();

    private UIScenarioCoordinate coordinate;
    private UIStorage uiStorage;


    public void addScreenshot(ScreenshotCoordinate screenshotCoordinate, TimeLineItemId timeLineItemId) {
        uiStorage.addScreenshot(coordinate, screenshotCoordinate, timeLineItemId);
    }

    public void setTimeLine(TimeLineCoordinate timeLineCoordinate) {
        uiStorage.setTimeLine(coordinate, timeLineCoordinate);
    }
}
