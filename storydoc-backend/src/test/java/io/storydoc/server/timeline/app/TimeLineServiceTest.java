package io.storydoc.server.timeline.app;

import io.storydoc.server.TestBase;
import io.storydoc.server.storydoc.StoryDocTestUtils;
import io.storydoc.server.storydoc.domain.BlockCoordinate;
import io.storydoc.server.timeline.domain.TimeLineCoordinate;
import io.storydoc.server.timeline.domain.TimeLineId;
import io.storydoc.server.timeline.domain.TimeLineItemId;
import io.storydoc.server.timeline.domain.TimeLineModelCoordinate;
import io.storydoc.server.workspace.WorkspaceTestUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TimeLineServiceTest extends TestBase {

    @Autowired
    TimeLineTestUtils timeLineTestUtils;

    @Autowired
    StoryDocTestUtils storyDocTestUtils;

    @Autowired
    WorkspaceTestUtils workspaceTestUtils;

    @Autowired
    TimeLineService timeLineService;

    @Autowired
    TimeLineQueryService timeLineQueryService;

    @Test
    public void create_timeline_model() {
        // given a storydoc with an artifact paragraph
        BlockCoordinate blockCoordinate = storyDocTestUtils.create_storydoc_with_artifact_block();

        // when I create a timeline model
        String timeline_model_name = "timeline_model";
        TimeLineModelCoordinate timeLineModelCoordinate = timeLineService.createTimeLineModel(blockCoordinate, timeline_model_name);

        // then I get a valid coordinate
        assertNotNull(timeLineModelCoordinate);
        assertNotNull(timeLineModelCoordinate.getTimeLineModelId());
        assertEquals(blockCoordinate, timeLineModelCoordinate.getBlockCoordinate());

        workspaceTestUtils.logFolderStructure("after create timeline model");
        workspaceTestUtils.logResourceContent("timeline model content", timeLineTestUtils.getUrn(timeLineModelCoordinate));

        // then I get the timeline model from its coordinate
        TimeLineModelDTO dto = timeLineQueryService.getTimeLineModel(timeLineModelCoordinate);
        assertNotNull(dto);
        assertEquals(timeLineModelCoordinate.getTimeLineModelId(), dto.getTimeLineModelCoordinate().getTimeLineModelId());
        assertEquals(timeline_model_name, dto.getName());

        // then the model has a default timeline
        assertNotNull(dto.getTimeLines());
        assertEquals(1, dto.getTimeLines().size());
        assertNotNull(dto.getTimeLines().get("default"));
        TimeLineDTO timeLineDTO = dto.getTimeLines().get("default");
        assertNotNull(timeLineDTO);
        assertNotNull(timeLineDTO.getTimeLineId());
    }

    @Test
    public void add_item_to_timeline() {
        // given a storydoc with an artifact paragraph
        BlockCoordinate blockCoordinate = storyDocTestUtils.create_storydoc_with_artifact_block();
        // given a timeline model
        TimeLineModelCoordinate modelCoordinate = timeLineTestUtils.createTimeLineModel(blockCoordinate);

        TimeLineModelDTO dto = timeLineQueryService.getTimeLineModel(modelCoordinate);
        TimeLineId defaultTimeLineId = dto.getTimeLines().get("default").getTimeLineId();
        TimeLineCoordinate defaultTimeLineCoordinate = TimeLineCoordinate.of(modelCoordinate, defaultTimeLineId);

        // when I add an item to the default
        String timeline_item_name = "timeline_item_name";
        TimeLineItemId timeLineItemId = timeLineService.addItemToTimeLine(defaultTimeLineCoordinate, timeline_item_name);

        workspaceTestUtils.logFolderStructure("after create timeline model");
        workspaceTestUtils.logResourceContent("timeline model content", timeLineTestUtils.getUrn(modelCoordinate));

    }

}