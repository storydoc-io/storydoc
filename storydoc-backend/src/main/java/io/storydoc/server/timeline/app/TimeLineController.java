package io.storydoc.server.timeline.app;

import io.storydoc.server.storydoc.domain.BlockCoordinate;
import io.storydoc.server.storydoc.domain.BlockId;
import io.storydoc.server.storydoc.domain.StoryDocId;
import io.storydoc.server.timeline.domain.*;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/timeline")
public class TimeLineController {

    private final TimeLineService timeLineService;

    private final TimeLineQueryService timeLineQueryService;

    public TimeLineController(TimeLineService timeLineService, TimeLineQueryService timeLineQueryService) {
        this.timeLineService = timeLineService;
        this.timeLineQueryService = timeLineQueryService;
    }

    @PostMapping(value = "/timelinemodel", produces = MediaType.APPLICATION_JSON_VALUE)
    public TimeLineModelCoordinate createTimeLineModel(String storyDocId, String blockId, String name) {
        BlockCoordinate coordinate = BlockCoordinate.of(new StoryDocId(storyDocId), new BlockId(blockId));
        return timeLineService.createTimeLineModel(coordinate, name);
    }

    @PostMapping(value = "/timelineitem", produces = MediaType.APPLICATION_JSON_VALUE)
    public TimeLineItemId createTimeLineItem(String storyDocId, String blockId, String timeLineModelId, String name) {
        BlockCoordinate blockCoordinate = BlockCoordinate.of(new StoryDocId(storyDocId), new BlockId(blockId));
        TimeLineModelCoordinate modelCoordinate = TimeLineModelCoordinate.of(blockCoordinate, TimeLineModelId.fromString(timeLineModelId));
        TimeLineId defaultTimeLineId = timeLineQueryService.getTimeLineModel(modelCoordinate).getTimeLines().get("default").getTimeLineId();
        TimeLineCoordinate timeLineCoordinate = TimeLineCoordinate.of(modelCoordinate, defaultTimeLineId);
        return timeLineService.addItemToTimeLine(timeLineCoordinate, name);
    }

    @GetMapping(value="/timelinemodel", produces = MediaType.APPLICATION_JSON_VALUE)
    public TimeLineModelDTO getTimeLineModel(String storyDocId, String blockId, String timeLineModelId) {
        BlockCoordinate blockCoordinate = BlockCoordinate.of(new StoryDocId(storyDocId), new BlockId(blockId));
        TimeLineModelCoordinate modelCoordinate = TimeLineModelCoordinate.of(blockCoordinate, TimeLineModelId.fromString(timeLineModelId));
        return timeLineQueryService.getTimeLineModel(modelCoordinate);
    }

    @GetMapping(value = "/timelinemodelsummaries", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TimeLineModelSummaryDTO> getTimeLineModelSummaries(String storyDocId, String blockId) {
        BlockCoordinate blockCoordinate = BlockCoordinate.of(new StoryDocId(storyDocId), new BlockId(blockId));
        return timeLineQueryService.getTimeLineModelSummaries(blockCoordinate);
    }




}
