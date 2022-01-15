package io.storydoc.server.storydoc.app.dto;

import io.storydoc.server.storydoc.domain.StoryDocId;
import io.storydoc.server.workspace.domain.ResourceUrn;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StoryDocDTO {

    private StoryDocId storyDocId;

    private List<BlockDTO> blocks;

    private String title;

    private ResourceUrn urn;
}
