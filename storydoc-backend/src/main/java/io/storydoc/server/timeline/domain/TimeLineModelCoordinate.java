package io.storydoc.server.timeline.domain;

import io.storydoc.server.storydoc.domain.ArtifactCoordinate;
import io.storydoc.server.storydoc.domain.BlockCoordinate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TimeLineModelCoordinate {

    private BlockCoordinate blockCoordinate;
    private TimeLineModelId timeLineModelId;

    public static TimeLineModelCoordinate of(BlockCoordinate blockCoordinate, TimeLineModelId timeLineModelId) {
        return new TimeLineModelCoordinate(blockCoordinate, timeLineModelId);
    }

    public ArtifactCoordinate asArtifactCoordinate() {
        return ArtifactCoordinate.of(timeLineModelId.asArtifactId(), blockCoordinate);
    }
}
