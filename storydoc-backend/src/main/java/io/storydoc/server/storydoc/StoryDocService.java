package io.storydoc.server.storydoc;

import io.storydoc.server.document.infra.execution.ExecutionEngine;
import io.storydoc.server.document.infra.store.model.Block;
import io.storydoc.server.document.infra.store.model.TextBlock;
import io.storydoc.server.storydoc.model.BlockRef;
import io.storydoc.server.storydoc.model.CmdBlock;
import io.storydoc.server.storydoc.model.StoryDoc;
import org.springframework.stereotype.Service;

@Service
public class StoryDocService {

    private final StoryDocRepo storyDocRepo;
    private final ExecutionEngine executionEngine;

    public StoryDocService(StoryDocRepo storyDocRepo, ExecutionEngine executionEngine) {
        this.storyDocRepo = storyDocRepo;
        this.executionEngine = executionEngine;
    }

    public StoryDoc getStoryDoc(String id) {
        return toDTO(storyDocRepo.findById(id));

    }

    private StoryDoc toDTO(io.storydoc.server.document.infra.store.model.StoryDoc storyDoc) {
        return StoryDoc.builder()
            .id(storyDoc.getId())
            .name(storyDoc.getName())
            .blocks(storyDoc.getBlocks().stream()
                .map(block -> BlockRef.builder()
                        .id(storyDoc.getId() + "/" + block.getId())
                        .type(block instanceof TextBlock ? "text": "cmd")
                        .build())
                .toArray(BlockRef[]::new)
            )
            .build();
    }



    public void runBlock(String id) {

        System.out.println("runBlock id : "+ id);

        io.storydoc.server.document.infra.store.model.StoryDoc storyDoc = storyDocRepo.findById("test");


        executionEngine.run(storyDoc, null);
    }

    public io.storydoc.server.storydoc.model.TextBlock getTextBlock(String id) {
        Block block = getBlock(id);
        return toDTO((TextBlock)block);

    }

    private Block getBlock(String id) {
        String[] parts = id.split("/");
        String docId = parts[0];
        String blockId = parts[1];

        io.storydoc.server.document.infra.store.model.StoryDoc storyDoc = storyDocRepo.findById(docId);
        return storyDoc.getBlocks().stream()
                .filter(b->b.getId().equals(blockId))
                .findFirst()
                .get();
    }

    private io.storydoc.server.storydoc.model.TextBlock toDTO(TextBlock block) {
        return io.storydoc.server.storydoc.model.TextBlock.builder()
                .id(block.getId())
                .text(block.getText())
                .name(block.getName())
                .build();
    }

    public CmdBlock getCmdBlock(String id) {
        Block block = getBlock(id);
        return toDTO((io.storydoc.server.document.infra.store.model.CmdBlock)block);
    }

    private CmdBlock toDTO(io.storydoc.server.document.infra.store.model.CmdBlock cmdBlock) {
        return CmdBlock.builder()
                .id(cmdBlock.getId())
                .cmd(cmdBlock.getCmd())
                .build();
    }

}
