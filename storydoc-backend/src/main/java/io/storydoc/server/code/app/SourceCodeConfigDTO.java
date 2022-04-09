package io.storydoc.server.code.app;

import io.storydoc.server.code.domain.SourceCodeConfigId;
import io.storydoc.server.storydoc.app.dto.StoryDocSummaryDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class SourceCodeConfigDTO {
    private SourceCodeConfigId id;
    private List<String> dirs;
    private String name;
    private StoryDocSummaryDTO storyDocSummary;

}
