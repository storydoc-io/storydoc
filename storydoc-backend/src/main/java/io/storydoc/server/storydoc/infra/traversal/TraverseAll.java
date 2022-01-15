package io.storydoc.server.storydoc.infra.traversal;

import io.storydoc.server.storydoc.infra.store.model.Block;
import io.storydoc.server.storydoc.infra.store.model.StoryDoc;

import java.util.List;

public class TraverseAll extends Traversal {

    public TraverseAll(StoryDoc storyDoc) {
        super(storyDoc);
    }

    @Override
    public Block next() {
        List<Block> blocks = storyDoc.getBlocks();
        if (current == null) {
            if (blocks.size() > 0) {
                current = blocks.get(0);
            }
        } else {
            boolean passedLast = false;
            for (Block block : blocks) {
                if (block.equals(current)) {
                    passedLast = true;
                } else {
                    if (passedLast) {
                        current = block;
                        break;
                    }
                }
            }
        }
        return current;
    }

}
