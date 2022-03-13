package io.storydoc.server.ui.app.screendesign;

import io.storydoc.server.storydoc.app.dto.StoryDocSummaryDTO;
import io.storydoc.server.ui.domain.screendesign.ScreenDesignCoordinate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScreenDesignDTO {
    private ScreenDesignCoordinate coordinate;
    private StoryDocSummaryDTO storyDocSummaryDTO;
    private String name;
    private SDContainerDTO rootContainer;
}
