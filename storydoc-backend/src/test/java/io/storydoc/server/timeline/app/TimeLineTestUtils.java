package io.storydoc.server.timeline.app;

import io.storydoc.server.storydoc.domain.ArtifactBlockCoordinate;
import io.storydoc.server.timeline.domain.*;
import io.storydoc.server.workspace.domain.ResourceUrn;
import org.springframework.stereotype.Component;

@Component
public class TimeLineTestUtils {

    private final TimeLineStore timeLineStore;

    private final TimeLineService timeLineService;

    private final TimeLineQueryService timeLineQueryService;

    public TimeLineTestUtils(TimeLineStore timeLineStore, TimeLineService timeLineService, TimeLineQueryService timeLineQueryService) {
        this.timeLineStore = timeLineStore;
        this.timeLineService = timeLineService;
        this.timeLineQueryService = timeLineQueryService;
    }

    ResourceUrn getUrn(TimeLineModelCoordinate timeLineModelCoordinate) {
        return timeLineStore.getUrn(timeLineModelCoordinate);
    }

    public TimeLineModelCoordinate createTimeLineModel(ArtifactBlockCoordinate blockCoordinate) {
        String timeline_model_name = "timeline_model";
        return timeLineService.createTimeLineModel(blockCoordinate, timeline_model_name);
    }

    public TimeLineItemId addItemToDefaultTimeLine(TimeLineModelCoordinate timeLineModelCoordinate) {
        return timeLineService.addItemToTimeLine(getDefaultTimeLine(timeLineModelCoordinate), "item");
    }

    public TimeLineCoordinate getDefaultTimeLine(TimeLineModelCoordinate timeLineModelCoordinate) {
        TimeLineId timeLineId = timeLineQueryService.getTimeLineModel(timeLineModelCoordinate).getTimeLines().get("default").getTimeLineId();
        return TimeLineCoordinate.of(timeLineModelCoordinate, timeLineId);
    }
}
