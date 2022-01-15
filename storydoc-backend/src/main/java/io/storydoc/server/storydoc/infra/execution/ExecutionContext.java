package io.storydoc.server.storydoc.infra.execution;

import io.storydoc.server.storydoc.infra.store.model.Block;
import io.storydoc.server.storydoc.infra.store.model.StoryDoc;
import io.storydoc.server.storydoc.infra.execution.event.ExecutionEvent;
import io.storydoc.server.storydoc.infra.execution.event.ExecutionListener;
import lombok.Builder;
import lombok.Value;
import lombok.With;

import java.util.List;


@Builder
@Value
public class ExecutionContext {

    StoryDoc storyDoc;
    List<Block> blocksToRun;
    @With Block currentBlock;
    List<ExecutionListener> listeners;

    public void emit(ExecutionEvent event) {
        if (listeners !=null) {
            for(ExecutionListener listener: listeners) {
                listener.handle(event);
            }
        }
    }

    public ExecutionContext withNextBlock() {
        int idxOfNextBlock = blocksToRun.indexOf(currentBlock) + 1;
        return (idxOfNextBlock < blocksToRun.size())
            ? withCurrentBlock(blocksToRun.get(idxOfNextBlock))
            : null;
    }

}
