package io.storydoc.server.timeline.domain;

import io.storydoc.server.storydoc.domain.ArtifactBlockCoordinate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TimeLineCoordinate {

    private TimeLineModelCoordinate timeLineModelCoordinate;
    private TimeLineId timeLineId;

    public static TimeLineCoordinate of(ArtifactBlockCoordinate blockCoordinate, TimeLineModelId timeLineModelId, TimeLineId timeLineId) {
        return new TimeLineCoordinate(TimeLineModelCoordinate.of(blockCoordinate, timeLineModelId), timeLineId);
    }

    public static TimeLineCoordinate of(TimeLineModelCoordinate timeLineModelCoordinate, TimeLineId timeLineId) {
        return new TimeLineCoordinate(timeLineModelCoordinate, timeLineId);
    }

}
