package io.storydoc.server.document.app.dto;

import io.storydoc.server.document.domain.StoryDocId;
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

}
