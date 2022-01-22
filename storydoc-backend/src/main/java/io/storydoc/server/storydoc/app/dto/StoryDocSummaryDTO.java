package io.storydoc.server.storydoc.app.dto;

import io.storydoc.server.storydoc.domain.StoryDocId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StoryDocSummaryDTO {
    private StoryDocId storyDocId;
    private String name;

}
