package io.storydoc.server.ui.app;

import io.storydoc.server.storydoc.app.dto.StoryDocSummaryDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScreenShotCollectionDTO {

    private String name;

    private StoryDocSummaryDTO storyDocSummary;

    private List<ScreenShotDTO> screenShots;

}
