package io.storydoc.server.timeline.app;

import io.storydoc.server.storydoc.domain.ArtifactBlockCoordinate;
import io.storydoc.server.timeline.domain.TimeLineCoordinate;
import io.storydoc.server.timeline.domain.TimeLineItemId;
import io.storydoc.server.timeline.domain.TimeLineModelCoordinate;

public interface TimeLineService {
    TimeLineModelCoordinate createTimeLineModel(ArtifactBlockCoordinate blockCoordinate, String name);
    TimeLineItemId addItemToTimeLine(TimeLineCoordinate defaultTimeLineCoordinate, String name);
}
