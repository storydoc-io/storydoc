package io.storydoc.server.timeline.domain;

import io.storydoc.server.timeline.infra.TimeLineModel;
import io.storydoc.server.workspace.domain.ResourceUrn;

public interface TimeLineStore {
    void createTimeLineModel(TimeLineModelCoordinate timeLineModelCoordinate, TimeLineItemId defaultTimeLineId, String name);

    ResourceUrn getUrn(TimeLineModelCoordinate timeLineModelCoordinate);

    TimeLineModel loadTimeLineModel(TimeLineModelCoordinate timeLineModelCoordinate);

    void addItemToTimeLine(TimeLineCoordinate timeLineCoordinate, TimeLineItemId timeLineItemId, String name);
}
