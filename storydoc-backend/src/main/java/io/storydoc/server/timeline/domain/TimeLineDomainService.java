package io.storydoc.server.timeline.domain;

import io.storydoc.server.infra.IDGenerator;
import io.storydoc.server.storydoc.domain.ArtifactBlockCoordinate;
import org.springframework.stereotype.Component;

@Component
public class TimeLineDomainService {

    private final TimeLineStore timeLineStore;

    public TimeLineDomainService(TimeLineStore timeLineStore, IDGenerator idGenerator) {
        this.timeLineStore = timeLineStore;
    }

    public TimeLineModelCoordinate createTimeLineModel(ArtifactBlockCoordinate blockCoordinate, TimeLineModelId timeLineModelId, TimeLineItemId defaultTimeLineId, String name) {
        TimeLineModelCoordinate timeLineModelCoordinate = TimeLineModelCoordinate.of(blockCoordinate, timeLineModelId);
        timeLineStore.createTimeLineModel(timeLineModelCoordinate, defaultTimeLineId, name);
        return timeLineModelCoordinate;
    }

    public TimeLineModel getTimeLineModel(TimeLineModelCoordinate timeLineModelCoordinate) {
        return TimeLineModel.builder()
            .timeLineModelId(timeLineModelCoordinate.getTimeLineModelId())
            .timeLineStore(timeLineStore)
            .build();
    }
}
