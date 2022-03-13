package io.storydoc.server.code.app;

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
    private List<CodeTraceItemDTO> items;
}
