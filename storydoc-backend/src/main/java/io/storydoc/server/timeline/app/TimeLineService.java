package io.storydoc.server.timeline.app;

import io.storydoc.server.storydoc.domain.BlockCoordinate;
import io.storydoc.server.timeline.domain.TimeLineCoordinate;
import io.storydoc.server.timeline.domain.TimeLineItemId;
import io.storydoc.server.timeline.domain.TimeLineModelCoordinate;

public interface TimeLineService {
    TimeLineModelCoordinate createTimeLineModel(BlockCoordinate blockCoordinate, String name);

    TimeLineItemId addItemToTimeLine(TimeLineCoordinate defaultTimeLineCoordinate, String name);
    void removeTimeLineItem(TimeLineCoordinate timeLineCoordinate, TimeLineItemId timeLineItemId);
    void renameTimeLineItem(TimeLineCoordinate timeLineCoordinate,  TimeLineItemId timeLineItemId, String name);

}
