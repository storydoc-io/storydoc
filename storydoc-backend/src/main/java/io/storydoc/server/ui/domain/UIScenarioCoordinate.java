package io.storydoc.server.ui.domain;

import io.storydoc.server.storydoc.domain.ArtifactBlockCoordinate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UIScenarioCoordinate {

    ArtifactBlockCoordinate blockCoordinate;
    UIScenarioId uiScenarioId;

    static public UIScenarioCoordinate of(ArtifactBlockCoordinate blockCoordinate, UIScenarioId uiScenarioId) {
        return new UIScenarioCoordinate(blockCoordinate, uiScenarioId);
    }
}
