package io.storydoc.server.ui.infra.json;

import io.storydoc.server.storydoc.domain.Artifact;
import io.storydoc.server.timeline.domain.TimeLineId;
import io.storydoc.server.timeline.domain.TimeLineModelCoordinate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UIScenario implements Artifact {

    private String id;
    private TimeLineModelCoordinate timeLineModelCoordinate;
    TimeLineId timeLineId;
    private List<ScreenshotTimeLineItem> screenshots;

}
