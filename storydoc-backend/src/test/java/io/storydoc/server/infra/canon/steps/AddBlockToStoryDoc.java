package io.storydoc.server.infra.canon.steps;

import io.storydoc.canon.StepDef;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AddBlockToStoryDoc extends StepDef {
    private String storyDocVariableName;
    private String blockVariableName;
}
