package io.storydoc.server.infra.canon.steps;

import io.storydoc.canon.StepDef;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateStoryDoc extends StepDef {
    private String storyDocVariableName;
}
