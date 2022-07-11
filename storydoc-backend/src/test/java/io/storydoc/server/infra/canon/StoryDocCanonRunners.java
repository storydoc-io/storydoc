package io.storydoc.server.infra.canon;

import io.storydoc.canon.ExecutionContext;
import io.storydoc.canon.annotation.UseCaseRunnerClass;
import io.storydoc.canon.annotation.UseCaseRunnerMethod;
import io.storydoc.server.infra.canon.steps.AddBlockToStoryDoc;
import io.storydoc.server.infra.canon.steps.CreateStoryDoc;
import io.storydoc.server.storydoc.app.StoryDocService;
import io.storydoc.server.storydoc.domain.BlockId;
import io.storydoc.server.storydoc.domain.StoryDocId;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@UseCaseRunnerClass
public class StoryDocCanonRunners {

    private final StoryDocService storyDocService;

    public StoryDocCanonRunners(StoryDocService storyDocService) {
        this.storyDocService = storyDocService;
    }

    @UseCaseRunnerMethod(CreateStoryDoc.class)
    public void createStoryDoc(CreateStoryDoc stepDef, ExecutionContext context) {
        String name = "storydoc-" + UUID.randomUUID();
        StoryDocId storyDocId = storyDocService.createDocument(name);
        context.setVariable(stepDef.getStoryDocVariableName(), storyDocId);
    }

    @UseCaseRunnerMethod(AddBlockToStoryDoc.class)
    public void addBlock(AddBlockToStoryDoc stepDef, ExecutionContext context) {
        StoryDocId storyDocId = (StoryDocId) context.getVariable(stepDef.getStoryDocVariableName());
        String blockName = "block-" + UUID.randomUUID();
        BlockId blockId = storyDocService.addArtifactBlock(storyDocId, blockName);
        context.setVariable(stepDef.getBlockVariableName(), blockId);
    }


}
