package io.storydoc.server.ui.app;

import io.storydoc.server.storydoc.app.dto.StoryDocSummaryDTO;
import io.storydoc.server.timeline.domain.TimeLineId;
import io.storydoc.server.timeline.domain.TimeLineModelCoordinate;
import io.storydoc.server.ui.domain.UIScenarioId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UIScenarioDTO {

    private StoryDocSummaryDTO storyDocSummary;

    private UIScenarioId id;

    private String name;

    private TimeLineModelCoordinate timeLineModelCoordinate;

    private TimeLineId timeLineId;

    private List<ScreenShotTimeLineItemDTO> screenshots;

    private List<ScreenshotCollectionSummaryDTO> associatedCollections;


}
