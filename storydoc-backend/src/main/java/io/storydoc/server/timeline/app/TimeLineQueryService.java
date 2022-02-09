package io.storydoc.server.timeline.app;

import io.storydoc.server.storydoc.domain.BlockCoordinate;
import io.storydoc.server.timeline.domain.TimeLineModelCoordinate;

import java.util.List;

public interface TimeLineQueryService {
    TimeLineModelDTO getTimeLineModel(TimeLineModelCoordinate timeLineModelCoordinate);
    List<TimeLineModelSummaryDTO> getTimeLineModelSummaries(BlockCoordinate blockCoordinate);
}
