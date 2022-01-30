package io.storydoc.server.timeline.app;

import io.storydoc.server.storydoc.app.dto.StoryDocSummaryDTO;
import io.storydoc.server.timeline.domain.TimeLineModelId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TimeLineModelDTO {

    private StoryDocSummaryDTO storyDocSummary;
    private String name;
    private TimeLineModelId timeLineModelId;
    private Map<String,TimeLineDTO> timeLines;

}
