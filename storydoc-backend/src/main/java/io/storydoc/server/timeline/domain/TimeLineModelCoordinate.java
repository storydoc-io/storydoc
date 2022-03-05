package io.storydoc.server.timeline.domain;

import io.storydoc.server.storydoc.domain.ArtifactCoordinate;
import io.storydoc.server.storydoc.domain.BlockCoordinate;
import io.storydoc.server.storydoc.domain.BlockId;
import io.storydoc.server.storydoc.domain.StoryDocId;
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

    public static TimeLineModelCoordinate of(StoryDocId timeLineModelStoryDocId, BlockId timeLineModelBlockId, TimeLineModelId timeLineModelId) {
        return of(BlockCoordinate.of(timeLineModelStoryDocId, timeLineModelBlockId), timeLineModelId);
    }

    public ArtifactCoordinate asArtifactCoordinate() {
        return ArtifactCoordinate.of(blockCoordinate, timeLineModelId.asArtifactId());
    }
}
