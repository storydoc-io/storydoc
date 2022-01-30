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
public class TimeLineModelCoordinate {

    private ArtifactBlockCoordinate blockCoordinate;
    private TimeLineModelId timeLineModelId;

    public static TimeLineModelCoordinate of(ArtifactBlockCoordinate blockCoordinate, TimeLineModelId timeLineModelId) {
        return new TimeLineModelCoordinate(blockCoordinate, timeLineModelId);
    }
}
