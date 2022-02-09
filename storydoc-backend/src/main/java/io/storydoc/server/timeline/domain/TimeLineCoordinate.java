package io.storydoc.server.timeline.domain;

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
public class TimeLineCoordinate {

    private TimeLineModelCoordinate timeLineModelCoordinate;
    private TimeLineId timeLineId;

    public static TimeLineCoordinate of(StoryDocId storyDocId, BlockId blockId, TimeLineModelId timeLineModelId, TimeLineId timeLineId) {
        BlockCoordinate blockCoordinate = BlockCoordinate.of(storyDocId, blockId);
        return new TimeLineCoordinate(TimeLineModelCoordinate.of(blockCoordinate, timeLineModelId), timeLineId);
    }

    public static TimeLineCoordinate of(BlockCoordinate blockCoordinate, TimeLineModelId timeLineModelId, TimeLineId timeLineId) {
        return new TimeLineCoordinate(TimeLineModelCoordinate.of(blockCoordinate, timeLineModelId), timeLineId);
    }

    public static TimeLineCoordinate of(TimeLineModelCoordinate timeLineModelCoordinate, TimeLineId timeLineId) {
        return new TimeLineCoordinate(timeLineModelCoordinate, timeLineId);
    }

}
