package io.storydoc.server.timeline.app;

import io.storydoc.server.infra.IDGenerator;
import io.storydoc.server.storydoc.domain.ArtifactBlockCoordinate;
import io.storydoc.server.timeline.domain.*;
import org.springframework.stereotype.Component;

@Component
public class TimeLineServiceImpl implements TimeLineService {

    private final TimeLineDomainService timeLineDomainService;
    private final IDGenerator idGenerator;

    public TimeLineServiceImpl(TimeLineDomainService timeLineDomainService, IDGenerator idGenerator) {
        this.timeLineDomainService = timeLineDomainService;
        this.idGenerator = idGenerator;
    }

    @Override
    public TimeLineModelCoordinate createTimeLineModel(ArtifactBlockCoordinate blockCoordinate, String name) {
        TimeLineModelId timeLineModelId = TimeLineModelId.fromString(idGenerator.generateID("TIMELINE-MODEL"));
        TimeLineItemId defaultTimeLineId = TimeLineItemId.fromString(idGenerator.generateID("TIMELINE"));
        return timeLineDomainService.createTimeLineModel(blockCoordinate, timeLineModelId, defaultTimeLineId, name);
    }

    @Override
    public TimeLineItemId addItemToTimeLine(TimeLineCoordinate timeLineCoordinate, String name) {
        TimeLineItemId timeLineItemId = TimeLineItemId.fromString(idGenerator.generateID("TIMELINE-ITEM"));
        timeLineDomainService
                .getTimeLineModel(timeLineCoordinate.getTimeLineModelCoordinate())
                .addItemToTimeLine(timeLineCoordinate, timeLineItemId, name);
        return timeLineItemId;
    }
}


