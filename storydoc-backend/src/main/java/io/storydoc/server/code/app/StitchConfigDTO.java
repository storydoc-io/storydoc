package io.storydoc.server.code.app;

import io.storydoc.server.code.domain.StitchConfigCoordinate;
import io.storydoc.server.code.domain.StitchConfigId;
import io.storydoc.server.storydoc.app.dto.StoryDocSummaryDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class StitchConfigDTO {
    private StitchConfigCoordinate stitchConfigCoordinate;
    private String dir;
    private String name;
    private StoryDocSummaryDTO storyDocSummary;

}
