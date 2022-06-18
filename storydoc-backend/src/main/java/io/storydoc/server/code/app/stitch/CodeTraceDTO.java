package io.storydoc.server.code.app.stitch;

import io.storydoc.server.code.domain.SourceCodeConfigCoordinate;
import io.storydoc.server.code.domain.StitchConfigCoordinate;
import io.storydoc.server.storydoc.app.dto.StoryDocSummaryDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class CodeTraceDTO {
    private String description;
    private StoryDocSummaryDTO storyDocSummary;
    private List<StitchItemDTO> items;
    private String name;
    public SourceCodeConfigCoordinate config;
    public StitchConfigCoordinate stitchConfigCoordinate;
}
