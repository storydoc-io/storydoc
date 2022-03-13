package io.storydoc.server.timeline.app;

import io.storydoc.server.infra.IDGenerator;
import io.storydoc.server.storydoc.domain.BlockCoordinate;
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
    public TimeLineModelCoordinate createTimeLineModel(BlockCoordinate blockCoordinate, String name) {
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

    @Override
    public void removeTimeLineItem(TimeLineCoordinate timeLineCoordinate, TimeLineItemId timeLineItemId) {
        timeLineDomainService
                .getTimeLineModel(timeLineCoordinate.getTimeLineModelCoordinate())
                .removeItem(timeLineCoordinate, timeLineItemId);
    }

    @Override
    public void renameTimeLineItem(TimeLineCoordinate timeLineCoordinate, TimeLineItemId timeLineItemId, String name) {
        timeLineDomainService
                .getTimeLineModel(timeLineCoordinate.getTimeLineModelCoordinate())
                .renameItem(timeLineCoordinate, timeLineItemId, name);
    }
}


