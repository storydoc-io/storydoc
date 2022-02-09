package io.storydoc.server.ui.domain;

import io.storydoc.server.storydoc.domain.BlockCoordinate;
import io.storydoc.server.storydoc.domain.BlockId;
import io.storydoc.server.storydoc.domain.StoryDocId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UIScenarioCoordinate {

    BlockCoordinate blockCoordinate;
    UIScenarioId uiScenarioId;

    static public UIScenarioCoordinate of(StoryDocId storyDocId, BlockId blockId, UIScenarioId uiScenarioId) {
        return of(BlockCoordinate.of(storyDocId, blockId), uiScenarioId);
    }

    static public UIScenarioCoordinate of(BlockCoordinate blockCoordinate, UIScenarioId uiScenarioId) {
        return new UIScenarioCoordinate(blockCoordinate, uiScenarioId);
    }
}
